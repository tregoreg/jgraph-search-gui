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
        return new Edge(idCnt++);
    }

    public NodeImpl createNode() {
        return new NodeImpl();
    }
}