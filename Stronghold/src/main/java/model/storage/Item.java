package model.storage;

public enum Item {
    SWORD(Weapon.SWORD, 20),
    LEATHER(Armor.LEATHER, 15);
    private Enum anEnum;
    private int price;

    Item(Enum anEnum, int price) {
        this.anEnum = anEnum;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
