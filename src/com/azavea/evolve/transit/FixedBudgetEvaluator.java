package com.azavea.evolve.transit;

import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ddohler on 12/11/14.
 */
public class FixedBudgetEvaluator extends BaseTransitSystemEvaluator implements FitnessEvaluator<TransitSystem> {
    private final City city;
    private final double budget;
    private final double scaleCost;
    private final double scaleValue;

    public FixedBudgetEvaluator(City city, double budget, double scaleCost, double scaleValue) {
        this.city = city;
        this.budget = budget;
        this.scaleCost = scaleCost;
        this.scaleValue = scaleValue;
    }

    /**
     *
     * @param transitSystem
     * @param transitSystems
     * @return system's value if it doesn't exceed the max budget, 0 otherwise.
     */
    @Override
    public double getFitness(TransitSystem transitSystem, List<? extends TransitSystem> transitSystems) {
        double totalCost = getSystemTotalPassengerLength(transitSystem);

        if (totalCost * scaleCost > budget) {
            return 0.0;
        }
        double totalValue = 0;

        for (Enumeration<TransitDestination> e = transitSystem.getDestinations().elements();
             e.hasMoreElements();) {
            TransitDestination dest = e.nextElement();
            // Don't decay -- the value of links is based off actual travel, rather than possible travel.
            totalValue = totalValue + decayedSystemValueFromStartingCell(dest, city, 1.0);
        }

        return totalValue * scaleValue;
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
