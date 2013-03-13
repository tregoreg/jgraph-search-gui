package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import javax.swing.SwingWorker;

/**
 *
 * @author Tomas Barton
 */
public class VertexContex extends SwingWorker<Void, HighlightTask> {

    private AbstractEvolution evolution;

    public VertexContex(AbstractEvolution evolution) {
        this.evolution = evolution;
    }

    @Override
    protected Void doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
