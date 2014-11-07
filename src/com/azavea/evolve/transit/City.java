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

    /*
     *   Calculate what a link between two cells would cost. Currently, just
     *   the Euclidean distance between them.
     */
    public double generateBaseLinkCost(CityCell cell1, CityCell cell2) {
        return costFactor * cell1.distanceToCell(cell2);
    }

    /*
     * Calculate the base value of a link (i.e., the value to the two cells it connects,
     * without factoring in any values to other cells).
     */
    public double generateBaseLinkValue(CityCell cell1, CityCell cell2) {
        if (cell1 == cell2) {
            return 0.0;
        }
        return valueFactor * (cell1.flowToCell(cell2) + cell2.flowToCell(cell1));
    }
}
