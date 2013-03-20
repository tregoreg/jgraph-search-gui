package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.gui.SearchLayer;
import cz.cvut.fit.zum.data.VertexContext;

/**
 * Keeps context (actual settings from GUI)
 *
 * @author Tomas Barton
 */
public class VertexCoverTask {

    private VertexContext vctx;
    private double mutation;
    private double crossover;
    private int population;
    private int generations;
    private SearchLayer sLayer;

    public VertexCoverTask(SearchLayer sLayer) {
        sLayer.updateLayer();
        this.sLayer = sLayer;

    }

    public void run(AbstractEvolution evolution) {
        evolution.setMutationProbability(mutation);
        evolution.setCrossoverProbability(crossover);
        evolution.setPopulationSize(population);
        evolution.setGenerations(generations);
        sLayer.updateLayer();
        //creates a new thread
        vctx = new VertexContext(sLayer, evolution);
        evolution.setVertexContext(vctx);
        vctx.execute();
    }

    public void setMutationProbability(int value) {
        mutation = ((double) value) / 100.0;
    }

    public void setCrossoverProbability(int value) {
        crossover = ((double) value) / 100.0;
    }

    public void setPopulationSize(int size) {
        population = size;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public void setFinish(boolean b) {
        vctx.getEvolution().setFinished(b);
        vctx.cancel(true);
    }

    public boolean isFinished() {
        if (vctx == null) {
            return true;
        }
        return vctx.isDone();
    }
}
