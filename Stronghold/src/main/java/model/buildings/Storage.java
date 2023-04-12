package model.buildings;

import utils.NumberOfThings;

public class Storage extends Building {
    private HashMap<Item, Integer> storage;

    public PopularityBuilding(Kingdom kingdom, BuildingType buildingType) {
        //TODO
    }

    public int putItem(NumberOfThings items) {
        //TODO add the items to the storage as much as possible. the return value is the number of items left to add. don't forget to call the kingdom's storage
    }

    public int removeItem(NumberOfThings items) {
        //TODO pretty much the same as putting items. don't forget to call the kingdom's storage.
    }
}
