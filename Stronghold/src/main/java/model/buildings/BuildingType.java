package model.buildings;

import model.Item;
import model.StringFunctions;
import model.army.Army;
import utils.Pair;

import java.util.Vector;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE("small stone gatehouse", "castle", null, 0, 1000,
            0, 8, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, "building");

    private String name;
    private String category;
    private Pair<Item, Integer> materialToBuild;
    private int price;
    private int maxHp;

    private int workersNeeded;
    private int homeCapacity;
    private int height;
    private int storageCapacity;
    private int attackPoint;
    private int popularityRate;
    private int produceRate;
    private Vector<Pair<Item, Integer>> uses;
    private Vector<Pair<Item, Integer>> produces;
    private int producePrice;
    private Vector<Army> troopsItCanMake;
    private Vector<Item> itemsItCanHold;
    private Vector<Item> itemsItCanMove;
    private boolean canBeDestroyedByTunnels;
    private boolean canBeRepaired;
    private String buildingClass;

    BuildingType(String name,
                 String category,
                 Pair<Item, Integer> materialToBuild,
                 int price,
                 int maxHp,
                 int workersNeeded,
                 int homeCapacity,
                 int height,
                 int storageCapacity,
                 int attackPoint,
                 int popularityRate,
                 int produceRate,
                 Vector<Pair<Item, Integer>> uses,
                 Vector<Pair<Item, Integer>> produces,
                 int producePrice,
                 Vector<Army> troopsItCanMake,
                 Vector<Item> itemsItCanHold,
                 Vector<Item> itemsItCanMove,
                 boolean canBeDestroyedByTunnels,
                 boolean canBeRepaired,
                 String buildingClass) {
        this.name = name;
        this.category = category;
        this.materialToBuild = materialToBuild;
        this.price = price;
        this.maxHp = maxHp;
        this.workersNeeded = workersNeeded;
        this.homeCapacity = homeCapacity;
        this.height = height;
        this.storageCapacity = storageCapacity;
        this.attackPoint = attackPoint;
        this.popularityRate = popularityRate;
        this.produceRate = produceRate;
        this.uses = uses;
        this.produces = produces;
        this.producePrice = producePrice;
        this.troopsItCanMake = troopsItCanMake;
        this.itemsItCanHold = itemsItCanHold;
        this.itemsItCanMove = itemsItCanMove;
        this.canBeDestroyedByTunnels = canBeDestroyedByTunnels;
        this.canBeRepaired = canBeRepaired;
        this.buildingClass = buildingClass;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Pair<Item, Integer> getMaterialToBuild() {
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

    public Vector<Pair<Item, Integer>> getUses() {
        return uses;
    }

    public Vector<Pair<Item, Integer>> getProduces() {
        return produces;
    }

    public int getProducePrice() {
        return producePrice;
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

    public String getBuildingClass() {
        return buildingClass;
    }

    public static BuildingType stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (BuildingType value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }
}
