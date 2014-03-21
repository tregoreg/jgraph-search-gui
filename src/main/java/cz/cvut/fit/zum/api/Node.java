package cz.cvut.fit.zum.api;

/**
 * Class representing a node of a map.
 * 
 * @author Tomas Barton
 */
import cz.cvut.fit.zum.data.Edge;
import java.awt.geom.Point2D;
import java.util.List;

public abstract interface Node {

    /**
     * Returns the index of the node in the graph, starting from 0.
     * 
     * @return unique index of the node
     */
    public int getId();

    /**
     * The X-coordinate of the node on a map from range [0.0; 1.0]
     *
     * @return The x-coordinate
     */
    public abstract double getX();

    /**
     * The Y-coordinate of the node on a map from range [0.0; 1.0]
     *
     * @return The y-coordinate
     */
    public abstract double getY();

    /**
     * Return List of directly connected Nodes to this node
     *
     * @return List of neighbor Nodes
     */
    public abstract List<Node> expand();

    /**
     * Test whether this is a target node for a path-searching algorithm.
     *
     * @return <code>true</code> if this is the target, <code>false</code>
     * otherwise
     */
    public abstract boolean isTarget();

    /**
     * Return true when other is the same as this, otherwise false
     *
     * @param other The other node to be tested for equality with the current
     * @return <code>true</code> when other is same as this object
     */
    @Override
    public abstract boolean equals(Object other);

    @Override
    public int hashCode();

    /**
     * Returns the (X,Y)-coordinates of the node as a <code>Point2D</code>
     * object.
     * 
     * @return The coordinates object of the node
     */
    public abstract Point2D getPoint();
    
    /**
     * Gets the list of edges which are in incidence with (i.e. start or end in)
     * the node.
     * 
     * @return List of Node's edges
     */
    public List<Edge> getEdges();
}