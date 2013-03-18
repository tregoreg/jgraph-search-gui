package cz.cvut.fit.zum.api.ga;

/**
 *
 * @author Tomas Barton
 */
public abstract class AbstractIndividual implements Comparable<AbstractIndividual> {

    
    public abstract double getFitness();
    
    public abstract void countFitness();
    
    public abstract boolean isVertexCovered(int nodeId);
    
    public abstract boolean getGen(int j);
    
    public abstract AbstractIndividual deepCopy();
    

    /* ################################################################### */
    /*    BASICALLY, YOU DO NOT NEED TO TOUCH CODE BELLOW THIS LINE ;-)    */
    /* ################################################################### */
    /**
     * Compares this object with the specified object by value of fitness
     * funcion for order. When you want to minimalize fitness, you have to
     * reverse positive and negative cases. This definition works for
     * 1maximizing value of fitness function.
     *
     * Returns -1 in case that this object is greater than o. Returns 0 in case
     * that this.equals(o) == true. Returns +1 in case that this object is
     * smaller than o.
     */
    @Override
    public int compareTo(AbstractIndividual another) {
        if (this.getFitness() > another.getFitness()) {
            return -1;
        }
        if (this.getFitness() < another.getFitness()) {
            return 1;
        }
        return 0;
    }
}
