package model.buildings;

import model.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public enum BuildingType {;
//    SMALL_STONE_GATEHOUSE("small stone gatehouse", "Castle", null, 0, 100, 0, 8, 0, 0, 0, 0, 0, null, null, 0, false);

    private String name;
    private String category;
    private HashMap<Item, Integer> materialToBuild;
    private int price;

    //TODO add direction, and list of troops to make and their cost, and list of items to be held, and movers, and cabBeDestroyedByTunnels
    private int maxHp;
    private int workersNeeded;
    private int homeCapacity;
    private int height;
    private int storageCapacity;
    private int attackPoint;
    private int popularityRate;
    private int produceRate;
    private HashMap<Item, Integer> uses;
    private HashMap<Item, Integer> produces;
    private int producePrice;
    private Direction direction;
    private Vector<Troop> troopsItCanMake;
    private Vector<Item> itemsItCanHold;
    private Vector<Item> itemsItCanMove;
    private boolean canBeDestroyedByTunnels;
    private boolean canBeRepaired;

    BuildingType(String name,
                 String category,
                 HashMap<Item, Integer> materialToBuild,
                 int price,
                 int maxHp,
                 int workersNeeded,
                 int homeCapacity,
                 int height,
                 int storageCapacity,
                 int attackPoint,
                 int popularityRate,
                 int produceRate,
                 HashMap<Item, Integer> uses,
                 HashMap<Item, Integer> produces,
                 int producePrice,
                 Direction direction,
                 Vector<Soldier> soldiersItCanMake,
                 Vector<Item> itemsItCanHold,
                 Vector<Item> itemsItCanMove,
                 boolean canBeDestroyedByTunnels,
                 boolean canBeRepaired) {
        //TODO
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public NumberOfThings getMaterialToBuild() {
        return materialToBuild;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getWorkersNeeded() {
        return workersNeeded;
    }

    public int getHomeCapacity() {
        return homeCapacity;
    }

    public int getHeight() {
        return height;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public int getProduceRate() {
        return produceRate;
    }

    public NumberOfThings getProduces() {
        return produces;
    }

    public NumberOfThings getUses() {
        return uses;
    }

    public boolean CanHaveBlazer() {
        return canHaveBlazer;
    }

    public BuildingType getBuildingTypeFromName(String name) {
        //TODO do a for loop of all building types
        return null;
    }
}
