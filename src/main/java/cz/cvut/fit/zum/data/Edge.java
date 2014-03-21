package cz.cvut.fit.zum.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Edge class representing a road between two nodes (cities) on the map.
 * Provides references to its start/end node.
 *
 * @author Tomas Barton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Edge")
public class Edge {

    @XmlAttribute(required = true)
    protected int fromId;
    @XmlAttribute(required = true)
    protected double length;
    @XmlAttribute(required = true)
    protected int toId;


    /**
     * Gets the index of the source node, counting from 0 to |Nodes|-1.
     * @return Index of the source node
     */
    public int getFromId() {
        return this.fromId;
    }

    /**
     * Sets the index of the source node, counting from 0
     * @param value The new source node's index
     */
    public void setFromId(int value) {
        this.fromId = value;
    }

    /**
     * Gets the index of the destination node, counting from 0 to |Nodes|-1.
     * @return Index of the destination node
     */
    public int getToId() {
        return this.toId;
    }

    /**
     * Sets the index of the destination node, counting from 0
     * @param value The new destination node's index
     */
    public void setToId(int value) {
        this.toId = value;
    }

    /**
     * Test whether this is the same edge as the other given as the argument.
     * @param obj Other object to be tested for equality to the current
     * @return <code>true</code> is the argument equals to edge,
     * <code>false</code> if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge other = (Edge) obj;
            if ((this.getFromId() == other.getFromId()) && (this.getToId() == other.getToId())) {
                return true;
            }
            if ((this.getFromId() == other.getToId()) && (this.getToId() == other.getFromId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Computes a hash value of the edge.
     * @return The hash value
     */
    @Override
    public int hashCode() {
        int hash = Math.max(fromId, toId);
        hash = 83 * hash + Math.min(fromId, toId);
        return hash;
    }
}