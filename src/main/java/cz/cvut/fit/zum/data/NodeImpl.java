package cz.cvut.fit.zum.data;

import cz.cvut.fit.zum.gui.TaskContext;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import cz.cvut.fit.zum.api.Node;

/**
 * Represents inner information about nodes
 *
 * @author Tomas Barton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"edge"})
@XmlRootElement(name = "Node")
public final class NodeImpl implements Node {

    @XmlElement(name = "Edge", required = true)
    protected List<Edge> edge;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected double radius;
    @XmlAttribute(required = true)
    protected double x;
    @XmlAttribute(required = true)
    protected double y;
    /**
     * Precomputed position in 2D system
     */
    @XmlTransient
    private Point2D point;
    @XmlTransient
    private static TaskContext context;

    @Override
    public List<Edge> getEdges() {
        if (this.edge == null) {
            this.edge = new ArrayList<Edge>();
        }
        return this.edge;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double value) {
        this.radius = value;
    }

    @Override
    public double getX() {
        return this.x;
    }

    public void setX(double value) {
        this.x = value;
    }

    @Override
    public double getY() {
        return this.y;
    }

    public void setY(double value) {
        this.y = value;
    }

    @Override
    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public static void setContext(TaskContext ctx) {
        context = ctx;
    }

    public static TaskContext getContext() {
        return context;
    }

    /**
     * For each task might behave differently
     *
     * @return List of neighbor nodes
     */
    @Override
    public List<Node> expand() {
        return context.expand(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NodeImpl)) {
            return false;
        }
        NodeImpl nodeImpl = (NodeImpl) obj;
        if (getId() != nodeImpl.getId()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean isTarget() {
        return context.isTarget(this);        
    }
}
