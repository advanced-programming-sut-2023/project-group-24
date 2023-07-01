package model.buildings;

import model.Kingdom;
import model.enums.Direction;
import model.map.Cell;

import java.util.ArrayList;

public class GateAndStairs extends DefenceBuilding {
    private boolean closed;
    private Direction direction;

    public GateAndStairs(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        closed = false;
        if (buildingType.getName().endsWith("left")) this.direction = Direction.UP;
        else if (buildingType.getName().endsWith("right")) this.direction = Direction.RIGHT;
        System.out.println(buildingType.getName());

        if (buildingType.getName().contains("small stone gatehouse")) setBuildingType(BuildingType.SMALL_STONE_GATEHOUSE);
        if (buildingType.getName().contains("large stone gatehouse")) setBuildingType(BuildingType.LARGE_STONE_GATEHOUSE);
        if (buildingType.getName().contains("stair")) setBuildingType(BuildingType.STAIR);
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

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        if (getBuildingType().getName().contains("stair "))
            return output;
        output.add("direction: " + direction.toString().toLowerCase());
        output.add(isClosed() ? "closed" : "open");
        return output;
    }
}
