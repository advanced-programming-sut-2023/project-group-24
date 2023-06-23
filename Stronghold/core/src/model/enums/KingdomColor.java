package model.enums;

import controller.MainController;

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
        String string = MainController.turnSpaceToUnderline(name);
        for (KingdomColor value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

}
