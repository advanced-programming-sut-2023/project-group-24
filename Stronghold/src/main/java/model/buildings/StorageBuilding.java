package model.buildings;

import model.enums.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

import java.util.HashMap;

public class StorageBuilding extends Building {
    private final HashMap<Item, Integer> storage;

    public StorageBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        storage = new HashMap<>();
    }

    public int getNumberOfItemsInStorage() {
        int numberOfItemsInStorage = 0;
        for (Item item : storage.keySet()) {
            numberOfItemsInStorage += storage.get(item);
        }
        return numberOfItemsInStorage;
    }

    public Pair<Item, Integer> putItem(Pair<Item, Integer> items) {
        int capacityLeft = getBuildingType().getStorageCapacity() - getNumberOfItemsInStorage();
        if (storage.containsKey(items.getObject1())) {
            if (items.getObject2() <= capacityLeft) {
                storage.replace(items.getObject1(), storage.get(items.getObject1()) + items.getObject2());
                return null;
            }
            storage.replace(items.getObject1(), storage.get(items.getObject1()) + capacityLeft);
            return new Pair<>(items.getObject1(), items.getObject2() - capacityLeft);
        }
        if (items.getObject2() <= capacityLeft) {
            storage.put(items.getObject1(), items.getObject2());
            return null;
        }
        storage.put(items.getObject1(), capacityLeft);
        return new Pair<>(items.getObject1(), items.getObject2() - capacityLeft);
    }

    public Pair<Item, Integer> removeItem(Pair<Item, Integer> items) {
        if (!storage.containsKey(items.getObject1())) return items;
        int numberOfItemsLeft = storage.get(items.getObject1()) - items.getObject2();
        if (numberOfItemsLeft < 0) {
            storage.replace(items.getObject1(), numberOfItemsLeft);
            return null;
        }
        storage.remove(items.getObject1());
        return new Pair<>(items.getObject1(), -numberOfItemsLeft);
    }

    public int getStockedNumber(Item item) {
        return storage.get(item);
    }
}
