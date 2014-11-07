package com.azavea.evolve.transit;

/**
 * The production in this cell flows to other cells based on the relative productions of the two cells.
 * A cell with equal production to this one will receive traffic = 0.5*production
 * A cell with much greater production receives traffic ~= production
 * A cell with much lower production receives traffic ~= 0
 */
public class ProportionalFlowCell extends CityCell {
    protected final double production;

    protected final Location location;

    protected final String cellID;

    public ProportionalFlowCell(double production, Location location, String cellID) {
        this.production = production;
        this.location = location;
        this.cellID = cellID;
    }
    public String getCellID() { return cellID; }
    public double getProduction() { return production; }
    public Location getLocation() { return location; }

    public double flowToCell(CityCell other) {
        double otherProduction = other.getProduction();
        return (otherProduction / (otherProduction + production)) * production;
    }

    public double distanceToCell(CityCell other) {
        return location.distance_to(other.getLocation());
    }
}
