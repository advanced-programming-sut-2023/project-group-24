package model.buildings;

import model.Kingdom;
import model.map.Cell;

public class TroopMakerBuilding extends Building {
    public TroopMakerBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
    }

    public void produceTroop(String name) {
        //TODO check for a troop with that name, get its price, and try to produce it.
    }
}
