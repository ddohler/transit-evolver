package com.azavea.evolve.transit;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * Represents an area within a city and the transit demand to travel to / from that area.
 */
public class CityCell {
    // The amount of traffic emanating from this cell; population, basically.
    protected final double production;
    // The desirability of visiting this cell.
    protected final double absoluteAttraction;
    // The desirability of this cell, weighted against all other cells.
    protected double relativeAttraction;
    // The location of this cell within the City (Euclidean at the moment).
    protected final Location location;

    public CityCell(double production, double absoluteAttraction, double x, double y) {
        this.production = production;
        this.absoluteAttraction = absoluteAttraction;

        this.location = new Location(x, y);
    }

    public void setRelativeAttraction(double relativeAttraction) {
        this.relativeAttraction = relativeAttraction;
    }

    public double getProduction() {
        return production;
    }

    public double getAbsoluteAttraction() {
        return absoluteAttraction;
    }

    public double getRelativeAttraction() {
        return relativeAttraction;
    }

    public double distanceToCell(CityCell other) {
        return location.distance_to(other.location);
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location.toString();
    }
}
