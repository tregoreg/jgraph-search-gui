package cz.cvut.fit.zum;

import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import java.util.Collection;
import java.util.LinkedHashMap;
import org.openide.util.Lookup;

/**
 *
 * @author Tomas Barton
 */
public class EvolutionFactory extends ServiceFactory<AbstractEvolution> {

    private static EvolutionFactory instance;

    public static EvolutionFactory getInstance() {
        if (instance == null) {
            instance = new EvolutionFactory();
        }
        return instance;
    }

    private EvolutionFactory() {
        providers = new LinkedHashMap<String, AbstractEvolution>();
        Collection<? extends AbstractEvolution> list = Lookup.getDefault().lookupAll(AbstractEvolution.class);
        for (AbstractEvolution c : list) {
            providers.put(c.getName(), c);
        }
    }
}
