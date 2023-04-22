package model.buildings;

import model.Item;
import model.army.Army;
import model.map.Texture;
import utils.Pair;

import java.util.Vector;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE("small stone gatehouse", "castle", null, 0, 1000,
            0, 8, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, Building.class);

    private final String name;
    private final String category;
    private final Pair<Item, Integer> materialToBuild;
    private final int price;
    private final int maxHp;

    private final int workersNeeded;
    private final int homeCapacity;
    private final int height;
    private final int storageCapacity;
    private final int attackPoint;
    private final int popularityRate;
    private final int produceRate;
    private final Vector<Pair<Item, Integer>> uses;
    private final Vector<Pair<Item, Integer>> produces;
    private final int producePrice;
    private final Vector<Army> troopsItCanMake;
    private final Vector<Item> itemsItCanHold;
    private final Vector<Item> itemsItCanMove;
    private final boolean canBeDestroyedByTunnels;
    private final boolean canBeRepaired;
    private final Vector<Texture> canOnlyBuiltOn;
    private final Class buildingClass;

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
                 Vector<Texture> canOnlyBuiltOn,
                 Class buildingClass) {
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
        this.canOnlyBuiltOn = canOnlyBuiltOn;
        this.buildingClass = buildingClass;
    }

    public static BuildingType getBuildingTypeFromName(String name) {
        for (BuildingType buildingType : values()) {
            if (buildingType.name.equals(name)) return buildingType;
        }
        return null;
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

    public Class getBuildingClass() {
        return buildingClass;
    }

    public Vector<Texture> getCanOnlyBuiltOn() {
        return canOnlyBuiltOn;
    }
}
