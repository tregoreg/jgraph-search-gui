package cz.cvut.fit.zum.gui;

import java.util.List;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.NodeImpl;

/**
 *
 * @author Tomas Barton
 */
public class Context {

    private final List<NodeImpl> nodes;
    private final NodeImpl startNode;
    private final NodeImpl endNode;
    private SearchLayer layer;
    private int expandCalls;
    private int exploredNodes;
    private int targetCheck;
    private long delay;
    private boolean stop = false;

    public Context(List<NodeImpl> nodes, NodeImpl startNode, NodeImpl endNode, SearchLayer layer, long delay) {
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

    public void highlightEdge(NodeImpl start, NodeImpl end) {
        layer.higlightEdge(start, end);
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
        layer.repaint(); //after exploring some node we repaint the layer
    }

    public void targetCheck(NodeImpl node) {
        targetCheck++;
       if (node.getId() != startNode.getId()) {
            layer.markCheckedPoint(node);
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
    }
}