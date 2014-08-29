package com.azavea.evolve.transit;

import java.util.ArrayList;

/**
 * Created by AZVA-INT\ddohler on 8/12/14.
 */
public class Cities {
    public static City thirtyCells() {
        ArrayList<CityCell> cells = new ArrayList<CityCell>();
        // Values are:
        // Generation, AbsoluteAttraction, X, Y
        // Column 0, top to bottom
        cells.add(new CityCell(2.0, 0.34, 0, 4));
        cells.add(new CityCell(15.0, 2.55, 0, 3));
        cells.add(new CityCell(1.0, 0.17, 0, 2));
        cells.add(new CityCell(1.0, 0.17, 0, 1));
        cells.add(new CityCell(1.0, 0.17, 0, 0));

        // Column 1, top to bottom
        cells.add(new CityCell(1.0, 0.17, 1, 4));
        cells.add(new CityCell(2.0, 0.34, 1, 3));
        cells.add(new CityCell(5.0, 0.85, 1, 2));
        cells.add(new CityCell(1.0, 0.17, 1, 1));
        cells.add(new CityCell(1.0, 0.17, 1, 0));

        // Column 2, top to bottom
        cells.add(new CityCell(1.0, 0.17, 2, 4));
        cells.add(new CityCell(5.0, 0.85, 2, 3));
        cells.add(new CityCell(25.0, 4.25, 2, 2));
        cells.add(new CityCell(5.0, 0.85, 2, 1));
        cells.add(new CityCell(1.0, 0.17, 2, 0));

        // Column 3
        cells.add(new CityCell(1.0, 0.17, 3, 4));
        cells.add(new CityCell(10.0, 1.7, 3, 3));
        cells.add(new CityCell(50.0, 8.5, 3, 2));
        cells.add(new CityCell(10.0, 1.7, 3, 1));
        cells.add(new CityCell(1.0, 0.17, 3, 0));

        // Column 4
        cells.add(new CityCell(1.0, 0.17, 4, 4));
        cells.add(new CityCell(5.0, 0.85, 4, 3));
        cells.add(new CityCell(10.0, 1.7, 4, 2));
        cells.add(new CityCell(10.0, 1.7, 4, 1));
        cells.add(new CityCell(5.0, 0.85, 4, 0));

        // Column 5
        cells.add(new CityCell(1.0, 0.17, 5, 4));
        cells.add(new CityCell(1.0, 0.17, 5, 3));
        cells.add(new CityCell(1.0, 0.17, 5, 2));
        cells.add(new CityCell(5.0, 0.85, 5, 1));
        cells.add(new CityCell(20.0, 3.4, 5, 0));

        // Scale things so that there is room for the values to oscillate.
        double scaleCost = 1.0;
        double scaleValue = 2.0;
        return new City(cells, scaleCost, scaleValue);
    }
}
