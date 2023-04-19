package model.buildings;

import model.Direction;
import model.army.Army;
import model.storage.Item;

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
    private Vector<Army> troopsItCanMake;
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
                 Vector<Army> troopsItCanMake,
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

    public HashMap<Item, Integer> getMaterialToBuild() {
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

    public HashMap<Item, Integer> getUses() {
        return uses;
    }

    public HashMap<Item, Integer> getProduces() {
        return produces;
    }

    public int getProducePrice() {
        return producePrice;
    }

    public Direction getDirection() {
        return direction;
    }

    public Vector<Army> getTroopsItCanMake() {
        return troopsItCanMake;
    }

    public Vector<Item> getItemsItCanHold() {
        return itemsItCanHold;
    }

    public Vector<Item> getItemsItCanMove() {
        return itemsItCanMove;
    }

    public boolean canBeDestroyedByTunnels() {
        return canBeDestroyedByTunnels;
    }

    public boolean canBeRepaired() {
        return canBeRepaired;
    }

    public BuildingType getBuildingTypeFromName(String name) {
        //TODO do a for loop of all building types
        return null;
    }
}
