package com.azavea.evolve.transit;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.*;

/**
 * Created by AZVA-INT\ddohler on 7/28/14.
 */
public class CostValueRatioEvaluator implements FitnessEvaluator<TransitSystem> {
    public static final double LINK_VALUE_DECAY_FACTOR = 0.8;
    private final City city;

    public CostValueRatioEvaluator(City city) {
        this.city = city;
    }


    @Override
    public double getFitness(TransitSystem transitSystem, List<? extends TransitSystem> transitSystems) {
        double totalCost = 0;
        double totalValue = 0;
        HashSet<TransitLink> uniqueLinks = new HashSet<TransitLink>();
        // First, get our total system cost: That's the cost of all links.
        List<TransitLink> systemLinks = transitSystem.getLinks();
        for (TransitLink link: systemLinks) {
            totalCost += link.getBaseCost();
        }
        // Next, for each node in the network, traverse the network and sum up the values of the nodes it can reach.
        // Decay exponentially by LINK_VALUE_DECAY_FACTOR for each degree of separation.
        for (Enumeration<TransitDestination> e = transitSystem.getDestinations().elements();
                e.hasMoreElements();) {
            TransitDestination dest = e.nextElement();
            totalValue += systemValueFromStartingCell(dest);
        }
        if (totalCost == 0.0) {
            return 0;
        }
        return totalValue / totalCost;
    }

    // Traverse the portion of the transit system accessible from starting node dest,
    // summing the value of being able to access all the nodes visited.
    private double systemValueFromStartingCell(TransitDestination source) {
        HashSet<TransitDestination> alreadyVisited = new HashSet<TransitDestination>();
        LinkedList<TransitDestination> toVisit = new LinkedList<TransitDestination>(); // Using as a deque
        // TODO: This is kind of ugly.
        LinkedList<Integer> jumps = new LinkedList<Integer>(); // Parallel the previous deque so we know where we are.
        double valueSum = 0;
        // Prime the queue
        toVisit.add(source);
        jumps.add(new Integer(0));
        int prevDegree = 0;
        TransitDestination current;
        // The first cell visited will be source, so it won't count toward the value. Therefore we divide the scaling
        // factor by the decay factor so that at the first degree of separation, decayFactor == 1.0.
        double decayFactor = 1.0 / LINK_VALUE_DECAY_FACTOR;
        while ((current = toVisit.poll()) != null) {
            Integer degree = jumps.poll();
            // If we've gone out another degree in the graph, we decay the values.
            if(!alreadyVisited.contains(current)) {
                valueSum += city.generateBaseLinkValue(current.getCell(), source.getCell()) * Math.pow(LINK_VALUE_DECAY_FACTOR, degree.doubleValue());
                // Traverse the links out from the current node.
                for (TransitLink link : current.getLinks()) {
                    TransitDestination nextCell = link.getConnected(current);
                    if (!alreadyVisited.contains(nextCell)) {
                        toVisit.add(nextCell);
                        jumps.add(new Integer(degree + 1));
                    }
                }
                // Mark this node as visited
                alreadyVisited.add(current);
            }
        }
        return valueSum;
    }

    @Override
    public boolean isNatural() { return true; }
}
