package cz.cvut.fit.zum.api.ga;

import cz.cvut.fit.zum.util.Pair;

/**
 * Abstract class for individuals in evolutionary algorithm. It defines the
 * genotype, among with related operations such as the mutation and the
 * crossover, as well as computing fitness.
 * 
 * @author Tomas Barton
 */
public abstract class AbstractIndividual implements Comparable<AbstractIndividual> {

    /**
     * Gets the fitness value of the individual. Make sure
     * <code>computeFitness</code> is always invoked prior to getting the
     * fitness value.
     * 
     * @return The fitness value of the current individual
     */
    public abstract double getFitness();

    /**
     * Evaluates the value of the fitness function for the individual. After
     * the fitness is computed, the <code>getFitness</code> may be called
     * repeatedly, saving computation time.
     */
    public abstract void computeFitness();

    /**
     * Determines whether a graph node of given index (starting from 0) is
     * part of the vertex cover encoded by the individual.
     * 
     * @param j Index of the node in the graph.
     * @return <code>true</code> if the <code>j</code>-th node is part of the
     * vertex cover, <code>false</code> if not
     */
    public abstract boolean isNodeSelected(int j);

    /**
     * Creates a deep copy of the current individual, i.e. a new individual
     * with the same internal data. This is necessary for genetic operations
     * like mutation, crossover, etc.
     * 
     * @return A new individual identical to the current
     */
    public abstract AbstractIndividual deepCopy();


    /**
     * Crosses the current individual over with other individual given as a
     * parameter, yielding a pair of offsprings.
     * 
     * @param other The other individual to be crossed over with
     * @return A couple of offspring individuals
     */
    public abstract Pair crossover(AbstractIndividual other);

    /**
     * Does random changes in the individual's genotype, taking mutation
     * probability into account.
     * 
     * @param mutationRate Probability of a bit being inverted, i.e. a node
     * being added to/removed from the vertex cover.
     */
    public abstract void mutate(double mutationProbability);

    /**
     * Compares the individuals based on their fitness.
     * 
     * Must be compatible with Java compare usage:
     * +1 if this is greater than the other 
     * -1 if this is smaller than the other, 0 if they are equal
     */
    @Override
    public int compareTo(AbstractIndividual another) {
        if (this.getFitness() > another.getFitness()) {
            return 1;
        } else if (this.getFitness() < another.getFitness()) {
            return -1;
        }
        return 0;
    }
}
