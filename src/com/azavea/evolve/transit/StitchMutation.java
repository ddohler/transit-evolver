package com.azavea.evolve.transit;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * Creates a bridge between two links
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
public class StitchMutation implements EvolutionaryOperator<TransitSystem> {
    private final Probability mutationProbability;
    private final boolean deleteAfterAdd;

    public StitchMutation(Probability mutationProbability, boolean deleteAfterAdd) {
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
        for (TransitSystem transitSys: population) {
            ArrayList<TransitLink> systemLinks = new ArrayList<TransitLink>(transitSys.getLinks());

            // Change a link?
            if (mutationProbability.nextEvent(rng)) {
                // Connect the link to another link
                TransitLink link1 = systemLinks.get(rng.nextInt(systemLinks.size()));
                TransitLink link2 = systemLinks.get(rng.nextInt(systemLinks.size()));
                TransitDestination pick1 = rng.nextBoolean() ? link1.getDest1() : link1.getDest2();
                TransitDestination pick2 = rng.nextBoolean() ? link2.getDest1() : link1.getDest2();
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
