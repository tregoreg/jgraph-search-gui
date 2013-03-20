package cz.cvut.fit.zum.data;

import cz.cvut.fit.zum.VisInfo;
import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.gui.HighlightEdge;
import cz.cvut.fit.zum.gui.HighlightPoint;
import cz.cvut.fit.zum.gui.HighlightTask;
import cz.cvut.fit.zum.gui.SearchLayer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Tomas Barton
 */
public class VertexContext extends SwingWorker<Void, HighlightTask> {

    private AbstractEvolution evolution;
    private long startTime, endTime;
    private SearchLayer layer;
    private BufferedImage notCovered;
    private BufferedImage coveredPoint;
    private BufferedImage reachablePoint;
    private Color edge = Color.BLUE;
    private Color nodeColor = new Color(0.0f, 0.0f, 0.0f);  // semi-transparent
    private HashSet<NodeImpl> cover;
    private HashSet<NodeImpl> reachable;
    private HashSet<NodeImpl> unreachable;
    private double bestFitness;
    protected HashMap<String, Double> stats = new HashMap<String, Double>();

    public VertexContext(SearchLayer layer, AbstractEvolution evolution) {
        this.evolution = evolution;
        this.layer = layer;
        cover = new HashSet<NodeImpl>(StateSpace.nodesCount());
        reachable = new HashSet<NodeImpl>(StateSpace.nodesCount() / 2);
        unreachable = new HashSet<NodeImpl>(StateSpace.nodesCount());
        unreachable.addAll(StateSpace.getNodes());
        VisInfo visInfo = VisInfo.getInstance();
        notCovered = visInfo.createCircle(nodeColor);
        coveredPoint = visInfo.createCircle(Color.GREEN);
        reachablePoint = visInfo.createCircle(Color.BLUE);
    }

    @Override
    protected Void doInBackground() throws Exception {
        startTime = System.currentTimeMillis();
        endTime = startTime;
        evolution.run();

        endTime = System.currentTimeMillis();
        return null;
    }

    @Override
    protected void process(List<HighlightTask> pairs) {
        while (!pairs.isEmpty()) {
            HighlightTask task = pairs.remove(0);
            task.process();
        }
        layer.repaint(); //after exploring some node we repaint the layer
        //  layer.updateStats(this);
    }

    @Override
    protected void done() {
        long time = endTime - startTime;
        System.out.println("Evolution time = " + time + " ms");
    }

    public long getTime() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    public void markNode(NodeImpl current, boolean covered) {
        if (covered) {
            publish(new HighlightPoint(layer, current, coveredPoint));
            cover.add(current);
            reachable.remove(current); //remove if present
            unreachable.remove(current);
            List<NodeImpl> nn = current.fastExpand(StateSpace.getNodes());
            for (NodeImpl to : nn) {
                //highlight edge

                //both edge's nodes are covered
                if (cover.contains(to)) {
                    publish(new HighlightEdge(layer, current, to, Color.YELLOW));
                } else {
                    publish(new HighlightEdge(layer, current, to, Color.GREEN));
                    //point
                    publish(new HighlightPoint(layer, to, reachablePoint));
                    reachable.add(to);
                    unreachable.remove(to);
                }
            }

        } else {
            cover.remove(current);

            List<NodeImpl> nn = current.fastExpand(StateSpace.getNodes());
            boolean neigourCovered = false;
            for (NodeImpl to : nn) {
                //from is not in cover
                if (cover.contains(to)) {
                    neigourCovered = true;
                    publish(new HighlightEdge(layer, current, to, Color.GREEN));
                } else {
                    publish(new HighlightEdge(layer, current, to, Color.RED));
                }
            }

            if (neigourCovered) {
                publish(new HighlightPoint(layer, current, reachablePoint));
                unreachable.remove(current);
            } else {
                publish(new HighlightPoint(layer, current, notCovered));
                reachable.remove(current);
                unreachable.add(current);
            }
        }
        updateStats();
    }

    protected void updateStats() {
        double covSize = cover.size();
        double cov = covSize / (double) StateSpace.nodesCount() * 100;
        stats.put("cover", covSize);
        stats.put("fitness", bestFitness);
        stats.put("coverage", cov);
        stats.put("reached", (double) reachable.size());
        stats.put("unreachable", (double) unreachable.size());
        stats.put("time", (double) getTime());

        layer.fireEvolutionChanged(stats);
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }
}
