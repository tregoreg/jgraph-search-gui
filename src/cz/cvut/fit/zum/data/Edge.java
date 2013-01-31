package cz.cvut.fit.zum.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
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
}