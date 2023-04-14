package model.army;

public enum WarMachineType {
    SIEGE_TOWER(2);

    private final int engineerNeeded;

    WarMachineType(int engineerNeeded) {
        this.engineerNeeded = engineerNeeded;
    }

    public static WarMachineType stringToEnum(String name) {
        //TODO ...
        return null;
    }

    public int getEngineerNeeded() {
        return engineerNeeded;
    }
}
