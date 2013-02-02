package cz.cvut.fit.zum.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.SwingUtilities;
import cz.cvut.fit.zum.api.AbstractAlgorithm;
import cz.cvut.fit.zum.api.InformedSearch;
import cz.cvut.fit.zum.api.UninformedSearch;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.NodeImpl;
import cz.cvut.fit.zum.VisInfo;
import java.util.HashMap;
import javax.swing.event.EventListenerList;
import org.openide.util.Exceptions;

/**
 *
 * @author Tomas Barton
 */
public class SearchLayer extends BufferedPanel {

    private static final long serialVersionUID = 7263174864182674953L;
    private NodeImpl from, to;
    private BufferedImage startPoint;
    private BufferedImage endPoint;
    private BufferedImage visited;
    private VisInfo visInfo;
    private AffineTransform at;
    private NodeImpl node;
    private Shape line;
    private Color pathColor;
    private Color edgeColor;
    private boolean searchFinished = false;
    private AbstractAlgorithm alg;
    private transient EventListenerList statListeners = new EventListenerList();
    private HashMap<String, Double> stats = new HashMap<String, Double>();
    private long delay;

    public SearchLayer(Dimension dim, final VisInfo visInfo) {
        setSize(dim);
        this.visInfo = visInfo;
        initComponents();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                node = visInfo.findNode(me.getX(), me.getY(), getSize());
                if (node != null) {
                    if (searchFinished) {
                        updateLayer(); //clean canvas
                        searchFinished = false;
                    }
                    final NodeImpl n = node;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            highlightPoint(n, startPoint);
                        }
                    });

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
                        highlightPoint(to, endPoint);
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
        edgeColor = new Color(255, 0, 255);
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
        fireStatsChanged(stats);
    }

    protected void higlightEdge(final NodeImpl start, final NodeImpl end) {
        Thread highlight = new HighlightThread(start, end);
        highlight.start();
        highlight.setPriority(Thread.MIN_PRIORITY + 2);
        try {
            highlight.join();
        } catch (InterruptedException e) {
            Exceptions.printStackTrace(e);
        }
    }

    private class HighlightThread extends Thread {

        private NodeImpl start;
        private NodeImpl end;

        public HighlightThread(NodeImpl start, NodeImpl end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            graphics.setColor(edgeColor);
            line = new Line2D.Double(start.getPoint(), end.getPoint());
            graphics.draw(line);
            repaint();
        }
    }
    
    public void markCheckedPoint(final NodeImpl point){
        highlightPoint(point, visited);
    }

    public void highlightPoint(final NodeImpl point, BufferedImage shape) {
        Thread highlight = new HighlightPointThread(point, shape);
        highlight.start();
        highlight.setPriority(Thread.MIN_PRIORITY + 2);
        try {
            highlight.join();
        } catch (InterruptedException e) {
            Exceptions.printStackTrace(e);
        }
    }

    private class HighlightPointThread extends Thread {

        private NodeImpl point;
        private BufferedImage shape;

        public HighlightPointThread(NodeImpl point, BufferedImage shape) {
            this.point = point;
            this.shape = shape;
        }

        @Override
        public void run() {
            drawPoint(point, shape);
        }
    }

    protected double highlightPath(List<Node> path) {
        if (path == null || path.size() < 2) {
            return 0;
        }
        graphics.setColor(pathColor);
        NodeImpl prev, next;
        int i = 0;
        int length = path.size();
        double distance = 0;
        do {
            prev = (NodeImpl) path.get(i++);
            if (i < length) {
                next = (NodeImpl) path.get(i);
                line = new Line2D.Double(prev.getPoint(), next.getPoint());
                graphics.draw(line);
                distance += euclideanDist(prev, next);
            }
        } while (i < length);
        repaint();
        return distance;
    }

    public double euclideanDist(NodeImpl a, NodeImpl b) {
        double dist = 0;
        double x, y;
        x = a.getX() - b.getX();
        dist += x * x;
        y = a.getY() - b.getY();
        dist += y * y;
        return Math.sqrt(dist) * 100; //just random coefficient to make numbers more interesting
    }

    public void algorithmChanged(AbstractAlgorithm algorithm) {
        this.alg = algorithm;
        if (from != null && to != null) {
            updateLayer();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    runSearch(from, to, alg);
                }
            });
        }
    }

    private void runSearch(NodeImpl source, NodeImpl target, AbstractAlgorithm algorithm) {
        Context ctx = new Context(visInfo.getNodes(), source, target, this, delay);
        NodeImpl.setContext(ctx);

        AlgorithmRunner thread = new AlgorithmRunner(ctx, algorithm);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        long timeout = 1000;
        try {
            thread.join(timeout);
        } catch (InterruptedException e) {
            Exceptions.printStackTrace(e);
        }
        searchFinished = true;
    }

    private void drawPoint(NodeImpl node, BufferedImage shape) {
        at.setToIdentity();
        at.translate(node.getPoint().getX() - visInfo.getCircleDiam(), node.getPoint().getY() - visInfo.getCircleDiam());
        graphics.drawImage(shape, at, null);
        repaint();
    }

    @Override
    protected void drawComponent(Graphics2D g) {
        //nothing to do
        if (from != null && to != null && alg != null) {
            runSearch(from, to, alg);
        }
    }

    public void addStatsListener(StatsListener listener) {
        statListeners.add(StatsListener.class, listener);
    }

    public void fireStatsChanged(HashMap<String, Double> stats) {
        if (statListeners != null) {
            StatsListener[] list = statListeners.getListeners(StatsListener.class);
            for (StatsListener l : list) {
                l.statsChanged(stats);
            }
        }
    }

    public void search(NodeImpl a, NodeImpl b) {
        from = a;
        to = b;
        updateLayer();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runSearch(from, to, alg);
            }
        });

    }

    private class AlgorithmRunner extends Thread implements Runnable {

        private AbstractAlgorithm algorithm;
        private List<Node> path = null;
        private Context ctx;

        public AlgorithmRunner(Context ctx, AbstractAlgorithm algorithm) {
            this.algorithm = algorithm;
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (algorithm instanceof UninformedSearch) {
                path = algorithm.findPath(ctx.getStartNode());
            } else if (algorithm instanceof InformedSearch) {
                path = algorithm.findPath(ctx.getStartNode(), ctx.getTargetNode());
            } else {
                throw new RuntimeException("Algorithm must implement either UninformedSearch or InformedSearch");
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    double dist = highlightPath(path);
                    double expanded = ctx.getExpandCalls();
                    double cov = expanded / (double) visInfo.getNodesCount() * 100;
                    stats.put("explored", (double) ctx.getExploredNodes());
                    stats.put("expanded", expanded);
                    stats.put("coverage", cov);
                    stats.put("distance", dist);
                    fireStatsChanged(stats);
                    highlightPoint(from, startPoint);
                    highlightPoint(to, endPoint);
                }
            });

        }
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    
}
