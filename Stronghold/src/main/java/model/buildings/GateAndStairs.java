package model.buildings;

import model.Direction;
import model.Kingdom;
import model.map.Cell;

public class GateAndStairs extends DefenceBuilding {
    private boolean closed;
    private Direction direction;

    public GateAndStairs(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        direction = Direction.UP;
        closed = false;
    }

    public boolean isClosed() {
        return closed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void changeClosedState() {
        closed = !closed;
    }
}
