package cz.cvut.fit.zum.api.ga;

import java.util.Arrays;

/**
 *
 * @author Tomas Barton
 */
public class AbstractPopulation {

    protected AbstractIndividual[] individuals = null;
    protected double avgFitness = 0;
    protected double bestFitness = 0;

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
     * Method counts fitness of each individual and averages it.
     *
     * @return average fitness value
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
     * Returns best individual from population
     *
     * @return best individual
     */
    public AbstractIndividual getBestIndividual() {
        AbstractIndividual best = this.individuals[0];
        for (int i = 0; i < this.individuals.length; i++) {
            if (this.individuals[i].getFitness() > best.getFitness()) {
                best = this.individuals[i];
            }
        }
        return best;
    }

    /**
     * This methods needs that Individual implements interface Comparable.
     */
    public void sortByFitness() {
        Arrays.sort(individuals);
    }

    public AbstractIndividual[] getIndividuals() {
        return individuals;
    }

    
    public int individualsLength(){
        return individuals.length;
    }
    
    public AbstractIndividual getIndividual(int idx){
        return this.individuals[idx];
    }
    
    public void addIndividual(AbstractIndividual ind){
        this.individuals[individuals.length] = ind;
    }
    
    public void setIndividuals(int index, AbstractIndividual individual){
        individuals[index] = individual;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }
    
    
}
