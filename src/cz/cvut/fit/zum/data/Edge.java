package cz.cvut.fit.zum.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Tomas Barton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Edge")
public class Edge {

    @XmlTransient
    private int id;
    @XmlAttribute(required = true)
    protected int fromId;
    @XmlAttribute(required = true)
    protected double length;
    @XmlAttribute(required = true)
    protected int toId;

    
    public void setId(int id){
        this.id = id;
    }

    public int getFromId() {
        return this.fromId;
    }

    public void setFromId(int value) {
        this.fromId = value;
    }

    public double getLength() {
        return this.length;
    }

    public void setLength(double value) {
        this.length = value;
    }

    public int getToId() {
        return this.toId;
    }

    public void setToId(int value) {
        this.toId = value;
    }

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }
}