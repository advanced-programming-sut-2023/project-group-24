package model.buildings;

import model.Kingdom;
import model.map.Cell;

public class PopularityBoosterBuilding extends ProducerBuilding {
    public PopularityBoosterBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
    }

    public void addPopularity() {
        //TODO check for the needed material, if there are any
    }
}
