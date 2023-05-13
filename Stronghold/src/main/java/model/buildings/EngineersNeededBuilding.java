package model.buildings;

import model.Kingdom;
import model.army.Soldier;
import model.map.Cell;

import java.util.ArrayList;

public class EngineersNeededBuilding extends WorkersNeededBuilding {
    private final ArrayList<Soldier> engineers;

    public EngineersNeededBuilding(Kingdom kingdom, Cell location, BuildingType buildingType) {
        super(kingdom, location, buildingType);
        engineers = new ArrayList<>();
    }

    public ArrayList<Soldier> getEngineers() {
        return engineers;
    }

    public void assignEngineers(Soldier soldier) {
        engineers.add(soldier);
    }

    @Override
    public boolean hasEnoughWorkers() {
        return engineers.size() >= getBuildingType().getWorkersNeeded();
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        if (!(this instanceof SiegeTent))
            output.add(String.format("number of engineers: %d/%d", engineers.size(), getBuildingType().getWorkersNeeded()));
        return output;
    }
}
