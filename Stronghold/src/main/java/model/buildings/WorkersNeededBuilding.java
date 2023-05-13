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

    public ArrayList<People> getWorkers() {
        return workers;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void assignWorker(People person) {
        workers.add(person);
        person.setWorkStation(this);
    }

    public void unAssignWorker(People person) {
        workers.remove(person);
    }

    public boolean hasEnoughWorkers() {
        return workers.size() == getBuildingType().getWorkersNeeded();
    }

    public void changeSleepingMode() {
        if (isSleeping) isSleeping = false;
        else {
            isSleeping = true;
            workers = new ArrayList<>();
        }
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        if (!(this instanceof EngineersNeededBuilding))
            output.add(String.format("Number of workers: %d/%d !", workers.size(),
                    getBuildingType().getWorkersNeeded()));
        return output;
    }
}
