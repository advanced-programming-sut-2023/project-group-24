package model.army;

import controller.MainController;

public enum WarMachineType {
    SIEGE_TOWER(4),
    PORTABLE_SHIELDS(1),
    BATTERING_RAMS(4),
    CATAPULT(2),
    TREBUCHETS(3),
    FIRE_BALLISTA(2);


    private final int engineerNeeded;

    WarMachineType(int engineerNeeded) {
        this.engineerNeeded = engineerNeeded;
    }

    public static WarMachineType stringToEnum(String name) {
        String string = MainController.turnSpaceToUnderline(name);
        for (WarMachineType value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public int getEngineerNeeded() {
        return engineerNeeded;
    }
}
