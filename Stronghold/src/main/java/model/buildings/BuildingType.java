package model.buildings;

import model.Item;
import model.army.Army;
import model.army.ArmyType;
import model.army.Type;
import model.map.Texture;
import utils.Pair;

import java.util.Vector;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE("small stone gatehouse", Category.CASTLE, null, 0, 1000,
            0, 8, 2, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, GateAndStairs.class),
    LARGE_STONE_GATEHOUSE("large stone gatehouse", Category.CASTLE, new Pair<>(Item.STONE, -20), 0,
            2000, 0, 10, 2, 0, 0, 0,
            0, null, null, 0, null, null,
            null, true, true, null, GateAndStairs.class),
    DRAWBRIDGE("drawbridge", Category.CASTLE, new Pair<>(Item.WOOD, -10), 0, 1000, 0,
            0, 0, 0, 0, 0, 0, null,
            null, 0, null, null, null,
            true, true, null, GateAndStairs.class),
    LOOKOUT_TOWER("lookout tower", Category.CASTLE, new Pair<>(Item.STONE, -10), 0, 250,
            0, 0, 4, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, Building.class),
    PERIMETER_TOWER("perimeter tower", Category.CASTLE, new Pair<>(Item.STONE, -10), 0, 1000,
            0, 0, 3, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, Building.class),
    DEFENCE_TURRET("defence turret", Category.CASTLE, new Pair<>(Item.STONE, -15), 0, 1200,
            0, 0, 3, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, Building.class),
    SQUARE_TOWER("square tower", Category.CASTLE, new Pair<>(Item.STONE, -35), 0, 1600,
            0, 0, 3, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, true, null, Building.class),
    ROUND_TOWER("round tower", Category.CASTLE, new Pair<>(Item.STONE, -40), 0, 2000,
            0, 0, 3, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, true, null, Building.class),
    ARMOURY("armoury", Category.STORAGE, new Pair<>(Item.WOOD, -5), 0, 150,
            0, 0, 0, 50, 0, 0, 0,
            null, null, 0, null, Item.Category.WEAPON, null,
            false, false, null, StorageBuilding.class),
    BARRACKS("barracks", Category.ARMY_MAKER, new Pair<>(Item.STONE, -15), 0, 150,
            0, 0, 0, 0, 0, 0, 0,
            null, null, 0, Type.EUROPEAN, null, null,
            false, false, null, TroopMakerBuilding.class),
    MERCENARY_POST("mercenary post", Category.ARMY_MAKER, new Pair<>(Item.WOOD, -10), 0, 150,
            0, 0, 0, 0, 0, 0, 0,
            null, null, 0, Type.ARABIAN, null, null,
            false, false, null, TroopMakerBuilding.class),
    ENGINEER_GUILD("engineer guild", Category.ARMY_MAKER, new Pair<>(Item.WOOD, -10), 100, 150,
            0, 0, 0, 0, 0, 0, 0,
            null, null, 0, Type.ENGINEER, null, null,
            false, false, null, TroopMakerBuilding.class);
    NAME("", Category., null, 0, 0,
            0, 0, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class);

    //TODO add walls, town hall, etc
    ;
    private String name;
    private Category category;
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
    private Type troopsItCanMake;
    private Item.Category itemsItCanHold;
    private Vector<Item> itemsItCanMove;
    private boolean canBeDestroyedByTunnels;
    private boolean canBeRepaired;
    private Vector<Texture> canOnlyBuiltOn;
    private Class buildingClass;

    BuildingType(String name,
                 Category category,
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
                 Type troopsItCanMake,
                 Item.Category itemsItCanHold,
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

    public Category getCategory() {
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

    public Type getTroopsItCanMake() {
        return troopsItCanMake;
    }

    public Item.Category getItemsItCanHold() {
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

    public enum Category {
        CASTLE,
        STORAGE,
        ARMY_MAKER,
    }
}
