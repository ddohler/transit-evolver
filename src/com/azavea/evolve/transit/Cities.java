package com.azavea.evolve.transit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by AZVA-INT\ddohler on 8/12/14.
 */
public class Cities {
    public static City thirtyCells(double scaleCost, double scaleValue) {
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
        return new City(cells, scaleCost, scaleValue);
    }

    private static VariableFlowCell cellFromTractRecord(String fipsRec, String xRec, String yRec, String popRec) {
        double x = Double.parseDouble(xRec);
        double y = Double.parseDouble(yRec);
        int population = Integer.parseInt(popRec);
        return new VariableFlowCell(population, fipsRec, x, y);
    }

    public static City fromTractFlowCSV(String path, double scaleCost, double scaleValue) {
        HashMap<String, VariableFlowCell> tractsByFIPS = new HashMap<String, VariableFlowCell>();

        Reader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CSVParser parser = null;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (CSVRecord record : parser) {
            if (count > 0 && count % 1000 == 0) {
                System.out.println(count);
            }
            count++;
            String srcFIPS = record.get("src_fips");
            String destFIPS = record.get("dest_fips");
            if (!tractsByFIPS.containsKey(srcFIPS)) {
                tractsByFIPS.put(srcFIPS, cellFromTractRecord(srcFIPS, record.get("source_x"), record.get("source_y"),
                        record.get("src_pop_2012")));
            }
            if (!tractsByFIPS.containsKey(destFIPS)) {
                tractsByFIPS.put(destFIPS, cellFromTractRecord(destFIPS, record.get("dest_x"), record.get("dest_y"),
                        record.get("dest_pop_2012")));
            }
            int flow = Integer.parseInt(record.get("flow"));
            tractsByFIPS.get(srcFIPS).addOutboundDestination(tractsByFIPS.get(destFIPS), flow);
        }

        return new City(new ArrayList<CityCell>(tractsByFIPS.values()), scaleCost, scaleValue);
    }
}
