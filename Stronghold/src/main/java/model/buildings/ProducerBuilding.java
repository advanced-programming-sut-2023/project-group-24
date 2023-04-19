package model.buildings;

import model.Kingdom;
import model.map.Cell;

public class ProducerBuilding extends WorkersNeededBuilding {
    private int numberOfItemsWaitingToBeLoaded;

    public ProducerBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        //TODO
    }

    public void produceItem() {
        //TODO call the kingdom class to use the material needed and produce the produce. also check if it has workers. also check for its capacity.
    }

    public boolean automaticallyProduces() {
        //TODO if it is free, it will produce the item (like farms). if it is not free, it will need the user to produce.
    }
}
