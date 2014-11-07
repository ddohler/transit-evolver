package com.azavea.evolve.transit;

import java.util.HashMap;
import java.util.Map;

public class VariableFlowCell extends CityCell {
    // The amount of traffic emanating from this cell; population, basically.
    protected final double production;
    // The location of this cell within the City (Euclidean at the moment).
    protected final Location location;
    // The places to which people travel from this cell.
    protected Map<CityCell, Double> outboundTravel;
    // Unique ID for this cell e.g. FIPS code.
    protected final String cellID;

    public VariableFlowCell(double production, String cellID, double x, double y) {
        this.production = production;
        this.location = new Location(x, y);
        this.outboundTravel = new HashMap<CityCell, Double>();
        this.cellID = new String(cellID);
    }

    public String getCellID() { return cellID; }
    public double getProduction() {
        return production;
    }
    public Location getLocation() {
        return location;
    }

    public double flowToCell(CityCell other) {
        return outboundTravel.get(other);
    }
    public double distanceToCell(CityCell other) {
        return location.distance_to(other.getLocation());
    }

    public double addOutboundDestination(CityCell other, double value) {
        return outboundTravel.put(other, value);
    }

    @Override
    public String toString() {
        return location.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof CityCell)) return false;
        CityCell otherCell = (CityCell)other;
        if (otherCell.getCellID() == this.cellID) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return cellID.hashCode();
    }
}
