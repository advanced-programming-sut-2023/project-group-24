package model.army;

import model.StringFunctions;

public enum UnitState {
    STANDING,
    DEFENSIVE,
    OFFENSIVE;

    public static UnitState stringToEnum(String unitState) {
        String string = StringFunctions.turnSpaceToUnderline(unitState);
        for (UnitState value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}
