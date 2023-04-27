package model.buildings;

import model.enums.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

public class PopularityBoosterBuilding extends ProducerBuilding {
    public PopularityBoosterBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
    }

    public void addPopularity() {
        if (!hasEnoughWorkers()) return;
        if (getBuildingType().getUses() != null) {
            for (Pair<Item, Integer> use : getBuildingType().getUses())
                getKingdom().changeStockNumber(use);
        }
        getKingdom().changePopularity(getBuildingType().getPopularityRate());
    }
}
