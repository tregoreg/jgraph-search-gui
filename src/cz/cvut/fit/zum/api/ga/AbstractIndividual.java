package cz.cvut.fit.zum.api.ga;

import java.util.List;

/**
 *
 * @author Tomas Barton
 */
public abstract class AbstractIndividual implements Comparable<AbstractIndividual> {

    public abstract double getFitness();

    public abstract void countFitness();

    public abstract boolean getGen(int j);

    public abstract AbstractIndividual deepCopy();

    public abstract List<AbstractIndividual> cross(AbstractIndividual i);

    public abstract void mutate();

    /**
     * Must be compatible with Java compare usage:
     * +1 if this is greater than the other 
     * -1 if this is smaller than the other, 0 if they are equal
     * 
     *
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
