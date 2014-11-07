package com.azavea.evolve.transit;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by AZVA-INT\ddohler on 8/12/14.
 */
public class Cities {
    public static City thirtyCells() {
        ArrayList<CityCell> cells = new ArrayList<CityCell>();
        // Values are:
        // Generation, AbsoluteAttraction, X, Y
        // Column 0, top to bottom
        cells.add(new ProportionalFlowCell(2.0, new Location(0, 4), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(15.0, new Location(0, 3), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(0, 2), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(0, 1), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(0, 0), UUID.randomUUID().toString()));

        // Column 1, top to bottom
        cells.add(new ProportionalFlowCell(1.0, new Location(1, 4), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(2.0, new Location(1, 3), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(5.0, new Location(1, 2), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(1, 1), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(1, 0), UUID.randomUUID().toString()));

        // Column 2, top to bottom
        cells.add(new ProportionalFlowCell(1.0, new Location(2, 4), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(5.0, new Location(2, 3), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(25.0, new Location(2, 2), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(5.0, new Location(2, 1), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(2, 0), UUID.randomUUID().toString()));

        // Column 3
        cells.add(new ProportionalFlowCell(1.0, new Location(3, 4), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(10.0, new Location(3, 3), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(50.0, new Location(3, 2), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(10.0, new Location(3, 1), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(3, 0), UUID.randomUUID().toString()));

        // Column 4
        cells.add(new ProportionalFlowCell(1.0, new Location(4, 4), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(5.0, new Location(4, 3), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(10.0, new Location(4, 2), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(10.0, new Location(4, 1), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(5.0, new Location(4, 0), UUID.randomUUID().toString()));

        // Column 5
        cells.add(new ProportionalFlowCell(1.0, new Location(5, 4), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(5, 3), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(1.0, new Location(5, 2), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(5.0, new Location(5, 1), UUID.randomUUID().toString()));
        cells.add(new ProportionalFlowCell(20.0, new Location(5, 0), UUID.randomUUID().toString()));

        // Scale things so that there is room for the values to oscillate.
        double scaleCost = 1.0;
        double scaleValue = 2.0;
        return new City(cells, scaleCost, scaleValue);
    }
}
