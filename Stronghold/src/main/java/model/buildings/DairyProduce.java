package model.buildings;

import model.enums.Item;
import model.Kingdom;
import model.map.Cell;
import utils.Pair;

import java.util.ArrayList;

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

    public void produceAnimal() {
        numberOfAnimals++;
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        output.add(String.format("number of cows: %d/3", numberOfAnimals));
        return output;
    }
}
