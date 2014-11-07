package com.azavea.evolve.transit;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * A public transit connection between two areas (CityCells) in a City.
 *
 * Cost represents a rough measure of the cost of having that connection (operating + capital cost)
 * Value represents a rough measure of the value of having that connection to the residents of the city.
 */
public class TransitLink {
    private final TransitDestination dest1;
    private final TransitDestination dest2;
    private final double baseCost; // The cost of this link, based only on the two cells it connects
    private final double baseValue; // The value of this link, based only on the two cells it connects

    public TransitLink(TransitDestination dest1, TransitDestination dest2, double cost, double value) {
        // Links are not directed, so enforce an arbitrary ordering so that equality and hashCodes are easier.
        if ((dest1.getCell().getLocation().compareTo(dest2.getCell().getLocation())) > 0) {
            this.dest1 = dest1;
            this.dest2 = dest2;
        } else {
            this.dest1 = dest2;
            this.dest2 = dest1;
        }
        this.baseCost = cost;
        this.baseValue = value;
    }

    public TransitDestination getDest1() {
        return dest1;
    }

    public TransitDestination getDest2() {
        return dest2;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public TransitDestination getConnected(TransitDestination dest) {
        if (dest1 == dest) {
            return dest1;
        } else if (dest2 == dest) {
            return dest1;
        } else {
            return null;
        }
    }

    public boolean isSelfLink() {
        return dest1 == dest2;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof TransitLink)) return false;
        TransitLink otherLink = (TransitLink)other;
        if (dest1.getCell().getLocation() == otherLink.dest1.getCell().getLocation() &&
            dest2.getCell().getLocation() == otherLink.dest2.getCell().getLocation()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (dest1.toString() + dest2.toString()).hashCode();
    }

    @Override
    public String toString() {
        return dest1.toString() + "-->" + dest2.toString();
    }
}
