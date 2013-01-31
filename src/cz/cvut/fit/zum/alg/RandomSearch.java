package cz.cvut.fit.zum.alg;

import cz.cvut.fit.zum.api.AbstractAlgorithm;
import java.util.List;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.UninformedSearch;
import java.util.ArrayList;
import java.util.HashSet;
import org.openide.util.lookup.ServiceProvider;

/**
 * WARNINIG: this is very stupid algorithm!!!
 *
 * Should serve only as an example of UninformedSearch usage
 *
 * @author Tomas Barton
 */
@ServiceProvider(service = AbstractAlgorithm.class, position = 100)
public class RandomSearch extends AbstractAlgorithm implements UninformedSearch {

    private HashSet<Node> blacklist;
    private List<Node> queue;

    @Override
    public String getName() {
        return "random search";
    }

    @Override
    public List<Node> findPath(Node startNode) {
        queue = new ArrayList<Node>();
        blacklist = new HashSet<Node>();
        List<Node> path = new ArrayList<Node>();

        expand(startNode);
        Node current = random(queue);

        blacklist.add(current);
        while (!current.isTarget()) {

            if (!blacklist.contains(current)) {
                expand(current);
            }
            blacklist.add(current);
            current = random(queue);
        }
        /**
         * @TODO return shortest path
         */
        return path;
    }

    private void expand(Node current) {
        List<Node> list = current.expand();
        for (Node n : list) {
            if (!blacklist.contains(n)) {
                queue.add(n);
            }
        }
    }

    /**
     * Select random node from list
     *
     * @param list
     * @return
     */
    private Node random(List<Node> list) {
        int min = 0;
        int max = list.size();
        int num = min + (int) (Math.random() * ((max - min)));
        //we want to remove explored nodes
        return list.remove(num);
    }
}

