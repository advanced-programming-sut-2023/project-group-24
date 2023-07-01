package model.map;

import com.badlogic.gdx.graphics.Color;
import controller.MainController;

public enum Texture {
    GROUND(true, true, Color.YELLOW),
    GRAVEL(true, true, Color.DARK_GRAY),
    SLAB(true, true, Color.LIGHT_GRAY),
    STONE(false, false, Color.WHITE),
    IRON(true, false, Color.RED),
    GRASS(true, true, Color.GREEN),
    GRASSLAND(true, true, Color.GREEN),
    CONDENSED(true, true, Color.GREEN),
    OIL(false, true, Color.BLACK),
    PLAIN(false, false, new Color(0, 0, 0.5f, 1)),
    SHALLOW_WATER(true, false, new Color(0.5f, 0.5f, 1, 1)),
    RIVER(false, false, Color.BLUE),
    POND_SMALL(false, false, Color.BLUE),
    POND_BIG(false, false, Color.BLUE),
    BEACH(true, false, Color.ORANGE),
    SEA(false, false, Color.BLUE);
    private final boolean canPass;
    private final boolean canBuild;
    private  final Color color;

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

    public Color getColor() {
        return color;
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