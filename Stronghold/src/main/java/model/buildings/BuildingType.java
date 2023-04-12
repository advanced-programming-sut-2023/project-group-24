package model.buildings;

import utils.NumberOfThings;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE("small stone gatehouse", "Castle", null, 0, 100, 0, 8, 0, 0, 0, 0, 0, null, null, 0, false);

    private String name;
    private String category;
    private NumberOfThings materialToBuild;
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
    private NumberOfThings uses;
    private NumberOfThings produces;
    private int producePrice;
    private boolean canHaveBlazer;

    BuildingType(String name,
                 String category,
                 NumberOfThings materialToBuild,
                 int price,
                 int maxHp,
                 int workersNeeded,
                 int homeCapacity,
                 int height,
                 int storageCapacity,
                 int attackPoint,
                 int popularityRate,
                 int produceRate,
                 NumberOfThings uses,
                 NumberOfThings produces,
                 int producePrice,
                 boolean canHaveBlazer) {
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
