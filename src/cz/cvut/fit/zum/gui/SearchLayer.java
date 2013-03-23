package cz.cvut.fit.zum.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import cz.cvut.fit.zum.api.AbstractAlgorithm;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.NodeImpl;
import cz.cvut.fit.zum.VisInfo;
import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.api.ga.VertexCoverTask;
import cz.cvut.fit.zum.data.StateSpace;
import java.util.HashMap;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Tomas Barton
 */
public class SearchLayer extends BufferedPanel {

    private static final long serialVersionUID = 7263174864182674953L;
    private Node from, to;
    protected BufferedImage startPoint;
    protected BufferedImage endPoint;
    protected BufferedImage visited;
    protected VisInfo visInfo;
    private AffineTransform at;
    private Node node;
    private Color pathColor;
    protected boolean searchFinished = false;
    private AbstractAlgorithm alg;
    protected HashMap<String, Double> stats = new HashMap<String, Double>();
    private long delay;
    private Context ctx;
    private VertexCoverTask vctx;
    private GridLayer roads;

    public SearchLayer(Dimension dim, GridLayer roadsLayer) {
        setSize(dim);
        this.roads = roadsLayer;
        visInfo = VisInfo.getInstance();
        initComponents();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                node = visInfo.findNode(me.getX(), me.getY(), getSize());
                if (node != null) {
                    if (searchFinished) {
                        updateLayer(); //clean canvas
                        searchFinished = false;
                    } else {
                        stopSearch();
                    }
                    final Node n = node;
                    highlightPoint(n, startPoint);
                    repaint();
                } else {
                    return;
                }

                if (from == null || (from != null && to != null)) {
                    from = node;
                    to = null;
                } else {
                    to = node;
                    if (node != null) {
                        node = null;
                        runSearch(from, to, alg);
                    }
                }
            }
        });
    }

    private void initComponents() {
        setOpaque(false);
        at = new AffineTransform();
        startPoint = visInfo.createCircle(Color.RED);
        endPoint = visInfo.createCircle(Color.GREEN);
        visited = visInfo.createCircle(new Color(215, 153, 3));
        pathColor = Color.white;
        vctx = new VertexCoverTask(this);
        clearStats();
    }

    public void clearSelection() {
        from = null;
        to = null;
        node = null;
        clearStats();
    }

    private void clearStats() {
        stats.put("explored", 0.0);
        stats.put("expanded", 0.0);
        stats.put("coverage", 0.0);
        stats.put("distance", 0.0);
        stats.put("time", 0.0);
        fireStatsChanged(stats);
    }

    protected void higlightEdge(final Node start, final Node end, Color color) {
        roads.higlightEdge(start, end, color);
    }

    void stopSearch() {
        TaskContext context = NodeImpl.getContext();
        if (context != null) {
            System.out.println("Stopping search");
            context.setFinish(true);
        }
        updateLayer();
        repaint();
    }

    public void highlightPoint(final Node point, BufferedImage shape) {
        drawPoint(point, shape);
    }

    protected double highlightPath(List<Node> path) {
        if (path == null || path.size() < 2) {
            return 0;
        }
        graphics.setColor(pathColor);
        Node prev, next;
        int i = 0;
        int length = path.size();
        double distance = 0;
        do {
            prev = path.get(i++);
            if (i < length) {
                next = path.get(i);
                roads.higlightEdge(prev, next, pathColor);
                distance += euclideanDist(prev, next);
            }
        } while (i < length);
        repaint();
        return distance;
    }

    public double euclideanDist(Node a, Node b) {
        double dist = 0;
        double x, y;
        x = a.getX() - b.getX();
        dist += x * x;
        y = a.getY() - b.getY();
        dist += y * y;
        return Math.sqrt(dist) * 100; //just random coefficient to make numbers more interesting
    }

    /**
     * Search algorithm changed
     *
     * @param algorithm
     */
    public void algorithmChanged(AbstractAlgorithm algorithm) {
        this.alg = algorithm;
        if (from != null && to != null) {
            updateLayer();
            runSearch(from, to, alg);
        }
    }

    /**
     * Evolutionary algorithm changed
     *
     * @param evolAlg
     */
    public void vertexAlgorithmChanged(final AbstractEvolution evolAlg, boolean run) {
        if (run) {
            vctx.run(evolAlg);
        }
    }

    private void runSearch(final Node source, final Node target, final AbstractAlgorithm algorithm) {
        highlightPoint(source, startPoint);
        highlightPoint(target, endPoint);
        repaint();
        if (ctx != null) {
            ctx.cancel(true);
        }
        ctx = new Context(algorithm, source, target, this, delay);
        NodeImpl.setContext(ctx);
        fireAlgEvent(AlgorithmEvents.STARTED);
        System.out.println("Starting search...");
        //System.out.println("starting search from " + source.getId() + " to " + target.getId());
        ctx.execute();
    }

    private void drawPoint(Node node, BufferedImage shape) {
        if (node != null) {
            at.setToIdentity();
            at.translate(node.getPoint().getX() - visInfo.getCircleDiam(), node.getPoint().getY() - visInfo.getCircleDiam());
            graphics.drawImage(shape, at, null);
            //intentionally no repaint
        }
    }

    @Override
    protected void drawComponent(Graphics2D g) {
        //nothing to do
        highlightPoint(from, startPoint);
        highlightPoint(to, endPoint);
    }

    public void search(Node a, Node b) {
        from = a;
        to = b;
        updateLayer();
        runSearch(from, to, alg);
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    protected void updateStats(Context ctx) {
        double expanded = ctx.getExpandCalls();
        double cov = expanded / (double) StateSpace.nodesCount() * 100;
        stats.put("explored", (double) ctx.getExploredNodes());
        stats.put("expanded", expanded);
        stats.put("coverage", cov);
        stats.put("time", (double) ctx.getTime());
        fireStatsChanged(stats);
    }
    private transient EventListenerList statListeners = new EventListenerList();

    public void addStatsListener(AlgorithmListener listener) {
        statListeners.add(AlgorithmListener.class, listener);
    }

    public void fireStatsChanged(HashMap<String, Double> stats) {
        if (statListeners != null) {
            AlgorithmListener[] list = statListeners.getListeners(AlgorithmListener.class);
            for (AlgorithmListener l : list) {
                l.statsChanged(stats);
            }
        }
    }

    public void fireAlgEvent(AlgorithmEvents type) {
        if (statListeners != null) {
            AlgorithmListener[] list = statListeners.getListeners(AlgorithmListener.class);
            for (AlgorithmListener l : list) {
                switch (type) {
                    case STARTED:
                        l.searchStarted();
                        break;
                    case FINISHED:
                        l.searchFinished();
                        break;
                }
            }
        }
    }
    private transient EventListenerList evolListeners = new EventListenerList();

    public void addEvolutionListener(EvolutionListener listener) {
        evolListeners.add(EvolutionListener.class, listener);
    }

    public void fireEvolutionChanged(HashMap<String, Double> stats) {
        if (evolListeners != null) {
            EvolutionListener[] list = evolListeners.getListeners(EvolutionListener.class);
            for (EvolutionListener l : list) {
                l.statsChanged(stats);
            }
        }
    }

    public VertexCoverTask getVertexCoverTask() {
        return vctx;
    }

    public void enableSearchButton() {
        if (evolListeners != null) {
            EvolutionListener[] list = evolListeners.getListeners(EvolutionListener.class);
            for (EvolutionListener l : list) {
                l.evolutionFinished();
            }
        }
    }
    
    @Override
    public void panelResized(){
        if(vctx != null){
            vctx.panelResized();
        }
    }
}
