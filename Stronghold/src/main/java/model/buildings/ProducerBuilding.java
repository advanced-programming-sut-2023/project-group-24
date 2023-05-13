package model.buildings;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.enums.Item;
import model.map.Cell;

import java.util.ArrayList;

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

    public boolean setItemToProduce(Item item) {
        for (int i = 0; i < getBuildingType().getProduces().size(); i++) {
            Pair<Item, Integer> produce = getBuildingType().getProduces().get(i);
            if (produce.getObject1().equals(item)) {
                itemToProduce = i;
                return true;
            }
        }
        return false;
    }

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

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        if (getBuildingType().getProduces() != null) {
            output.add("items this building can produce:");
            for (int i = 0; i < getBuildingType().getProduces().size(); i++) {
                Pair<Item, Integer> pair = getBuildingType().getProduces().get(i);
                String item = String.format("%d) %s - %d", i + 1, pair.getObject1().getName(), Math.abs(pair.getObject2()));
                if (getBuildingType().getUses() != null)
                    output.add(item + String.format("requires: %s - %d",
                            getBuildingType().getUses().get(i).getObject1().getName(), Math.abs(pair.getObject2())));
                else
                    output.add(item);
            }
        }
        if (getBuildingType() == BuildingType.QUARRY)
            output.add(String.format("items waiting to be loaded: %d/%d", numberOfItemsWaitingToBeLoaded,
                    getBuildingType().getStorageCapacity()));
        return output;
    }
}
