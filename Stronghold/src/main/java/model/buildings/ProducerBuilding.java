package model.buildings;

import model.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

public class ProducerBuilding extends WorkersNeededBuilding {
    private final int numberOfItemsWaitingToBeLoaded;
    private Pair<Item, Integer> itemToProduce;

    public ProducerBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        numberOfItemsWaitingToBeLoaded = 0;
        itemToProduce = buildingType.getProduces().get(0);
    }

    public int getNumberOfItemsWaitingToBeLoaded() {
        return numberOfItemsWaitingToBeLoaded;
    }

    public Pair<Item, Integer> getItemToProduce() {
        return itemToProduce;
    }

    public void setItemToProduce(Item item) {
        for (Pair<Item, Integer> produce : getBuildingType().getProduces()) {
            if (produce.getObject1().equals(item)) {
                itemToProduce = produce;
                return;
            }
        }
    }

    public void produceItem() {
        //TODO call the kingdom class to use the material needed and produce the produce. also check if it has workers. also check for its capacity.
    }
}
