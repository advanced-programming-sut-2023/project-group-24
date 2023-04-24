package model;

import model.army.Army;
import model.army.ArmyType;
import model.buildings.Building;
import model.buildings.StorageBuilding;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Kingdom {
    private final KingdomColor color;
    private User owner;
    private Building gateHouse;
    private Army lord;
    private ArrayList<Building> buildings;
    private ArrayList<Army> armies;
    private HashMap<Item, Integer> storage;
    private ArrayList<Trade> trades;
    private ArrayList<People> population;//TODO
    private int popularity;
    private HashMap<Factor, Integer> popularityFactors;
    private int gold;

    public Kingdom(KingdomColor color, Building gateHouse) {
        this.color = color;
        this.gateHouse = gateHouse;
        this.lord = new Army(gateHouse.getLocation(), ArmyType.LORD, this);
        setKingdomAttribute();
    }

    private void setKingdomAttribute() {//TODO
        popularity = 75;
        buildings = new ArrayList<>();
        buildings.add(gateHouse);
        trades = new ArrayList<>();
        armies = new ArrayList<>();
        armies.add(lord);
        popularityFactors.put(Factor.FEAR , 0);
        popularityFactors.put(Factor.FOOD , 0);
        popularityFactors.put(Factor.RELIGION , 0);
        popularityFactors.put(Factor.TAX , 0);
        setStorage();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Building getGateHouse() {
        return gateHouse;
    }

    public int getPopularity() {
        return popularity;
    }

    public void changePopularity(int popularity) {
        this.popularity = popularity;
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

    public Army getLord() {
        return lord;
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
                    changeStockNumber(new Pair<> (value , ((StorageBuilding) building).getStockedNumber(value)));
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
