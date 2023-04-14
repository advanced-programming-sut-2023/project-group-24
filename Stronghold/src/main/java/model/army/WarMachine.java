package model.army;

import model.map.Cell;

public class WarMachine extends Army {

    private final WarMachineType warMachineType;

    public WarMachine(Cell location, ArmyType armyType, WarMachineType warMachineType) {
        super(location, armyType);
        this.warMachineType = warMachineType;
    }

    public WarMachineType getWarMachineType() {
        return warMachineType;
    }
}
