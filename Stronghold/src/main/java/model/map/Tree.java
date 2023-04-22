package model.map;

import model.StringFunctions;

public enum Tree {
    erger;
    public static Tree stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (Tree value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}
