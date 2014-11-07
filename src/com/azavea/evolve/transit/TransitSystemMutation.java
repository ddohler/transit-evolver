package com.azavea.evolve.transit;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
public class TransitSystemMutation implements EvolutionaryOperator<TransitSystem> {
    private final City city;
    private final Probability mutationProbability;

    public TransitSystemMutation(City city, Probability mutationProbability) {
        this.city = city;
        this.mutationProbability = mutationProbability;
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

            // Add/remove a link?
            if (mutationProbability.nextEvent(rng)) {
                // Choose between add / remove a link, equally likely.
                if (rng.nextBoolean()) { // Add a random link when true.
                    TransitDestination pick1 = new TransitDestination((cells.get(rng.nextInt(cells.size()))));
                    TransitDestination pick2 = new TransitDestination(cells.get(rng.nextInt(cells.size())));

                    double cost = city.generateBaseLinkCost(pick1.getCell(), pick2.getCell());
                    double val = city.generateBaseLinkValue(pick1.getCell(), pick2.getCell());

                    systemLinks.add(new TransitLink(pick1, pick2, cost, val));
                } else { // Remove a random link when false.
                    if (systemLinks.size() != 0) {
                        systemLinks.remove(rng.nextInt(systemLinks.size()));
                    }
                }
            }
            results.add(new TransitSystem(systemLinks));
        }
        return results;
    }
}
