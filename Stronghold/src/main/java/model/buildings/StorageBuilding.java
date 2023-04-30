package model.buildings;

import model.Kingdom;
import model.enums.Item;
import model.map.Cell;
import utils.Pair;

import java.util.HashMap;

public class StorageBuilding extends Building {
    private final HashMap<Item, Integer> storage;

    public StorageBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        storage = new HashMap<>();
        for (Item value : Item.values()) {
            if (value.getCategory() == buildingType.getItemsItCanHold()) storage.put(value, 0);
        }
    }

    public int getNumberOfItemsInStorage() {
        int numberOfItemsInStorage = 0;
        for (Item item : storage.keySet()) {
            numberOfItemsInStorage += storage.get(item);
        }
        return numberOfItemsInStorage;
    }

    private Pair<Item, Integer> putItem(Pair<Item, Integer> items) {
        int capacityLeft = getBuildingType().getStorageCapacity() - getNumberOfItemsInStorage();
        if (items.getObject2() <= capacityLeft) {
            storage.replace(items.getObject1(), storage.get(items.getObject1()) + items.getObject2());
            return new Pair<>(items.getObject1(), 0);
        }
        storage.replace(items.getObject1(), storage.get(items.getObject1()) + capacityLeft);
        return new Pair<>(items.getObject1(), items.getObject2() - capacityLeft);
    }

    private Pair<Item, Integer> removeItem(Pair<Item, Integer> items) {
        int numberOfItemsLeft = storage.get(items.getObject1()) + items.getObject2();
        if (numberOfItemsLeft > 0) {
            storage.replace(items.getObject1(), numberOfItemsLeft);
            return new Pair<>(items.getObject1(), 0);
        }
        storage.replace(items.getObject1(), 0);
        return new Pair<>(items.getObject1(), numberOfItemsLeft);
    }

    public Pair<Item, Integer> changeItemCount(Pair<Item, Integer> items) {
        if (items.getObject2() > 0) return putItem(items);
        else return removeItem(items);
    }

    public int getStockedNumber(Item item) {
        return storage.get(item);
    }
}
