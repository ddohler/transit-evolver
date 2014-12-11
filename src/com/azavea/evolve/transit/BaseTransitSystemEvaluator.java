package com.azavea.evolve.transit;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseTransitSystemEvaluator {
    /**
     *
     * @param system
     * @return Summation of system link costs, based solely on link length
     */
    protected double getSystemTotalLength(TransitSystem system) {
        double totalCost = 0;
        List<TransitLink> systemLinks = system.getLinks();
        for (TransitLink link : systemLinks) {
            totalCost = totalCost + link.getLength();
        }
        return totalCost;
    }

    /**
     * @param system
     * @return Summation of system link costs, based on link length and num passengers (passenger-meters traveled)
     */
    protected double getSystemTotalPassengerLength(TransitSystem system) {
        double totalCost = 0;
        List<TransitLink> systemLinks = system.getLinks();
        for (TransitLink link : systemLinks) {
            totalCost = totalCost + link.getLength() * link.getFlow() * 2; // 2: Return trip
        }
        return totalCost;
    }

    // Traverse the portion of the transit system accessible from starting node dest,
    // summing the value of being able to access all the nodes visited.
    protected double decayedSystemValueFromStartingCell(TransitDestination source, City city, double linkValueDecayFactor) {
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
        double decayFactor = 1.0 / linkValueDecayFactor;
        while ((current = toVisit.poll()) != null) {
            Integer degree = jumps.poll();
            // If we've gone out another degree in the graph, we decay the values.
            if(!alreadyVisited.contains(current)) {
                valueSum += city.generateBaseLinkValue(current.getCell(), source.getCell()) * Math.pow(linkValueDecayFactor, degree.doubleValue());
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
}
