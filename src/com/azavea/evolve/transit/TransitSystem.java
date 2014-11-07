package com.azavea.evolve.transit;

import java.util.*;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * A transit system, represented by a list of links, that is, connections between places in a city.
 */
public class TransitSystem {
    private final List<TransitLink> links;
    private final Hashtable<Location, TransitDestination> destinations;

    public TransitSystem() {
        this.links = new ArrayList<TransitLink>();
        this.destinations = new Hashtable<Location, TransitDestination>();
    }

    // Copy constructor -- also slow
    public TransitSystem(TransitSystem other) {
        this.links = new ArrayList<TransitLink>(other.links.size());
        this.destinations = new Hashtable<Location, TransitDestination>(other.destinations.size());
        for (TransitLink link: other.links) {
            addLink(link);
        }
    }

    // Not recommended -- better to initialize an empty TransitSystem and let it
    // managed the insertion / deletion of links. However, for operations like crossovers
    // that don't operate well on graphs, this is necessary.
    // TODO: Write a crossover implementation that maintains destinations.
    public TransitSystem(List<TransitLink> links) {
        this.links = new ArrayList<TransitLink>(links.size());
        this.destinations = new Hashtable<Location, TransitDestination>();
        // If constructed with only TransitLinks, bootstrap the Destination graph.
        // This is slow, so amortize when possible.
        for (TransitLink link: links) {
            addLink(link);
        }
    }

    private TransitDestination getOrCreateDestination(CityCell cell) {
        Location loc = cell.getLocation();
        if (destinations.containsKey(loc)) {
            return destinations.get(loc);
        } else {
            TransitDestination newDest = new TransitDestination(cell);
            destinations.put(loc, newDest);
            return newDest;
        }
    }

    private boolean removeLinkFromDest(TransitDestination dest, TransitLink link) {
        if (destinations.containsKey(dest.getCell().getLocation())) {
            destinations.get(dest.getCell().getLocation()).removeLink(link);
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
    private void addLink(CityCell cell1, CityCell cell2, double cost, double val) {
        TransitDestination dest1 = getOrCreateDestination(cell1);
        TransitDestination dest2 = getOrCreateDestination(cell2);
        TransitLink newLink = new TransitLink(dest1, dest2, cost, val);
        dest1.addLink(newLink);
        dest2.addLink(newLink);
        links.add(newLink);
    }

    // Add a link based on a TransitLink object.
    private void addLink(TransitLink link) {
        TransitDestination dest1 = getOrCreateDestination(link.getDest1().getCell());
        TransitDestination dest2 = getOrCreateDestination(link.getDest2().getCell());
        TransitLink newLink = new TransitLink(dest1, dest2, link.getBaseCost(), link.getBaseValue());
        dest1.addLink(newLink);
        dest2.addLink(newLink);
        links.add(newLink);
    }

    // Remove a link from this system, and remove it from its destinations, too.
    private void removeLink(TransitLink link) {
        removeLinkFromDest(link.getDest1(), link);
        removeLinkFromDest(link.getDest2(), link);
        links.remove(link);
    }
}
