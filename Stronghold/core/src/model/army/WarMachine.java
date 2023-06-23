package model.army;

import model.Kingdom;
import model.map.Cell;

public class WarMachine extends Army {

    private final WarMachineType warMachineType;

    public WarMachine(Cell location, ArmyType armyType, Kingdom owner, WarMachineType warMachineType) {
        super(location, armyType, owner);
        this.warMachineType = warMachineType;
    }

    public WarMachineType getWarMachineType() {
        return warMachineType;
    }
}
