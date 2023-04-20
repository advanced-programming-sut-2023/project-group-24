package model;

import model.army.Army;
import model.buildings.Building;
import model.map.Map;

import java.util.ArrayList;

public class GameDatabase {
    private Kingdom currentKingdom;
    private ArrayList<Kingdom> kingdoms;
    private Building currentBuilding;
    private final ArrayList<Army> selectedUnits;
    private final Map map;

    public GameDatabase(ArrayList<User> players, Map map) {
        selectedUnits = new ArrayList<Army>();
        this.map = map;
        //TODO add gor kingdoms current player
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
        selectedUnits.clear();
        selectedUnits.addAll(selectedUnits);
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
}
