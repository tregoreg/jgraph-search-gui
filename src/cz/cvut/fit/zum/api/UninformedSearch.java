package cz.cvut.fit.zum.api;

import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public interface UninformedSearch extends Algorithm {
    /**
     * The only way how to find out whether you've reached your destination is checking
     * Node#isTarget()
     * 
     * @see Node
     * @param start
     * @return List of nodes with shortest path found
     */
    public List<Node> findPath(Node start);
}
