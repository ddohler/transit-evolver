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
    private final TransitDestination cell1;
    private final TransitDestination cell2;
    private final double baseCost; // The cost of this link, based only on the two cells it connects
    private final double baseValue; // The value of this link, based only on the two cells it connects

    public TransitLink(TransitDestination cell1, TransitDestination cell2, double cost, double value) {
        // Links are not directed, so enforce an arbitrary ordering so that equality and hashCodes are easier.
        if ((cell1.getLocation().compareTo(cell2.getLocation())) > 0) {
            this.cell1 = cell1;
            this.cell2 = cell2;
        } else {
            this.cell1 = cell2;
            this.cell2 = cell1;
        }
        this.baseCost = cost;
        this.baseValue = value;
    }

    public CityCell getCell1() {
        return cell1;
    }

    public CityCell getCell2() {
        return cell2;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public TransitDestination getConnected(TransitDestination cell) {
        if (cell1 == cell) {
            return cell2;
        } else if (cell2 == cell) {
            return cell1;
        } else {
            return null;
        }
    }

    public boolean isSelfLink() {
        return cell1 == cell2;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof TransitLink)) return false;
        TransitLink otherLink = (TransitLink)other;
        if (cell1.getLocation() == otherLink.cell1.getLocation() && cell2.getLocation() == otherLink.cell2.getLocation()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (cell1.toString() + cell2.toString()).hashCode();
    }

    @Override
    public String toString() {
        return cell1.toString() + "-->" + cell2.toString();
    }
}
