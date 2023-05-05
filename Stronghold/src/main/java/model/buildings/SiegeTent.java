package model.buildings;

import model.Kingdom;
import model.army.WarMachineType;
import model.map.Cell;

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
}
