package model.map;

import controller.MainController;

public enum Texture {
    GROUND(true, true),
    GRAVEL(true, true),
    SLAB(true, true),
    STONE(false, false),
    IRON(true, false),
    GRASS(true, true),
    GRASSLAND(true, true),
    CONDENSED(true, true),
    OIL(false, true),
    PLAIN(false, false),
    SHALLOW_WATER(true, false),
    RIVER(false, false),
    POND_SMALL(false, false),
    POND_BIG(false, false),
    BEACH(true, false),
    SEA(false, false);
    private final boolean canPass;
    private final boolean canBuild;

    Texture(boolean canPass, boolean canBuild) {
        this.canPass = canPass;
        this.canBuild = canBuild;
    }

    public static Texture stringToEnum(String name) {
        String string = MainController.turnSpaceToUnderline(name);
        for (Texture value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public boolean isCanBuild() {
        return canBuild;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("-", " ");
    }
}