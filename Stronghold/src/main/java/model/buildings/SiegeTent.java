package model.buildings;

import model.Kingdom;
import model.army.WarMachineType;
import model.map.Cell;

import java.util.ArrayList;

public class SiegeTent extends EngineersNeededBuilding {
    private WarMachineType producingWarMachine;

    public SiegeTent(Kingdom kingdom, Cell location, BuildingType buildingType) {
        super(kingdom, location, buildingType);
    }

    public void setProducingWarMachine(WarMachineType producingWarMachine) {
        this.producingWarMachine = producingWarMachine;
    }

    public WarMachineType getProducingWarMachine() {
        return producingWarMachine;
    }

    @Override
    public boolean hasEnoughWorkers() {
        return producingWarMachine.getEngineerNeeded() <= getEngineers().size();
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        output.add("producing " + producingWarMachine.name());
        output.add("engineers: " + getEngineers().size() + "/" + producingWarMachine.getEngineerNeeded());
        return output;
    }
}
