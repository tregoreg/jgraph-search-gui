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
        return new Edge();
    }

    public NodeImpl createNode() {
        return new NodeImpl();
    }
}