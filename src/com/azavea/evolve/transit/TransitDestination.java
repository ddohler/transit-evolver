package com.azavea.evolve.transit;

import java.util.ArrayList;

/**
 * Created by AZVA-INT\ddohler on 8/11/14.
 * Represents a transit destination: A combination of a CityCell and the TransitLinks which connect to it.
 */
public class TransitDestination {
    private ArrayList<TransitLink> links;
    private CityCell here;

    // Mirrors the CityCell constructor; sets up production, attraction, and location but no links.
    public TransitDestination(double production, double x, double y, String cellID) {
        here = new ProportionalFlowCell(production, new Location(x, y), cellID);
        links = new ArrayList<TransitLink>();
    }

    public TransitDestination(CityCell cell) {
        here = cell;
        links = new ArrayList<TransitLink>();
    }

    // Copy an existing CityCell and initialize the first link to it.
    public TransitDestination(CityCell cell, TransitLink firstLink) {
        here = cell;
        links = new ArrayList<TransitLink>();
        links.add(firstLink);
    }

    public ArrayList<TransitLink> getLinks() {
        return links;
    }

    // Adds a link to this Destination's links. Doesn't check for duplicate links.
    public TransitDestination addLink(TransitLink link) {
        links.add(link);
        return this;
    }

    public CityCell getCell() {
        return here;
    }

    // Removes a link (if it exists) from this Destination's links.
    // Returns true if removed, false if not found.
    public boolean removeLink(TransitLink link) {
        return links.remove(link);
    }

    public String toString() {
        return here.toString();
    }
}
