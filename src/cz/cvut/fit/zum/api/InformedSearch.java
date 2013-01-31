package cz.cvut.fit.zum.api;

import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public interface InformedSearch extends Algorithm {

    /**
     * Should find shortest path in graph when given source Node and target
     * Node, approximate distance from target could be checked with Node.getX()
     * and Node.getY()
     *
     * @see Node
     *
     * @param source
     * @param target
     * @return shortest path of Nodes from source to target
     */
    public List<Node> findPath(Node source, Node target);
}
