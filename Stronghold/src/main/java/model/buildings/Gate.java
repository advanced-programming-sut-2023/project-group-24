package model.buildings;

import model.Kingdom;
import model.map.Cell;

public class Gate extends DefenceBuilding {
    private boolean closed;

    public Gate(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        //TODO
    }

    public boolean isClosed() {
        return closed;
    }

    public void changeClosedState() {
        //TODO obvious. (this joke NEVER gets old)
    }
}
