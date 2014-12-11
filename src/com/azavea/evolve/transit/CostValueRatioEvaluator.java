package com.azavea.evolve.transit;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.*;

/**
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
// TODO: Refactor cost / value scaling away from City and into FitnessEvaluators
public class CostValueRatioEvaluator extends BaseTransitSystemEvaluator implements FitnessEvaluator<TransitSystem> {
    public static final double LINK_VALUE_DECAY_FACTOR = 0.8;
    private final City city;
    private double scaleCost;
    private double scaleValue;

    public CostValueRatioEvaluator(City city) {
        this.city = city;
    }


    @Override
    public double getFitness(TransitSystem transitSystem, List<? extends TransitSystem> transitSystems) {
        double totalCost = getSystemTotalLength(transitSystem);
        double totalValue = 0;
        // For each node in the network, traverse the network and sum up the values of the nodes it can reach.
        // Decay exponentially by LINK_VALUE_DECAY_FACTOR for each degree of separation.
        for (Enumeration<TransitDestination> e = transitSystem.getDestinations().elements();
                e.hasMoreElements();) {
            TransitDestination dest = e.nextElement();
            totalValue = totalValue + decayedSystemValueFromStartingCell(dest, city, LINK_VALUE_DECAY_FACTOR);
        }
        if (totalCost == 0.0) {
            return 0;
        }
        return totalValue * scaleValue / totalCost * scaleCost;
    }

    @Override
    public boolean isNatural() { return true; }
}
