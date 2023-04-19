package model;

import controller.gamecontrollers.KingdomController;
import model.buildings.Building;

import java.util.ArrayList;

public class GameDatabase {
    private Kingdom currentKingdom;
    private ArrayList<Kingdom> kingdoms;
    private Building currentBuilding;
    private ArrayList<Army> selectedUnits;
    private Map map;

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

    public void setSelectedUnits(ArrayList<Army> selectedUnits) {
        selectedUnits.clear();
        selectedUnits.addAll(selectedUnits);
    }

    public ArrayList getSelectedUnits() {
        return selectedUnits;
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
