package cz.cvut.fit.zum.api.ga;

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
}
