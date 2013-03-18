package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.gui.SearchLayer;
import cz.cvut.fit.zum.data.VertexContext;

/**
 *
 * @author Tomas Barton
 */
public class VertexCoverTask {

    private VertexContext vctx;
    private AbstractEvolution evolution;
    private double mutation = 0.3;
    private double crossover = 0.4;
    private int population;
    private int generations;

    public VertexCoverTask(SearchLayer sLayer, AbstractEvolution evolution) {
        sLayer.updateLayer();
        vctx = new VertexContext(sLayer, evolution);
        evolution.setVertexContext(vctx);
        evolution.setMutationProbability(mutation);
        evolution.setCrossoverProbability(crossover);
        
        this.evolution = evolution;
    }

    public void run() {
        if (vctx != null) {
            vctx.execute();
        }
    }

    public void setMutationProbability(int value) {
        mutation = ((double) value) / 100.0;
        this.evolution.setMutationProbability(mutation);
    }

    public void setCrossoverProbability(int value) {
        crossover = ((double) value) / 100.0;
        this.evolution.setCrossoverProbability(crossover);
    }
    
    public void setPopulationSize(int size){
        population = size;
        evolution.setPopulationSize(size);
    }
    
    public void setGenerations(int generations){
        this.generations = generations;
        evolution.setGenerations(generations);
    }
    
    public void setFinish(boolean b){
        evolution.setFinished(b);
        vctx.cancel(true);
    }
    
    public boolean isFinished(){
        if(vctx == null){
            return true;
        }
        return vctx.isDone();
    }
            
}
