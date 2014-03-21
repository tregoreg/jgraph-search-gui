package cz.cvut.fit.zum.data;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.gui.LoaderContext;
import java.util.ArrayList;
import java.util.List;

/**
 * State space class providing access to all the Nodes and Edges present in
 * the map.
 * 
 * @author Tomas Barton
 */
public class StateSpace {

    private static List<Node> nodes;
    private static List<Edge> edges = new ArrayList<Edge>();

    /**
     * Sets the list of the map's nodes.
     * 
     * @param n The list of nodes
     */
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

    /**
     * Iterates over the list of nodes and explores the neighborhood relations,
     * creating a list of edges.
     */
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

    /**
     * Gets the total number of edges on the map.
     * @return The number of nodes
     */
    public static int nodesCount() {
        if (nodes != null) {
            return nodes.size();
        }
        return 0;
    }

    /**
     * Gets the total number of edges (roads) on the map.
     * @return The number of edges
     */
    public static int edgesCount() {
        return edges.size();
    }

    /**
     * Gets a reference to the list of all nodes.
     * @return The list of all nodes
     */
    public static List<Node> getNodes() {
        return nodes;
    }

   /**
    * Gets a reference to a list of all edges.
    * @return The list of all edges
    */
    public static List<Edge> getEdges() {
        return edges;
    }

    /**
     * Gets a node at given index, counting from 0 to |Nodes|-1
     * @param idx Index of the node to be retrieved
     * @return The Node object at the index given
     */
    public static Node getNode(int idx) {
        return nodes.get(idx);
    }

    /**
     * Gets an edge at given index, counting from 0 to |Edges|-1
     * @param idx Index of the edge to be retrieved
     * @return The Edge object at the index given
     */
    public static Edge getEdge(int idx) {
        return edges.get(idx);
    }
}
