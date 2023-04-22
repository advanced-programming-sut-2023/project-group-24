package model.buildings;

import model.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

public class DairyProduce extends ProducerBuilding {
    private int numberOfAnimals;

    public DairyProduce(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        numberOfAnimals = 0;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void produceLeather() {
        getKingdom().changeStockNumber(new Pair<Item, Integer>(Item.LEATHER_ARMOR, 3));
        numberOfAnimals--;
    }

    public void produceCheese() {
        if (numberOfAnimals >= 3) getKingdom().changeStockNumber(new Pair<Item, Integer>(Item.CHEESE, 4));
    }

    public void useAnimal() {
        numberOfAnimals--;
    }

    public void produceAnimal() {
        numberOfAnimals++;
    }
}
