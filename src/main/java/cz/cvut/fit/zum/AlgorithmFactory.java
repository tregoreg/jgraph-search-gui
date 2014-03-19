package cz.cvut.fit.zum;

import cz.cvut.fit.zum.api.AbstractAlgorithm;
import java.util.Collection;
import java.util.LinkedHashMap;
import org.openide.util.Lookup;

/**
 *
 * @author Tomas Barton
 */
public class AlgorithmFactory extends ServiceFactory<AbstractAlgorithm> {

    private static AlgorithmFactory instance;

    public static AlgorithmFactory getDefault() {
        if (instance == null) {
            instance = new AlgorithmFactory();
        }
        return instance;
    }

    private AlgorithmFactory() {
        providers = new LinkedHashMap<String, AbstractAlgorithm>();
        Collection<? extends AbstractAlgorithm> list = Lookup.getDefault().lookupAll(AbstractAlgorithm.class);
        for (AbstractAlgorithm c : list) {
            providers.put(c.getName(), c);
        }
    }
}
