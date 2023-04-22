package model;

public enum KingdomColor {
    BLUE,
    RED,
    GREEN,
    BLACK,
    WHITE,
    BROWN,
    ORANGE,
    YELLOW;

    public static KingdomColor stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (KingdomColor value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

}
