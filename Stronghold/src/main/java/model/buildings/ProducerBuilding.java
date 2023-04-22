package model.buildings;

import model.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

public class ProducerBuilding extends WorkersNeededBuilding {
    private int numberOfItemsWaitingToBeLoaded;
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
        if (!hasEnoughWorkers()) return;
        if (getBuildingType().getName().equals("quarry")) {
            numberOfItemsWaitingToBeLoaded += getBuildingType().getProduces().get(0).getObject2();
            return;
        }
        if (getBuildingType().getUses() != null) {
            for (Pair<Item, Integer> use : getBuildingType().getUses())
                getKingdom().changeStockNumber(use);
        }
        getKingdom().changeStockNumber(itemToProduce);
    }
}
