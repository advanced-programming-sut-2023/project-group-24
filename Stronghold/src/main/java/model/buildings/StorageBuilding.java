package model.buildings;

import java.util.HashMap;

public class StorageBuilding extends Building {
    private HashMap<Item, Integer> storage;

    public PopularityBuilding(Kingdom kingdom, BuildingType buildingType) {
        //TODO
    }

    public int putItem(HashMap<Item, Integer> items) {
        //TODO add the items to the storage as much as possible. the return value is the number of items left to add. don't forget to call the kingdom's storage
    }

    public int removeItem(HashMap<Item, Integer> items) {
        //TODO pretty much the same as putting items. don't forget to call the kingdom's storage.
    }
}
