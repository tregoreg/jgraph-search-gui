package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.VisInfo;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.data.Edge;
import cz.cvut.fit.zum.data.StateSpace;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Tomas Barton
 */
public class VertexContext extends SwingWorker<Void, HighlightTask> implements TaskContext {

    private AbstractEvolution evolution;
    private long startTime, endTime;
    private SearchLayer layer;
    private BufferedImage notCovered;
    private BufferedImage coveredPoint;
    private BufferedImage reachablePoint;
    private Color edge = Color.BLUE;
    private Color nodeColor = new Color(0.0f, 0.0f, 0.0f);  // semi-transparent
    private HashSet<Node> cover;
    private HashSet<Node> reachable;
    private HashSet<Node> unreachable;
    private double bestFitness;
    protected HashMap<String, Double> stats = new HashMap<String, Double>();

    public VertexContext(SearchLayer layer, AbstractEvolution evolution) {
        this.evolution = evolution;
        this.layer = layer;
        cover = new HashSet<Node>(StateSpace.nodesCount());
        reachable = new HashSet<Node>(StateSpace.nodesCount() / 2);
        unreachable = new HashSet<Node>(StateSpace.nodesCount());
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
        updateStats();
        layer.enableSearchButton();
    }

    public long getTime() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public List<Node> expand(Node node) {
        List<Edge> edges = node.getEdges();
        ArrayList<Node> result = new ArrayList<Node>();
        Node node2;
        for (Edge e : edges) {
            int toId = e.getToId();
            node2 = StateSpace.getNode(toId);
            result.add(node2);
        }
        return result;
    }

    public void markNode(Node current, boolean covered) {
        if (covered) {
            publish(new HighlightPoint(layer, current, coveredPoint));
            cover.add(current);
            reachable.remove(current); //remove if present
            unreachable.remove(current);
            List<Node> nn = current.expand();
            for (Node to : nn) {
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

            List<Node> nn = current.expand();
            boolean neigourCovered = false;
            for (Node to : nn) {
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

    public AbstractEvolution getEvolution() {
        return evolution;
    }

    @Override
    public boolean isTarget(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFinish(boolean interrupt) {
        cancel(interrupt);
    }
}