package model;

import model.army.Army;
import model.buildings.Building;
import model.buildings.StorageBuilding;
import model.enums.Item;
import model.enums.KingdomColor;
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
    private ArrayList<People> population;//TODO
    private int popularity;
    private HashMap<Factor, Integer> popularityFactors;
    private int gold;

    public Kingdom(KingdomColor color) {
        this.color = color;
        setKingdomAttribute();
    }

    private void setKingdomAttribute() {
        popularity = 75;
        gold = 1000;
        buildings = new ArrayList<>();
        trades = new ArrayList<>();
        armies = new ArrayList<>();
        popularityFactors = new HashMap<>();
        population = new ArrayList<>();
        popularityFactors.put(Factor.FEAR, 0);
        popularityFactors.put(Factor.FOOD, 0);
        popularityFactors.put(Factor.RELIGION, 0);
        popularityFactors.put(Factor.TAX, 0);
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

    public void changePopularity(int amount) {
        this.popularity += amount;
        if (popularity < 0)
            popularity = 0;
        if (popularity > 100)
            popularity = 100;
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
            if (!people.isWorking())
                unemployment++;
        return unemployment;
    }

    public int getGold() {
        return gold;
    }

    public HashMap<Factor, Integer> getPopularityFactors() {
        return popularityFactors;
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

    public ArrayList<Trade> getNotifications() {
        return notifications;
    }

    public void changeStockNumber(Pair<Item, Integer> item) {
        storage.replace(item.getObject1(), getStockedNumber(item.getObject1()) + item.getObject2());
    }

    public void setStorage() {
        storage = new HashMap<>();
        for (Item value : Item.values())
            storage.put(value, 0);
        for (Building building : buildings)
            if (building instanceof StorageBuilding)
                for (Item value : Item.values())
                    changeStockNumber(new Pair<>(value, ((StorageBuilding) building).getStockedNumber(value)));
    }

    public void removeBuilding(Building destroyedBuilding) {
        buildings.remove(destroyedBuilding);
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    private enum Factor {
        FOOD,
        FEAR,
        RELIGION,
        TAX
    }
}
