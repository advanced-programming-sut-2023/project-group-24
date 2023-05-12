package model.buildings;

import model.enums.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

public class ProducerBuilding extends WorkersNeededBuilding {
    private int numberOfItemsWaitingToBeLoaded;
    private int itemToProduce;

    public ProducerBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        numberOfItemsWaitingToBeLoaded = 0;
        itemToProduce = 0;
    }

    public int getNumberOfItemsWaitingToBeLoaded() {
        return numberOfItemsWaitingToBeLoaded;
    }

    public int getItemToProduce() {
        return itemToProduce;
    }

    public void setItemToProduce(Item item) {
        for (int i = 0; i < getBuildingType().getProduces().size(); i++) {
            Pair<Item, Integer> produce = getBuildingType().getProduces().get(i);
            if (produce.getObject1().equals(item)) {
                itemToProduce = i;
                return;
            }
        }
    }

    //todo Pair<Pair<Item, Integer>, Pair<Item, Integer>>

    public Pair<Pair<Item, Integer>, Pair<Item, Integer>> produceItem() {
        if (!hasEnoughWorkers()) return null;
        if (getBuildingType().getName().equals("quarry")) {
            numberOfItemsWaitingToBeLoaded += getBuildingType().getProduces().get(0).getObject2();
            if (numberOfItemsWaitingToBeLoaded > getBuildingType().getStorageCapacity())
                numberOfItemsWaitingToBeLoaded = getBuildingType().getStorageCapacity();
            return null;
        }
        if (getBuildingType().getUses() != null) {
            return new Pair<>(getBuildingType().getUses().get(itemToProduce),
                    getBuildingType().getProduces().get(itemToProduce));
        }
        return new Pair<>(null, getBuildingType().getProduces().get(itemToProduce));
    }

    public void loadItem(int amount) {
        numberOfItemsWaitingToBeLoaded -= amount;
    }
}
