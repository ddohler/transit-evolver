package com.azavea.evolve.transit;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * A Euclidean location class for measuring the distance between things.
 */
public class Location implements Comparable<Location> {
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
        return Math.sqrt(Math.pow((other.x() - x), 2) + Math.pow((other.y() - y), 2));
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
    }

    /**
     * Arbitrarily determine that Locations are sorted in x, y order.
     * @param o
     * @return
     */
    @Override
    public int compareTo(Location o) {
        if (o.x < x) {
            return -1;
        } else if (o.x > x) {
            return 1;
        } else { // o.x == x
            if (o.y < y) {
                return -1;
            } else if (o.y > y) {
                return 1;
            } else { // o == this
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Location)) return false;
        Location otherLoc = (Location)other;
        if (otherLoc.x == x && otherLoc.y == y) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
