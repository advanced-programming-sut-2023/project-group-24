package model.databases;

import model.Kingdom;
import model.Trade;
import model.User;
import model.army.Army;
import model.buildings.Building;
import model.map.Map;

import java.util.ArrayList;

public class GameDatabase {
    private final ArrayList<Army> selectedUnits;
    private final Map map;
    private Kingdom currentKingdom;
    private ArrayList<Kingdom> kingdoms;
    private Building currentBuilding;

    public GameDatabase(ArrayList<User> players, Map map) {
        selectedUnits = new ArrayList<>();
        this.map = map;
        //TODO add kingdoms of current player
    }

    public void setCurrentPlayer(Kingdom currentKingdom) {
        this.currentKingdom = currentKingdom;
    }

    public Kingdom getCurrentKingdom() {
        return currentKingdom;
    }

    public ArrayList getSelectedUnits() {
        return selectedUnits;
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

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }
}
