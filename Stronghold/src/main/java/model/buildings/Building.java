package model.buildings;

import model.Kingdom;
import model.People;
import model.map.Cell;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private final Kingdom kingdom;
    private final Cell location;
    private final BuildingType buildingType;
    private int hp;

    public Building(Kingdom kingdom, Cell location, BuildingType buildingType) {
        this.kingdom = kingdom;
        this.location = location;
        this.buildingType = buildingType;
        this.hp = buildingType.getMaxHp();
        location.setExistingBuilding(this);
        kingdom.addBuilding(this);
    }

    public static Building getBuildingFromBuildingType(Kingdom kingdom, Cell location, BuildingType buildingType) {
        try {
            return (Building) buildingType.getBuildingClass().getConstructor(Kingdom.class,
                    Cell.class, BuildingType.class).newInstance(kingdom, location, buildingType);
        } catch (Exception ignored) {
            return null;
        }
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public Cell getLocation() {
        return location;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int amount) {
        hp -= amount;
        if (hp <= 0) {
            location.setExistingBuilding(null);
            kingdom.removeBuilding(this);
            if (this instanceof WorkersNeededBuilding) {
                for (People worker : ((WorkersNeededBuilding) this).getWorkers()) {
                    kingdom.removePeople(worker);
                }
            }
        }
    }

    public void repair() {
        hp = buildingType.getMaxHp();
    }

    public ArrayList<String> showDetails() {
        return new ArrayList<>(List.of(buildingType.getName(), "hp = " + hp + "/" + buildingType.getMaxHp()));
    }
}
