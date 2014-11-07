package com.azavea.evolve.transit;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * Represents an area within a city and the transit demand to travel to / from that area to other areas
 */
abstract class CityCell {
    public abstract String getCellID();
    public abstract double getProduction();
    public abstract Location getLocation();

    public abstract double flowToCell(CityCell other);
    public abstract double distanceToCell(CityCell other);
}