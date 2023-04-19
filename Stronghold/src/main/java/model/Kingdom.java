package model;

import model.army.Army;
import model.map.Cell;

import java.util.ArrayList;
import java.util.HashMap;

public class Kingdom {
    private User owner;
    private Cell gateHouseLocation;
    private ArrayList<Building> buildings;
    private ArrayList<Army> armies;
    private ArrayList<Trade> trades;
    private ArrayList<People> population;
    private Color color;
    private int popularity;
    private int foodFactor;
    private int fearFactor;
    private int religionFactor;
    private int taxFactor;
    private int gold;

    public Kingdom(Color color) {
        this.color = color;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
    public Cell getGateHouseLocation() {
        return gateHouseLocation;
    }

    public void setGateHouseLocation(Cell gateHouseLocation) {
        this.gateHouseLocation = gateHouseLocation;
    }

    public int getPopularity() {
        return popularity;
    }

    public void changePopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getFoodFactor() {
        return foodFactor;
    }

    public void setFoodFactor(int foodFactor) {
        this.foodFactor = foodFactor;
    }

    public int getFearFactor() {
        return fearFactor;
    }

    public void setFearFactor(int fearFactor) {
        this.fearFactor = fearFactor;
    }

    public int getReligionFactor() {
        return religionFactor;
    }

    public void setReligionFactor(int religionFactor) {
        this.religionFactor = religionFactor;
    }

    public int getTaxFactor() {
        return taxFactor;
    }

    public void setTaxFactor(int taxFactor) {
        this.taxFactor = taxFactor;
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

    public void changeGold(int amount) {
        this.gold += amount;
    }

    public void addArmy(Army army) {
        armies.add(army);
    }

    public void removeArmy(Army army) {
        for (int i = 0; i < armies.size(); i++)
            if (armies.get(i).equals(army))
                armies.remove(i);
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }


}
