package cz.cvut.fit.zum.data;

import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 * @author Tomas Barton
 */
@XmlRegistry
public class ObjectFactory {

    private static int idCnt = 0;

    public Nodes createNodes() {
        return new Nodes();
    }

    public Edge createEdge() {
        Edge e = new Edge();
        e.setId(idCnt++);
        return e;
    }

    public NodeImpl createNode() {
        return new NodeImpl();
    }
}