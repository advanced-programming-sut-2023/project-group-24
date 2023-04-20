package model.buildings;

import model.Direction;
import model.Kingdom;
import model.map.Cell;

public class GateAndStairs extends DefenceBuilding {
    private boolean closed;
    private final Direction direction;

    public GateAndStairs(Kingdom kingdom, Cell cell, BuildingType buildingType, Direction direction) {
        super(kingdom, cell, buildingType);
        this.direction = direction;
        closed = false;
    }

    public boolean isClosed() {
        return closed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeClosedState() {
        closed = !closed;
    }
}
