package model;

import model.army.Army;

import java.util.ArrayList;
import java.util.HashMap;

public class Kingdom {
    private User owner;
    private ArrayList<Building> buildings;
    private ArrayList<Army> armies;
    private ArrayList<Trade> trades;
    private Color color;
    private int popularity;
    private int foodFactor;
    private int fearFactor;
    private int religionFactor;
    private int taxFactor;
    private int peopleCapacity;
    private int workers;
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

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    public void addPeopleCapacity(int amount) {
        this.peopleCapacity += amount;
    }

    public int getWorkers() {
        return workers;
    }

    public void changeWorkersNumbers(int amount) {
        this.workers += amount;
    }

    public int getGold() {
        return gold;
    }

    public void changeGold(int amount) {
        this.gold += amount;
    }
    public int getUnEmployed() {
        return peopleCapacity - workers;
    }

}
