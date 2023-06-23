package model.enums;

import controller.MainController;

public enum Direction {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction stringToEnum(String name) {
        String string = MainController.turnSpaceToUnderline(name);
        for (Direction value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}
