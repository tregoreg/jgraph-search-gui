package cz.cvut.fit.zum.data;

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
import cz.cvut.fit.zum.gui.Context;
import org.openide.util.Exceptions;

/**
 * Represents inner information about nodes
 *
 * @author Tomas Barton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"edge"})
@XmlRootElement(name = "Node")
public class NodeImpl implements Node {

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
    private static Context context;

    public List<Edge> getEdge() {
        if (this.edge == null) {
            this.edge = new ArrayList<Edge>();
        }
        return this.edge;
    }

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

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public static void setContext(Context ctx) {
        context = ctx;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public List<Node> expand() {
        context.expandCalled();
        List<Edge> edges = getEdge();
        ArrayList<Node> result = new ArrayList<Node>();
        NodeImpl node2;
        for (Edge e : edges) {
            int toId = e.getToId();
            node2 = context.getNode(toId);
            if (node2 == null) {
                System.err.println("Node with id " + toId + " is not in node list!");
            } else {
                context.highlightEdge(this, node2);
                result.add(node2);
            }
        }
        //if we have to stop
        if(context.isStop()){
            throw new RuntimeException("Algorithm stopped by the user");
        }
        context.incExplored(result.size());
        try {
            Thread.sleep(context.getDelay());
        } catch (InterruptedException e) {
            Exceptions.printStackTrace(e);
        }
        
        return result;
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
        context.targetCheck(this);
        return context.isFinal(this.getId());
    }
}
