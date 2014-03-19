package cz.cvut.fit.zum.data;

import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 * @author Tomas Barton
 */
@XmlRegistry
public class ObjectFactory {

    public Nodes createNodes() {
        return new Nodes();
    }

    public Edge createEdge() {
        Edge e = new Edge();
        return e;
    }

    public NodeImpl createNode() {
        return new NodeImpl();
    }
}