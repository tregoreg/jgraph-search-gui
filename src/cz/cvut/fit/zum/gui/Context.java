package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.api.AbstractAlgorithm;
import cz.cvut.fit.zum.api.Algorithm;
import cz.cvut.fit.zum.api.InformedSearch;
import java.util.List;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.UninformedSearch;
import cz.cvut.fit.zum.data.NodeImpl;
import javax.swing.SwingWorker;

/**
 *
 * @author Tomas Barton
 */
public class Context extends SwingWorker<Void, HighlightTask> {

    private Algorithm algorithm;
    private final List<NodeImpl> nodes;
    private final NodeImpl startNode;
    private final NodeImpl endNode;
    private SearchLayer layer;
    private int expandCalls;
    private int exploredNodes;
    private int targetCheck;
    private long delay;
    private boolean stop = false;
    private List<Node> path;
    private long startTime, endTime;

    public Context(AbstractAlgorithm algorithm, List<NodeImpl> nodes, NodeImpl startNode, NodeImpl endNode, SearchLayer layer, long delay) {
        this.algorithm = algorithm;
        this.nodes = nodes;
        this.layer = layer;
        this.startNode = startNode;
        this.endNode = endNode;
        this.expandCalls = 0;
        this.exploredNodes = 0;
        this.targetCheck = 0;
        this.delay = delay;
    }

    public Node getStartNode() {
        return this.startNode;
    }

    public Node getTargetNode() {
        return this.endNode;
    }

    public List<NodeImpl> getNodes() {
        return nodes;
    }

    public final NodeImpl getNode(int id) {
        return nodes.get(id);
    }

    public boolean isFinal(int id) {
        return id == this.endNode.getId();
    }

    /**
     * Delayed painting
     *
     * @param start
     * @param end
     */
    public void highlightEdge(NodeImpl start, NodeImpl end) {
        publish(new HighlightEdge(layer, start, end));
    }

    public void expandCalled() {
        expandCalls++;
    }

    public int getExpandCalls() {
        return expandCalls;
    }

    public int getExploredNodes() {
        return exploredNodes;
    }

    public void incExplored(int exp) {
        exploredNodes += exp;

    }

    public void targetCheck(NodeImpl node) {
        targetCheck++;
        if (node.getId() != startNode.getId()) {
            publish(new HighlightCheckedPoint(layer, node));
        }
    }

    public int getTargetCheck() {
        return targetCheck;
    }

    public long getDelay() {
        return delay;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
        if (stop) {
            cancel(true);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        startTime = System.currentTimeMillis();
        endTime = 0;
        if (algorithm instanceof UninformedSearch) {
            path = ((UninformedSearch) algorithm).findPath(startNode);
        } else if (algorithm instanceof InformedSearch) {
            path = ((InformedSearch) algorithm).findPath(startNode, endNode);
        } else {
            throw new RuntimeException("Algorithm must implement either UninformedSearch or InformedSearch");
        }

        layer.searchFinished = true;
        stop = true;

        endTime = System.currentTimeMillis();
        layer.fireAlgEvent(AlgorithmEvents.FINISHED);

        return null;
    }

    @Override
    protected void process(List<HighlightTask> pairs) {
        while (!pairs.isEmpty()) {
            HighlightTask task = pairs.remove(0);
            task.process();
        }
        layer.repaint(); //after exploring some node we repaint the layer
        layer.updateStats(this);
    }

    @Override
    protected void done() {
        layer.highlightPoint(startNode, layer.startPoint);
        layer.highlightPoint(endNode, layer.endPoint);
        double dist = layer.highlightPath(path);
        double expanded = getExpandCalls();
        double cov = expanded / (double) layer.visInfo.getNodesCount() * 100;
        long time = endTime - startTime;
        layer.stats.put("explored", (double) getExploredNodes());
        layer.stats.put("expanded", expanded);
        layer.stats.put("coverage", cov);
        layer.stats.put("distance", dist);
        layer.stats.put("time", (double) time);
        layer.fireStatsChanged(layer.stats);
        layer.repaint();

        System.out.println("Search finished, time = " + time + " ms");
    }

    public long getTime() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }
}