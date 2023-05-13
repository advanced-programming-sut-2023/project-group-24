package model.army;

import controller.MainController;

public enum UnitState {
    STANDING(0),
    DEFENSIVE(1),
    OFFENSIVE(2);
    private final int fireRange;

    UnitState(int fireRange) {
        this.fireRange = fireRange;
    }

    public static UnitState stringToEnum(String unitState) {
        String string = MainController.turnSpaceToUnderline(unitState);
        for (UnitState value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public int getFireRange() {
        return fireRange;
    }
}
