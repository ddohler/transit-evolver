package com.azavea.evolve.transit;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
public class TransitSystemCrossover extends AbstractCrossover<TransitSystem> {
    private TransitLinkListCrossover transitLinkListCrossover = new TransitLinkListCrossover();

    protected TransitSystemCrossover(int crossoverPoints) { super(crossoverPoints); }

    public List<TransitSystem> mate(TransitSystem parent1, TransitSystem parent2, int numCrossoverPoints, Random rng) {
        List<List<TransitLink>> offspringLinks = transitLinkListCrossover.mate(parent1.getLinks(), parent2.getLinks(), numCrossoverPoints, rng);
        List<TransitSystem> offspring = new LinkedList<TransitSystem>();

        for (List<TransitLink> links: offspringLinks) {
            // This uses the slow constructor, but I don't see any way around it at the moment without implementing
            // a TransitSystem-specific crossover function from scratch.
            // This is the only place where it's used.
            offspring.add(new TransitSystem(links));
        }

        return offspring;
    }
}
