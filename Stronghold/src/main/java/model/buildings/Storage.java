package model.buildings;

import utils.NumberOfThings;

import java.util.ArrayList;

public class Storage extends Building{
    int capacity;
    ArrayList<NumberOfThings> storage;

    public Storage(String type,
                   String category,
                   User owner,
                   String materialToBuild,
                   int numberOfMaterialToBuild,
                   int price,
                   int capacity) {
        //TODO
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFullSpace() {
        //TODO
    }

    public void addItem(NumberOfThings things) {
        //TODO
    }
}
