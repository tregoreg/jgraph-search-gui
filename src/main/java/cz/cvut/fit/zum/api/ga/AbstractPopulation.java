package cz.cvut.fit.zum.api.ga;

import java.util.Arrays;

/**
 * Population of individuals used by the evolutionary algorithm.
 * 
 * @author Tomas Barton
 */
public class AbstractPopulation {

    protected AbstractIndividual[] individuals = null;
    protected double avgFitness = 0;
    private double bestFitness = 0;

    /* ################################################################### */
    /*    BASICALLY, YOU DO NOT NEED TO TOUCH THIS CODE                    */
    /* ################################################################### */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== POPULATION ===\n");
        for (int i = 0; i < individuals.length; i++) {
            sb.append(individuals[i].toString());
            sb.append("\n");
        }
        sb.append("=== avgFIT: ").append(avgFitness).append(" ===\n");
        return sb.toString();
    }

    /**
     * Computes an average fitness of all the individuals in the population,
     * using their <code>getFitness</code> method.
     *
     * @return average fitness in the population
     */
    public double getAvgFitness() {
        avgFitness = 0;
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].getFitness();
            avgFitness += individuals[i].getFitness();
            bestFitness = Math.max(bestFitness, individuals[i].getFitness());
        }
        avgFitness = avgFitness / individuals.length;
        return avgFitness;
    }

    /**
     * Returns the best individual (the elite) currently present in the
     * population, based of the values returned by the <code>getFitness</code>
     * method.
     *
     * @return The elite individual
     */
    public AbstractIndividual getBestIndividual() {
        AbstractIndividual best = this.individuals[0];
        for (int i = 0; i < this.individuals.length; i++) {
            if (Double.isNaN(best.getFitness()) || this.individuals[i].getFitness() > best.getFitness()) {
                best = this.individuals[i];
            }
        }
        return best;
    }

    /**
     * Internally sorts the individuals in the population according to their
     * fitness values
     */
    public void sortByFitness() {
        Arrays.sort(individuals);
    }

    /**
     * Gets a reference to the internal array of individuals.
     * 
     * @return The array of individuals
     */
    public AbstractIndividual[] getIndividuals() {
        return individuals;
    }
    
    /**
     * Gets the number of individuals in the population.
     * 
     * @return Population size
     */
    public int size(){
        return individuals.length;
    }

    /**
     * Gets an individual at a specified index.
     * 
     * @param idx The index of the individual to be returned
     * @return The individual at the index given
     */
    public AbstractIndividual getIndividual(int idx){
        return this.individuals[idx];
    }
    
    /**
     * 
     * @param index
     * @param individual 
     */
    public void setIndividualAt(int index, AbstractIndividual individual){
        individuals[index] = individual;
    }

    /**
     * Gets the fitness of the elite individual in the population.
     * 
     * @return Fitness of the fittest individual
     */
    public double getBestFitness() {
        return bestFitness;
    }

    /**
     * Sets the fitness of the elite individual in the population.
     * 
     * @param bestFitness The new elite fitness
     */
    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }
    
    
}
