package cz.cvut.fit.zum.data;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.gui.LoaderContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public class StateSpace {

    private static List<Node> nodes;
    private static List<Edge> edges = new ArrayList<Edge>();

    public static void setNodes(List<NodeImpl> n) {

        NodeImpl.setContext(new LoaderContext());
        
        CollectionTransformer transformer = new CollectionTransformer<NodeImpl, Node>() {
            @Override
            Node transform(NodeImpl e) {
                return e;
            }
        };

        nodes = transformer.transform(n);
        updateEdges();
    }

    private static void updateEdges() {
        for (int n = 0; n < nodes.size(); n++) {
            Node node = nodes.get(n);
            List<Node> neighbours = node.expand();
            for (int s = 0; s < neighbours.size(); s++) {
                Edge edge = new Edge();
                edge.setFromId(node.getId());
                edge.setToId(neighbours.get(s).getId());
                if (!edges.contains(edge)) {
                    edges.add(edge);
                }
            }
        }
        System.out.println("edges size: " + edges.size());
    }

    public static int nodesCount() {
        if (nodes != null) {
            return nodes.size();
        }
        return 0;
    }

    public static int edgesCount() {
        return edges.size();
    }

    public static List<Node> getNodes() {
        return nodes;
    }

    public static List<Edge> getEdges() {
        return edges;
    }

    public static Node getNode(int id) {
        return nodes.get(id);
    }

    public static Edge getEdge(int id) {
        return edges.get(id);
    }
}
