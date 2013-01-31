package cz.cvut.fit.zum.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Tomas Barton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"node"})
@XmlRootElement(name = "Nodes")
public class Nodes {

    @XmlElement(name = "Node")
    protected List<NodeImpl> node;

    public List<NodeImpl> getNodes() {
        if (this.node == null) {
            this.node = new ArrayList<NodeImpl>();
        }
        return this.node;
    }
}
