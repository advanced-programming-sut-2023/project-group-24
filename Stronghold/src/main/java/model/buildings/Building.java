package model.buildings;

import model.Kingdom;
import model.map.Cell;

public class Building {
    private final Cell location;
    private Kingdom kingdom;
    private BuildingType buildingType;
    private int hp;

    public Building(Kingdom kingdom, Cell location, BuildingType buildingType) {
        this.location = location;
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

    public Cell getLocation() {
        return location;
    }
}
