package cz.cvut.fit.zum.gui;

import java.util.EventListener;
import java.util.HashMap;

/**
 *
 * @author Tomas Barton
 */
public interface EvolutionListener extends EventListener {

    public void statsChanged(HashMap<String, Double> stats);
}
