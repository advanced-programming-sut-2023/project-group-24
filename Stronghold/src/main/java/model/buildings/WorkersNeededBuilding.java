package model.buildings;

import model.Kingdom;
import model.People;
import model.map.Cell;

import java.util.ArrayList;

public class WorkersNeededBuilding extends Building {
    private ArrayList<People> workers;
    private boolean isSleeping;

    public WorkersNeededBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        workers = new ArrayList<>();
        isSleeping = false;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void assignWorker(People person) {
        workers.add(person);
    }

    public void unAssignWorker(People person) {
        workers.remove(person);
    }

    public boolean hasEnoughWorkers() {
        return workers.size() == getBuildingType().getWorkersNeeded();
    }

    public void changeSleepingMode() {
        isSleeping = true;
        workers = new ArrayList<>();
    }
}
