package model.buildings;

import model.Kingdom;
import model.map.Cell;

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
        //TODO call the kingdom storage
    }

    public void useAnimal() {
        numberOfAnimals--;
    }

    public void produceAnimal() {
        numberOfAnimals++;
    }
}
