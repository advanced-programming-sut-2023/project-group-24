package model.buildings;

import model.enums.Direction;
import model.Kingdom;
import model.map.Cell;

import java.util.ArrayList;

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
        if (direction == Direction.RIGHT || direction == Direction.LEFT) this.direction = Direction.RIGHT;
        else this.direction = Direction.UP;
    }

    public void changeClosedState() {
        closed = !closed;
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        if (getBuildingType() == BuildingType.STAIR)
            return output;
        output.add("direction: " + direction.toString().toLowerCase());
        output.add(isClosed() ? "closed" : "open");
        return output;
    }
}
