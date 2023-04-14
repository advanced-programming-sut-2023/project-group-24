package model.storage;

import java.util.HashMap;

public class Storage {
    private HashMap<Material, Integer> materialStorage;
    private HashMap<Food, Integer> foodStorage;
    private HashMap<Weapon, Integer> weaponStorage;
    private HashMap<Armor, Integer> armorStorage;
    private int horseAmount;

    public int getStockedNumber(String name) {
        //TODO ...
        return 0;
    }

    public void addStock(String productName) {
        //TODO
    }

    public int getHorseAmount() {
        return horseAmount;
    }

    public void changeHorseAmount(int amount) {
        this.horseAmount += horseAmount;
    }
}
