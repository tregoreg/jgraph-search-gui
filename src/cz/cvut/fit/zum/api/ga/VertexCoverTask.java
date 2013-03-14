package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.gui.SearchLayer;
import cz.cvut.fit.zum.gui.VertexContext;

/**
 *
 * @author Tomas Barton
 */
public class VertexCoverTask {
    
    private VertexContext vctx;

    public VertexCoverTask(SearchLayer sLayer, AbstractEvolution evolution) {
        vctx = new VertexContext(sLayer, evolution);
        evolution.setVertexContext(vctx);
        vctx.execute();
    }
}
