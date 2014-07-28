package com.azavea.evolve.transit;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * Creates TransitSystem candidates.
 */
public class TransitSystemCandidateFactory extends AbstractCandidateFactory<TransitSystem> {
    private final City city;
    private final double initialDensity;

    /**
     * @param city
     * @param initialDensity dictates how many links each CityCell will have, on average; alternatively, it dictates
     * the number of links in each generated TransitSystem. A value of 1 results in TransitSystems where the
     * number of links in each TransitSystem is equal to the number of cells in the City, giving a density of
     * 1 link per cell, on average.
     */
    public TransitSystemCandidateFactory(City city, double initialDensity) {
        this.city = city;
        this.initialDensity = initialDensity;
    }

    /**
     * Generate a population of TransitSystems, with the number of links per system determined by
     * the CandidateFactory's initialDensity attribute.
     * @param populationSize
     * @param rng
     * @return
     */
    @Override
    public List<TransitSystem> generateInitialPopulation(int populationSize, Random rng) {
        List<TransitSystem> population = new ArrayList<TransitSystem>(populationSize);
        while (population.size() < populationSize) {
            population.add(this.generateRandomCandidate(rng));
        }
        return population;
    }

    /**
     * Generate a random TransitSystem, with the number of links determined by the CandidateFactory's
     * initialDensity attribute.
     * @param rng
     * @return
     */
    @Override
    public TransitSystem generateRandomCandidate(Random rng) {
        int desiredLinks = (int)(initialDensity * city.getCells().size());
        List<CityCell> cells = city.getCells();
        List<TransitLink> systemLinks = new ArrayList<TransitLink>(desiredLinks);
        while (systemLinks.size() < desiredLinks) {
            CityCell pick1 = cells.get(rng.nextInt(cells.size()));
            CityCell pick2 = cells.get(rng.nextInt(cells.size()));

            double cost = city.generateBaseLinkCost(pick1, pick2);
            double val = city.generateBaseLinkValue(pick1, pick2);
            systemLinks.add(new TransitLink(pick1, pick2, cost, val));
        }
        return new TransitSystem(systemLinks);
    }


}
