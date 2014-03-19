package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.Edge;
import cz.cvut.fit.zum.data.StateSpace;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public class LoaderContext implements TaskContext {

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

    @Override
    public boolean isTarget(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFinish(boolean interrupt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void panelResize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
