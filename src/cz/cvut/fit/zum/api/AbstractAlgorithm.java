package cz.cvut.fit.zum.api;

import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public abstract class AbstractAlgorithm implements Algorithm {

    @Override
    public abstract String getName();

    public List<Node> findPath(Node startNode) {
        startNode.expand();

        return null;
    }

    public List<Node> findPath(Node source, Node target) {
        source.expand();
        return null;
    }
}
