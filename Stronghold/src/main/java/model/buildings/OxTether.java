package model.buildings;

public class OxTether extends Building {
    ProducerBuilding quarry;
    public DefenceBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        //TODO
    }

    private void findNearestNonEmptyQuarry() {
        //TODO obvious. it is always called in move.
    }

    public void move() {
        //TODO find the starting point and the destination, and move the appropriate number of items.
    }
}
