package model.map;

import controller.MainController;
import javafx.scene.paint.Color;

public enum Texture {
    GROUND(true, true, Color.YELLOW),
    GRAVEL(true, true, Color.LIGHTYELLOW),
    SLAB(true, false, Color.WHITE),
    STONE(false, false, Color.MAGENTA),
    IRON(true, false, Color.RED),
    GRASS(true, true, Color.GREEN),
    GRASSLAND(true, true, Color.LIGHTGREEN),
    CONDENSED(true, true, Color.GREEN),
    OIL(false, true, Color.YELLOW),
    PLAIN(false, false, Color.BLACK),
    SHALLOW_WATER(true, false, Color.BLACK),
    RIVER(false, false, Color.BLUE),
    POND_SMALL(false, false, Color.BLACK),
    POND_BIG(false, false, Color.BLUE),
    BEACH(true, false, Color.BLACK),
    SEA(false, false, Color.BLUE);
    private final boolean canPass;
    private final boolean canBuild;
    private final Color color;

    Texture(boolean canPass, boolean canBuild, Color color) {
        this.canPass = canPass;
        this.canBuild = canBuild;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("-", " ");
    }
}