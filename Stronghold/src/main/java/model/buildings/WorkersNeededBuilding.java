package model.buildings;

import model.Kingdom;
import model.People;
import model.map.Cell;

import java.util.ArrayList;

public class WorkersNeededBuilding extends Building {
    private ArrayList<People> workers;
    private boolean isSleeping;

    public WorkersNeededBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        //TODO
    }

    public void assignWorker(People person) {
        //TODO assign the said worker
    }

    public boolean hasEnoughWorkers() {
        //TODO i think its so obvious, it doesnt need proper punctuation, except commas
    }

    public void changeSleepingMode() {
        //TODO obvious
    }
}
