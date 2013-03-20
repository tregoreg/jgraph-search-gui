package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.api.Node;
import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public interface TaskContext {

    public List<Node> expand(Node node);

    public boolean isTarget(Node node);
    
    /**
     * 
     * @param interrupt 
     */
    public void setFinish(boolean interrupt);
    
    public void panelResize();
}
