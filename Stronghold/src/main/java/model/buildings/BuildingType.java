package model.buildings;

import model.enums.Item;
import model.army.Type;
import model.map.Texture;
import utils.Pair;

import java.util.List;
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
            false, false, null, TroopMakerBuilding.class),
    KILLING_PIT("killing pit", Category.TRAP, new Pair<>(Item.WOOD, -6), 0, 1,
            0, 0, 0, 0, 150, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class),
    OIL_SMELTER("oil smelter", Category.CASTLE, new Pair<>(Item.IRON, -10), 100, 75,
            1, 0, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, EngineersNeededBuilding.class),
    PITCH_DITCH("pitch ditch", Category.TRAP, new Pair<>(Item.PITCH, -1), 0, 1,
            0, 0, 0, 0, 225, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class),
    CAGED_WAR_DOGS("caged war dogs", Category.CASTLE, new Pair<>(Item.WOOD, -10), 100, 50,
            0, 0, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class),
    SIEGE_TENT("siege tent", Category.CASTLE, null, 0, 1,
            1, 0, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, EngineersNeededBuilding.class),
    STABLE("stable", Category.CASTLE, new Pair<>(Item.WOOD, -20), 400, 100,
            0, 0, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Stable.class),
    APPLE_ORCHARD("apple orchard", Category.FARM, new Pair<>(Item.WOOD, -5), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            null, new Vector<>(List.of(new Pair<>(Item.APPLE, 6))), 0, null,
            null, null, false, false,
            new Vector<>(List.of(Texture.GRASS, Texture.CONDENSED)), ProducerBuilding.class),
    DIARY_FARM("dairy farmer", Category.FARM, new Pair<>(Item.WOOD, -10), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            null, null, 0, null, null,
            null, false, false,
            new Vector<>(List.of(Texture.GRASS, Texture.CONDENSED)), DairyProduce.class),
    HOPS_FARM("hops farmer", Category.FARM, new Pair<>(Item.WOOD, -15), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            null, new Vector<>(List.of(new Pair<>(Item.HOPS, 3))), 0, null,
            null, null, false, false,
            new Vector<>(List.of(Texture.GRASS, Texture.CONDENSED)), ProducerBuilding.class),
    WHEAT_FARM("wheat farmer", Category.FARM, new Pair<>(Item.WOOD, -15), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            null, new Vector<>(List.of(new Pair<>(Item.WHEAT, 2))), 0, null,
            null, null, false, false,
            new Vector<>(List.of(Texture.GRASS, Texture.CONDENSED)), ProducerBuilding.class),
    HUNTER_POST("hunter post", Category.FARM, new Pair<>(Item.WOOD, -5), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            null, new Vector<>(List.of(new Pair<>(Item.MEAT, 3))), 0, null,
            null, null, false, false,
            null, ProducerBuilding.class),
    BAKERY("bakery", Category.FOOD_PROCESSING, new Pair<>(Item.WOOD, -10), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            new Vector<>(List.of(new Pair<>(Item.FLOUR, -1))), new Vector<>(List.of(new Pair<>(Item.BREAD, 1))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    BREWER("brewer", Category.FOOD_PROCESSING, new Pair<>(Item.WOOD, -10), 0, 100,
            1, 0, 0, 0, 0, 0, 1,
            new Vector<>(List.of(new Pair<>(Item.HOPS, -1))), new Vector<>(List.of(new Pair<>(Item.ALE, 1))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    GRANARY("granary", Category.STORAGE, new Pair<>(Item.WOOD, -5), 0, 150,
            0, 0, 0, 250, 0, 0, 0,
            null, null, 0, null, Item.Category.FOOD, null,
            false, false, null, StorageBuilding.class),
    INN("inn", Category.FOOD_PROCESSING, new Pair<>(Item.WOOD, -20), 100, 150,
            1, 0, 0, 0, 0, 4, 1,
            new Vector<>(List.of(new Pair<>(Item.ALE, -1))), null,
            0, null, null, null,
            false, false, null, PopularityBoosterBuilding.class),
    MILL("mill", Category.FOOD_PROCESSING, new Pair<>(Item.WOOD, -20), 0, 100,
            3, 0, 0, 0, 0, 0, 3,
            new Vector<>(List.of(new Pair<>(Item.WHEAT, -6))), new Vector<>(List.of(new Pair<>(Item.FLOUR, 12))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    IRON_MINE("iron mine", Category.INDUSTRY, new Pair<>(Item.WOOD, -20), 0, 100,
            2, 0, 0, 0, 0, 0, 1,
            null, new Vector<>(List.of(new Pair<>(Item.IRON, 2))),
            0, null, null, null,
            false, false, new Vector<>(List.of(Texture.IRON)),
            ProducerBuilding.class),
    MARKET("market", Category.INDUSTRY, new Pair<>(Item.WOOD, -5), 0, 100,
            1, 0, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, WorkersNeededBuilding.class),
    OX_TETHER("ox tether", Category.INDUSTRY, new Pair<>(Item.WOOD, -5), 0, 50,
            1, 0, 0, 12, 0, 0, 0,
            null, null, 0, null, null,
            new Vector<>(List.of(Item.STONE)), false, false,
            null, WorkersNeededBuilding.class),
    PITCH_RIG("pitch rig", Category.INDUSTRY, new Pair<>(Item.WOOD, -20), 0, 100,
            1, 0, 0, 0, 0, 0, 0,
            null, new Vector<>(List.of(new Pair<>(Item.PITCH, 1))), 0, null,
            null, null, false, false,
            new Vector<>(List.of(Texture.OIL)), ProducerBuilding.class),
    QUARRY("quarry", Category.INDUSTRY, new Pair<>(Item.WOOD, -20), 0, 100,
            3, 0, 0, 36, 0, 0, 0,
            null, new Vector<>(List.of(new Pair<>(Item.STONE, 3))), 0, null,
            null, null, false, false,
            new Vector<>(List.of(Texture.STONE)), ProducerBuilding.class),
    WOOD_CUTTER("wood cutter", Category.INDUSTRY, new Pair<>(Item.WOOD, -3), 0, 75,
            1, 0, 0, 0, 0, 0, 0,
            null, new Vector<>(List.of(new Pair<>(Item.WOOD, 6))), 0, null,
            null, null, false, false,
            null, ProducerBuilding.class),
    STOCKPILE("stockpile", Category.STORAGE, null, 0, 100,
            0, 0, 0, 200, 0, 0, 0,
            null, null, 0, null, Item.Category.MATERIAL, null,
            false, false, null, StorageBuilding.class),
    HOVEL("hovel", Category.TOWN, new Pair<>(Item.WOOD, -6), 0, 100,
            0, 8, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class),
    CHURCH("church", Category.TOWN, null, 500, 150,
            0, 0, 0, 0, 0, 2, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class),
    CATHEDRAL("cathedral", Category.TOWN, null, 1000, 200,
            0, 0, 0, 0, 0, 2, 0,
            null, null, 0, Type.CHURCH, null, null,
            false, false, null, TroopMakerBuilding.class),
    FLETCHER("fletcher", Category.WEAPON, new Pair<>(Item.WOOD, -20), 100, 150,
            1, 0, 0, 0, 0, 0, 0,
            new Vector<>(List.of(new Pair<>(Item.WOOD, -2), new Pair<>(Item.WOOD, -3))),
            new Vector<>(List.of(new Pair<>(Item.BOW, 1), new Pair<>(Item.CROSSBOW, 1))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    BLACKSMITH("blacksmith", Category.WEAPON, new Pair<>(Item.WOOD, -20), 200, 150,
            1, 0, 0, 0, 0, 0, 0,
            new Vector<>(List.of(new Pair<>(Item.IRON, -1), new Pair<>(Item.IRON, -1))),
            new Vector<>(List.of(new Pair<>(Item.MACE, 1), new Pair<>(Item.SWORD, 1))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    ARMOURER("armourer", Category.WEAPON, new Pair<>(Item.WOOD, -20), 100, 150,
            1, 0, 0, 0, 0, 0, 0,
            new Vector<>(List.of(new Pair<>(Item.IRON, -1))), new Vector<>(List.of(new Pair<>(Item.METAL_ARMOR, 1))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    POLETURNER("poleturner", Category.WEAPON, new Pair<>(Item.WOOD, -10), 100, 150,
            1, 0, 0, 0, 0, 0, 0,
            new Vector<>(List.of(new Pair<>(Item.WOOD, -1), new Pair<>(Item.WOOD, -2))),
            new Vector<>(List.of(new Pair<>(Item.SPEAR, 1), new Pair<>(Item.PIKE, 1))),
            0, null, null, null,
            false, false, null, ProducerBuilding.class),
    LOW_WALL("low wall", Category.CASTLE, new Pair<>(Item.STONE, -1), 0, 200,
            0, 0, 1, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, DefenceBuilding.class),
    HIGH_WALL("high wall", Category.CASTLE, new Pair<>(Item.STONE, -2), 0, 300,
            0, 0, 2, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, DefenceBuilding.class),
    STAIR("stair", Category.CASTLE, new Pair<>(Item.STONE, -1), 0, 150,
            0, 0, 1, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            true, true, null, GateAndStairs.class),
    TOWN_HALL("town hall", Category.CASTLE, null, 0, 2000,
            0, 8, 0, 0, 0, 0, 0,
            null, null, 0, null, null, null,
            false, false, null, Building.class);

    //TODO add good and bad things, etc
    private final String name;
    private final Category category;
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
    private final Type troopsItCanMake;
    private final Item.Category itemsItCanHold;
    private final Vector<Item> itemsItCanMove;
    private final boolean canBeDestroyedByTunnels;
    private final boolean canBeRepaired;
    private final Vector<Texture> canOnlyBuiltOn;
    private final Class buildingClass;

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
        TRAP,
        FARM,
        FOOD_PROCESSING,
        INDUSTRY,
        TOWN,
        WEAPON
    }
}
