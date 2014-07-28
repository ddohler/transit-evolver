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
    private final CityCell cell1;
    private final CityCell cell2;
    private final double baseCost; // The cost of this link, based only on the two cells it connects
    private final double baseValue; // The value of this link, based only on the two cells it connects

    public TransitLink(CityCell cell1, CityCell cell2, double cost, double value) {
        this.cell1 = cell1;
        this.cell2 = cell2;
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

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof TransitLink)) return false;
        TransitLink otherLink = (TransitLink)other;
        if (cell1 == otherLink.cell1 && cell2 == otherLink.cell2) return true;
        return false;
    }
}
