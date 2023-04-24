package model.map;

import model.StringFunctions;

public enum Tree {
    DATE_PALM,
    COCONUT_PALM,
    CHERRY_PALM,
    OLIVE_TREE,
    DESERT_SHRUB;

    public static Tree stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (Tree value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}
