package com.azavea.evolve.transit;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashSet;
import java.util.List;

/**
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
public class TransitSystemEvaluator implements FitnessEvaluator<TransitSystem> {
    private final City city;

    public TransitSystemEvaluator(City city) {
        this.city = city;
    }


    @Override
    public double getFitness(TransitSystem transitSystem, List<? extends TransitSystem> transitSystems) {
        double fitness = 0;
        HashSet<TransitLink> uniqueLinks = new HashSet<TransitLink>();
        for (TransitLink link: transitSystem.getLinks()) {
            // Duplicate links don't provide further value; assume that all links can have their capacity
            // changed to meet demand without incurring additional cost beyond the base cost.
            if (uniqueLinks.add(link)) {
                fitness += link.getBaseValue();
                fitness -= link.getBaseCost(); // TODO: Make this smarter (graph traversal)
            }
        }
        return fitness;
    }

    @Override
    public boolean isNatural() { return true; }
}
