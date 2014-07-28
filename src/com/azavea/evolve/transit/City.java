package com.azavea.evolve.transit;

import java.util.List;

/**
 * Created by AZVA-INT\ddohler on 7/25/14.
 *
 * Represents a city as a list of locations within the city.
 */
public class City {
    private final List<CityCell> cells;

    public City(List<CityCell> cells) {
        this.cells = cells;

        // Normalize attraction scores over the whole city to be in 0...1.
        // Guarantees that all cells have a relativeAttraction score.
        // Get the sum
        double totalAttraction = 0;
        for (CityCell c : cells) {
            totalAttraction += c.getAbsoluteAttraction();
        }
        // Normalize
        for (CityCell c: cells) {
            c.setRelativeAttraction(c.getAbsoluteAttraction()/totalAttraction);
        }
    }

    public List<CityCell> getCells() {
        return cells;
    }

    /*
     *   Calculate what a link between two cells would cost. Currently, just
     *   the Euclidean distance between them.
     */
    public double generateBaseLinkCost(CityCell cell1, CityCell cell2) {
        return cell1.distanceToCell(cell2);
    }

    /*
     * Calculate the base value of a link (i.e., the value to the two cells it connects,
     * without factoring in any values to other cells).
     */
    public double generateBaseLinkValue(CityCell cell1, CityCell cell2) {
        return cell1.getProduction() * cell2.getRelativeAttraction() + cell2.getProduction() * cell1.getRelativeAttraction();
    }
}
