package model;

import model.army.Army;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.StorageBuilding;
import model.enums.Item;
import model.enums.KingdomColor;
import model.enums.PopularityFactor;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Kingdom {
    private final KingdomColor color;
    private User owner;
    private ArrayList<Building> buildings;
    private ArrayList<Army> armies;
    private HashMap<Item, Integer> storage;
    private ArrayList<Trade> trades;
    private ArrayList<Trade> notifications;
    private ArrayList<People> population;
    private int populationCapacity;
    private int popularity;
    private HashMap<PopularityFactor, Integer> popularityFactors;
    private int gold;
    private int foodRate;
    private int wantedFoodRate;
    private int taxRate;
    private int wantedTaxRate;
    private double fearRate;// it has effect on workers and unit!

    public Kingdom(KingdomColor color) {
        this.color = color;
        setKingdomAttribute();
    }

    private void setKingdomAttribute() {
        popularity = 75;
        gold = 1000;
        foodRate = 0;
        taxRate = 0;
        fearRate = 0;
        populationCapacity = 8;
        buildings = new ArrayList<>();
        trades = new ArrayList<>();
        armies = new ArrayList<>();
        popularityFactors = new HashMap<>();
        population = new ArrayList<>();
        popularityFactors.put(PopularityFactor.FEAR, 0);
        popularityFactors.put(PopularityFactor.FOOD, 0);
        popularityFactors.put(PopularityFactor.RELIGION, 0);
        popularityFactors.put(PopularityFactor.TAX, 0);
        popularityFactors.put(PopularityFactor.INN, 0);
        setStorage();
    }

    public ArrayList<Army> getArmies() {
        return armies;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void changePopularity(int amount) {
        this.popularity += amount;
        if (popularity < 0) popularity = 0;
        if (popularity > 100) popularity = 100;
    }

    public int getPopulation() {
        return population.size();
    }

    public void addPeople(People people) {
        population.add(people);
    }

    public int getUnemployment() {
        int unemployment = 0;
        for (People people : population)
            if (!people.isWorking()) unemployment++;
        return unemployment;
    }

    public int getUnEmployedPeople() {
        int unEmployedPeople = 0;
        for (People people : population)
            if (!people.isWorking())
                unEmployedPeople++;
        return unEmployedPeople;
    }

    public void assignToArmy(int number) {
        for (int i = 0; i < number; i++) {
            for (People people : population) {
                if (!people.isWorking()) {
                    population.remove(people);
                    break;
                }
            }
        }
    }

    public int getGold() {
        return gold;
    }

    public int getPopularityFactor(PopularityFactor factor) {
        return popularityFactors.get(factor);
    }

    public void setPopularityFactor(PopularityFactor factor, int amount) {
        popularityFactors.replace(factor, amount);
    }

    public void changeGold(int amount) {
        this.gold += amount;
    }

    public void addArmy(Army army) {
        armies.add(army);
    }

    public void removeArmy(Army army) {
        armies.remove(army);
    }

    public void removeArmies(ArrayList<Army> deadArmies) {
        armies.removeAll(deadArmies);
    }

    public KingdomColor getColor() {
        return color;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public int getStockedNumber(Item item) {
        return storage.get(item);
    }

    public void changeStockNumber(Pair<Item, Integer> item) {
        storage.replace(item.getObject1(), getStockedNumber(item.getObject1()) + item.getObject2());
    }

    public void setStorage() {
        storage = new HashMap<>();
        for (Item value : Item.values())
            storage.put(value, 0);
        for (Building building : buildings)
            if (building instanceof StorageBuilding) for (Item value : Item.values())
                changeStockNumber(new Pair<>(value, ((StorageBuilding) building).getStockedNumber(value)));
    }

    public void removeBuilding(Building destroyedBuilding) {
        buildings.remove(destroyedBuilding);
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getWantedFoodRate() {
        return wantedFoodRate;
    }

    public void setWantedFoodRate(int wantedFoodRate) {
        this.wantedFoodRate = wantedFoodRate;
    }

    public int getWantedTaxRate() {
        return wantedTaxRate;
    }

    public void setWantedTaxRate(int wantedTaxRate) {
        this.wantedTaxRate = wantedTaxRate;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Trade> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Trade> notifications) {
        this.notifications = notifications;
    }

    public void setPopulationCapacity() {
        populationCapacity = BuildingType.TOWN_HALL.getHomeCapacity();
        for (Building building : buildings)
            if (building.getBuildingType().equals(BuildingType.HOVEL))
                populationCapacity += BuildingType.HOVEL.getHomeCapacity();
    }

    public int getFoodNumber() {
        int foodNumber = 0;
        for (Item item : storage.keySet())
            if (item.getCategory().equals(Item.Category.FOOD))
                foodNumber++;
        return foodNumber;
    }

    public int getChurchNumber() {
        int churchAmount = 0;
        for (Building building : buildings)
            if (building.getBuildingType().equals(BuildingType.CHURCH) ||
                    building.getBuildingType().equals(BuildingType.CATHEDRAL))
                churchAmount++;
        return churchAmount;
    }

    public double getFearRate() {
        return fearRate;
    }

    public void setFearRate(double fearRate) {
        this.fearRate = fearRate;
    }
}
