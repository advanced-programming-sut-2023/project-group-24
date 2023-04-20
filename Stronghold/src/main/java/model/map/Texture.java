package model.map;

import model.StringFunctions;
import utils.enums.Color;

public enum Texture {
    GROUND(true, true, Color.YELLOW_BACKGROUND),
    GRAVEL(true, true, Color.YELLOW_BACKGROUND_BRIGHT),
    SLAB(true, false, Color.WHITE_BACKGROUND),
    STONE(false, false, Color.MAGENTA_BACKGROUND),
    IRON(true, false, Color.RED_BACKGROUND),
    GRASS(true, true, Color.GREEN_BACKGROUND),
    GRASSLAND(true, true, Color.GREEN_BACKGROUND_BRIGHT),
    CONDENSED(true, true, Color.GREEN_BACKGROUND),
    OIL(false, true, Color.YELLOW_BACKGROUND),
    PLAIN(false, false, Color.BLACK_BACKGROUND_BRIGHT),
    SHALLOW_WATER(true, false, Color.BLACK_BACKGROUND_BRIGHT),
    RIVER(false, false, Color.BLUE_BACKGROUND),
    POND_SMALL(false, false, Color.BLACK_BACKGROUND_BRIGHT),
    POND_BIG(false, false, Color.BLUE_BACKGROUND),
    BEACH(true, false, Color.BLACK_BACKGROUND_BRIGHT),
    SEA(false, false, Color.BLUE_BACKGROUND);
    private final boolean canPass;
    private final boolean canBuild;
    private final Color color;

    Texture(boolean canPass, boolean canBuild, Color color) {
        this.canPass = canPass;
        this.canBuild = canBuild;
        this.color = color;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public boolean isCanBuild() {
        return canBuild;
    }

    public Color getColor() {
        return color;
    }

    public static Texture stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (Texture value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}