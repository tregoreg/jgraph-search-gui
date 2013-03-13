package cz.cvut.fit.zum;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.Edge;
import cz.cvut.fit.zum.data.NodeImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public class StateSpace {

    private static List<NodeImpl> nodes;
    private static List<Edge> edges = new ArrayList<Edge>();

    protected static void setNodes(List<NodeImpl> n) {
        nodes = n;
        updateEdges();
    }

    private static void updateEdges() {
        int e = 0;
        for (int n = 0; n < nodes.size(); n++) {
            NodeImpl node = (NodeImpl) nodes.get(n);
            List<Node> neighbours = node.expand();
            for (int s = 0; s < neighbours.size(); s++) {
               /* Edge tmp = new Edge();
                tmp.setFromId(node.getId());
                tmp.setToId(((NodeImpl) neighbours.get(s)).getId());
                EdgeWrapper ew = new EdgeWrapper(e, tmp);*/
                Edge edge = getEdge(e);
                if (!edges.contains(edge)) {
                    edges.add(edge);
                }
                e++;
            }
        }
    }

    public static int nodesCount() {
        if (nodes != null) {
            return nodes.size();
        }
        return 0;
    }
    
    public static int edgesCount(){
        return edges.size();
    }

    public static List<NodeImpl> getNodes() {
        return nodes;
    }

    public static NodeImpl getNode(int id) {
        return nodes.get(id);
    }
    
    public static Edge getEdge(int id){
        return edges.get(id);
    }
}
