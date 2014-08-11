package com.azavea.evolve.transit;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * A transit system, represented by a list of links, that is, connections between places in a city.
 */
public class TransitSystem {
    private List<TransitLink> links;
    private Hashtable<Location, TransitDestination> destinations;

    public TransitSystem() {
        this.links = new ArrayList<TransitLink>();
        this.destinations = new Hashtable<Location, TransitDestination>();
    }

    // Copy constructor -- also not really recommended, but probably better than one below.
    public TransitSystem(TransitSystem other) {
        this.links = new ArrayList<TransitLink>(other.links);
        this.destinations = new Hashtable<Location, TransitDestination>(other.destinations);
    }

    // Not recommended -- better to initialize an empty TransitSystem and let it
    // managed the insertion / deletion of links. However, for operations like crossovers
    // that don't operate well on graphs, this is necessary.
    // TODO: Write a crossover implementation that maintains destinations.
    public TransitSystem(List<TransitLink> links) {
        this.links = links;
        this.destinations = new Hashtable<Location, TransitDestination>();
        // If constructed with only TransitLinks, bootstrap the Destination graph.
        // This is slow, so amortize when possible.
        for (TransitLink link: links) {
            ArrayList<CityCell> cells = new ArrayList<CityCell>();
            cells.add(link.getCell1());
            cells.add(link.getCell2());
            for (CityCell cell: cells) {
                TransitDestination dest = getOrCreateDestination(cell);
                dest.addLink(link);
            }
        }
    }

    private TransitDestination getOrCreateDestination(CityCell cell) {
        Location loc = cell.getLocation();
        if (destinations.contains(loc)) {
            return destinations.get(loc);
        } else {
            return destinations.put(loc, new TransitDestination(cell));
        }
    }

    private boolean removeLinkFromDest(CityCell cell, TransitLink link) {
        if (destinations.contains(cell.getLocation())) {
            destinations.get(cell.getLocation()).removeLink(link);
            return true;
        }
        return false;
    }

    public List<TransitLink> getLinks() {
        return links;
    }

    public Dictionary<Location, TransitDestination> getDestinations() {
        return destinations;
    }

    public int numLinks() {
        return links.size();
    }

    // Add a link to this system -- manages the destinations table.
    public void addLink(CityCell cell1, CityCell cell2, double cost, double val) {
        TransitDestination dest1 = getOrCreateDestination(cell1);
        TransitDestination dest2 = getOrCreateDestination(cell2);
        TransitLink newLink = new TransitLink(dest1, dest2, cost, val);
        dest1.addLink(newLink);
        dest2.addLink(newLink);
        links.add(newLink);
    }

    // Remove a link from this system, and remove it from its destinations, too.
    public void removeLink(TransitLink link) {
        removeLinkFromDest(link.getCell1(), link);
        removeLinkFromDest(link.getCell2(), link);
        links.remove(link);
    }
}
