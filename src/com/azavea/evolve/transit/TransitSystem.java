package com.azavea.evolve.transit;

import java.util.List;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * A transit system, represented by a list of links, that is, connections between places in a city.
 */
public class TransitSystem {
    private final List<TransitLink> links;

    public TransitSystem(List<TransitLink> links) {
        this.links = links;
    }
}
