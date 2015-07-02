package com.azavea.evolve.transit;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Mutator that extends an existing link to some other cell
 * Created by ddohler on 12/12/14.
 */
public class ExtendMutation implements EvolutionaryOperator<TransitSystem> {
    private final City city;
    private final Probability mutationProbability;
    private final boolean deleteAfterAdd;

    public ExtendMutation(City city, Probability mutationProbability, boolean deleteAfterAdd) {
        this.city = city;
        this.mutationProbability = mutationProbability;
        this.deleteAfterAdd = deleteAfterAdd;
    }

    /**
     *
     * @param population The TransitSystems to mutate
     * @param rng random number generation
     * @return List of possibly mutated TransitSystems
     */
    @Override
    public List<TransitSystem> apply(List<TransitSystem> population, Random rng) {
        LinkedList<TransitSystem> results = new LinkedList<TransitSystem>();
        List<CityCell> cells = city.getCells();
        for (TransitSystem transitSys: population) {
            ArrayList<TransitLink> systemLinks = new ArrayList<TransitLink>(transitSys.getLinks());

            // Extend a link -- also deletes a link so the total number of links stays fixed
            if (mutationProbability.nextEvent(rng)) {
                // Connect the link to another link
                TransitLink link = systemLinks.get(rng.nextInt(systemLinks.size()));
                TransitDestination pick1 = new TransitDestination((cells.get(rng.nextInt(cells.size()))));
                TransitDestination pick2 = rng.nextBoolean() ? link.getDest1() : link.getDest2();
                systemLinks.add(new TransitLink(pick1, pick2));
            }
            results.add(new TransitSystem(systemLinks));
            if (deleteAfterAdd && systemLinks.size() != 0) {
                systemLinks.remove(rng.nextInt(systemLinks.size()));
            }
        }
        return results;
    }
}
