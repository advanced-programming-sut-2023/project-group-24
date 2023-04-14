package model.buildings;

public class Building {
    private Kingdom kingdom;
    private Cell cell;
    private BuildingType buildingType;
    private int hp;

    public Building(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        //TODO
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int amount) {
        //TODO
    }
}
