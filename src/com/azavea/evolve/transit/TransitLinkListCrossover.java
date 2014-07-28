package com.azavea.evolve.transit;

import org.uncommons.watchmaker.framework.operators.ListCrossover;

import java.util.List;
import java.util.Random;

/**
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
public class TransitLinkListCrossover extends ListCrossover<TransitLink> {
    @Override
    public List<List<TransitLink>> mate(List<TransitLink> parent1, List<TransitLink> parent2, int numberOfCrossoverPoints, Random rng) {
        return super.mate(parent1, parent2, numberOfCrossoverPoints, rng);
    }
}
