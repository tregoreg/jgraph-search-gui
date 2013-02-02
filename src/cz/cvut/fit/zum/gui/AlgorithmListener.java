package cz.cvut.fit.zum.gui;

import java.util.EventListener;
import java.util.HashMap;

/**
 *
 * @author Tomas Barton
 */
public interface AlgorithmListener extends EventListener {
    
    public void searchStarted();
    
    public void searchFinished();

    public void statsChanged(HashMap<String, Double> stats);
}
