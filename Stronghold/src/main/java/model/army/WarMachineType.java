package model.army;

import model.StringFunctions;

public enum WarMachineType {
    SIEGE_TOWER(2);

    private final int engineerNeeded;

    WarMachineType(int engineerNeeded) {
        this.engineerNeeded = engineerNeeded;
    }

    public static WarMachineType stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (WarMachineType value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public int getEngineerNeeded() {
        return engineerNeeded;
    }
}
