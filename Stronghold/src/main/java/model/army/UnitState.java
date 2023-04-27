package model.army;

import controller.MainController;

public enum UnitState {
    STANDING,
    DEFENSIVE,
    OFFENSIVE;

    public static UnitState stringToEnum(String unitState) {
        String string = MainController.turnSpaceToUnderline(unitState);
        for (UnitState value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}
