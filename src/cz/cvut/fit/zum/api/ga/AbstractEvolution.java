package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.data.NodeImpl;
import cz.cvut.fit.zum.data.StateSpace;
import cz.cvut.fit.zum.data.VertexContext;
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
    protected int generations = 100;
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
    protected boolean isFinished = false;

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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
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

    public void setMutationProbability(double prob) {
        this.mutationProbability = prob;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(double prob) {
        this.crossoverProbability = prob;
    }

    public void updateMap(AbstractIndividual best) {
        boolean covered;
        int id;

        context.setBestFitness(best.getFitness());
        for (NodeImpl node : nodes) {
            covered = best.getGen(node.getId());
            id = node.getId();
            //something is different, update map
            if (covered != currentCover[id]) {
                context.markNode(node, covered);
                currentCover[id] = covered;
            }
        }
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append("[");
        sb.append("generations = ").append(getGenerations()).append("\n");
        sb.append("population = ").append(getPopulationSize()).append("\n");
        sb.append("mutation = ").append(getMutationProbability()).append("\n");
        sb.append("crossover = ").append(getCrossoverProbability()).append("\n");
        sb.append("]");
        return sb.toString();
    }
}
