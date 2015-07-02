package com.azavea.evolve.transit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * Represents a city as a list of locations within the city.
 */
public class City {
    private final ArrayList<CityCell> cells;
    private final double costFactor;
    private final double valueFactor;

    public City(ArrayList<CityCell> cells, double costFactor, double valueFactor) {
        this.cells = cells;
        this.costFactor = costFactor;
        this.valueFactor = valueFactor;
    }

    public List<CityCell> getCells() {
        return cells;
    }
}
