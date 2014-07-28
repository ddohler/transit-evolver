package com.azavea.evolve.transit;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * A Euclidean location class for measuring the distance between things.
 */
public class Location {
    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double distance_to(Location other) {
        return Math.sqrt(Math.pow((other.x()-x), 2) + Math.pow((other.y()-y),2));
    }
}
