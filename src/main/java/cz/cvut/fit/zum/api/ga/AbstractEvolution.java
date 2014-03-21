package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.StateSpace;
import cz.cvut.fit.zum.gui.VertexContext;
import java.util.List;

/**
 * Abstract superclass for evolutionary algorithm. Students may inherit one
 * or more subclasses, creating their own evolutionary algorithms.
 * 
 * @author Tomas Barton
 */
public abstract class AbstractEvolution<Individual extends AbstractIndividual> implements Runnable {

    // EVOLUTION CONFIGURATION
    /**
     * Number of generations
     */
    protected int generations = 100;
    
    /**
     * Size of the population
     */
    protected int populationSize = 20;
    
    /**
     * Probability of mutation
     */
    protected double mutationProbability = 0.02;
    
    /**
     * Probability of crossover
     */
    protected double crossoverProbability = 0.5;
    
    protected List<Node> nodes = null;
    private VertexContext context;
    private boolean[] currentCover;
    protected boolean isFinished = false;

    /**
     * Sets a reference to the GUI
     *
     * @param vcx
     */
    protected void setVertexContext(VertexContext vcx) {
        context = vcx;
        currentCover = new boolean[StateSpace.nodesCount()];
        nodes = StateSpace.getNodes();
    }

    /**
     * Gets the list of graph nodes.
     * 
     * @return Reference to the list of nodes.
     */
    protected List<Node> getNodes() {
        return nodes;
    }
    
    /**
     * Gets the number of nodes in the graph.
     * 
     * @return Number of nodes
     */
    public int getNodesCount() {
        return nodes.size();
    }

    /**
     * Sets the list of graph nodes.
     * 
     * @param nodes 
     */
    protected void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Get the name identifier of the evolutionary algorithm (to be displayed
     * in the GUI).
     * 
     * @return The name of the algorithm, e.g. "My evolution"
     */
    public abstract String getName();

    /**
     * Tests whether the evolutionary algorithm is finished.
     * 
     * @return <code>true</code> is the evolution is finished, <code>false</code>
     * if the evolution is still running
     */
    protected boolean isFinished() {
        return isFinished;
    }

    /**
     * Sets whether the evolutionary algorithms is finished.
     * 
     * @param isFinished <code>true</code> is the evolution is finished
     */
    protected void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * Gets the number of generations that the evolutionary algorithms shall
     * run for.
     * 
     * @return Number of generations
     */
    public int getGenerations() {
        return generations;
    }

    /**
     * Sets the number of generations that the evolutionary algorithm shall
     * run for.
     * 
     * @param generations Number of generations
     */
    protected void setGenerations(int generations) {
        this.generations = generations;
    }

    /**
     * Gets the number of individuals in the population used by the evolutionary
     * algorithm.
     * 
     * @return Size of the population
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * Sets the number of individuals in the population used by the evolutionary
     * algorithm.
     * 
     * @param populationSize Size of the population
     */
    protected void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    /**
     * Gets the mutation probability used by the evolutionary algorithm. Meaning
     * of this number is ambiguous, depending on the actual type of EA being
     * used. In case of genetic algorithm, it may encode the probability of
     * bit-flip mutation for each bit.
     * 
     * @return The current mutation probability
     */
    public double getMutationProbability() {
        return mutationProbability;
    }

    /**
     * Sets the mutation probability to be used by the evolutionary algorithm.
     * Meaning of this number is ambiguous, depending on the actual type of EA
     * being used. In case of genetic algorithm, it may encode the probability
     * of bit-flip mutation for each bit.
     * 
     * @param prob The new mutation probability
     */
    protected void setMutationProbability(double prob) {
        this.mutationProbability = prob;
    }

    /**
     * Gets the crossover probability used by the evolutionary algorithm. This
     * is the probability of selected the individuals to be crossed over before
     * being mutated.
     * 
     * @return The current crossover probability
     */
    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    /**
     * Sets the crossover probability to be used by the evolutionary algorithm.
     * This is the probability of the selected individuals to be crossed over
     * before being mutated.
     * 
     * @param prob The new crossover probability
     */
    protected void setCrossoverProbability(double prob) {
        this.crossoverProbability = prob;
    }

    /**
     * Updates the GUI map, visualizing the elite individual given.
     * 
     * @param best The current elite
     */
    protected void updateMap(AbstractIndividual best) {
        boolean covered;

        context.setBestFitness(best.getFitness());
        for (int i = 0; i < StateSpace.nodesCount(); i++) {
            covered = best.isNodeSelected(i);            
            //something is different, update map
            if (covered != currentCover[i]) {
                context.markNode(StateSpace.getNode(i), covered);
                currentCover[i] = covered;
            }
        }

    }
    
    /**
     * Redraws the number of generations in the GUI.
     * 
     * @param num Number of the current generation
     */
    protected void updateGenerationNumber(int num){
        context.updateGeneration(num);
    }

    @Override
    public String toString() {
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
