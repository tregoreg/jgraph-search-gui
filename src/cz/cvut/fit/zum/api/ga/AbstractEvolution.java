package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.data.NodeImpl;
import cz.cvut.fit.zum.data.StateSpace;
import cz.cvut.fit.zum.gui.VertexContext;
import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public abstract class AbstractEvolution implements Runnable {

    // EVOLUTION CONFIGURATION
    /**
     * Count of generations
     */
    protected int generationSize = 100;
    /**
     * Size of population
     */
    protected int populationSize = 20;
    /**
     * Probability of mutation
     */
    protected double mutationProbability = 0.5;
    /**
     * Probability of cross
     */
    protected double crossoverProbability = 0.5;
    protected List<NodeImpl> nodes = null;
    private VertexContext context;
    private boolean[] currentCover;

    /**
     * Reference to GUI
     *
     * @param vcx
     */
    protected void setVertexContext(VertexContext vcx) {
        context = vcx;
        currentCover = new boolean[StateSpace.nodesCount()];
        nodes = StateSpace.getNodes();
    }

    public List<NodeImpl> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeImpl> nodes) {
        this.nodes = nodes;
    }

    public abstract String getName();

    public int getGenerationSize() {
        return generationSize;
    }

    public void setGenerationSize(int generationSize) {
        this.generationSize = generationSize;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public void setBestIndividual(AbstractIndividual best) {
        boolean covered;
        for(NodeImpl node: nodes){
            covered = best.isVertexCovered(node.getId());
            //something is different, update map
            if(covered != currentCover[node.getId()]){
                context.markNode(node, covered);
            }
            
        }
    }
    
}
