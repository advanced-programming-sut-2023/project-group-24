package model.buildings;

import model.enums.Item;
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
        numberOfAnimals--;
    }

    public Pair<Item, Integer> produceCheese() {
        if (numberOfAnimals >= 3) return new Pair<>(Item.CHEESE, 4);
        return null;
    }

    public void useAnimal() {
        numberOfAnimals--;
    }

    public void produceAnimal() {
        numberOfAnimals++;
    }
}
