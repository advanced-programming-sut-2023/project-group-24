package model.enums;

import controller.MainController;

import java.util.List;

public enum Item {
    BOW(30, Category.WEAPON),
    CROSSBOW(58, Category.WEAPON),
    SPEAR(20, Category.WEAPON),
    PIKE(36, Category.WEAPON),
    MACE(58, Category.WEAPON),
    SWORD(58, Category.WEAPON),
    LEATHER_ARMOR(25, Category.ARMOR),
    METAL_ARMOR(58, Category.ARMOR),
    MEAT(8, Category.FOOD),
    APPLE(8, Category.FOOD),
    CHEESE(8, Category.FOOD),
    BREAD(8, Category.FOOD),
    WHEAT(23, Category.MATERIAL),
    FLOUR(32, Category.MATERIAL),
    HOPS(15, Category.MATERIAL),
    ALE(20, Category.MATERIAL),
    STONE(14, Category.MATERIAL),
    IRON(45, Category.MATERIAL),
    PITCH(20, Category.MATERIAL),
    WOOD(4, Category.MATERIAL);


    public static final List<Item> Values = List.of(values());
    private final Category category;
    private final int price;

    Item(int price, Category category) {
        this.category = category;
        this.price = price;
    }

    public static Item stringToEnum(String name) {
        String string = MainController.turnSpaceToUnderline(name);
        for (Item value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public enum Category {
        WEAPON,
        FOOD,
        ARMOR,
        MATERIAL
    }
}
