package model.databases;

import model.Kingdom;
import model.Trade;
import model.army.Army;
import model.buildings.Building;
import model.map.Map;

import java.util.ArrayList;

public class GameDatabase {
    private final ArrayList<Army> selectedUnits;
    private final Map map;
    private Kingdom currentKingdom;
    private final ArrayList<Kingdom> kingdoms;
    private Building currentBuilding;
    private int turnPlayed;
    private ArrayList<Trade> trades; //todo

    public GameDatabase(ArrayList<Kingdom> kingdoms, Map map) {
        selectedUnits = new ArrayList<>();
        this.map = map;
        turnPlayed = 0;
        this.kingdoms = new ArrayList<>(kingdoms);
        currentKingdom = kingdoms.get(0);
    }

    public void nextTurn() {
    currentKingdom = kingdoms.get(++turnPlayed % kingdoms.size());
    }

    public void removeKingdom() {
        //TODO ...
    }

    public int turnPlayed() {
        //Todo ...
        return 0;
    }

    public void setCurrentPlayer(Kingdom currentKingdom) {
        this.currentKingdom = currentKingdom;
    }

    public Kingdom getCurrentKingdom() {
        return currentKingdom;
    }

    public ArrayList<Army> getSelectedUnits() {
        return selectedUnits;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public void setSelectedUnits(ArrayList<Army> selectedUnits) {
        this.selectedUnits.clear();
        this.selectedUnits.addAll(selectedUnits);
    }

    public Map getMap() {
        return map;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public void removeTrade(Trade trade) {
        this.trades.remove(trade);
    }

    public void removeTrade(int index) {
        this.trades.remove(index);
    }
}
