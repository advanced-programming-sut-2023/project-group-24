package model;

import java.util.ArrayList;

public class GameDatabase {
    private Kingdom currentKingdom;
    private ArrayList<Kingdom> kingdoms;
    private ArrayList<Army> selectedUnits;
    private Map map;

    public GameDatabase(ArrayList<User> players, Map map) {
        selectedUnits = new ArrayList<Army>();
        this.map = map;
        //TODO get kingdoms from map and set owner for them
        currentKingdom = kingdoms.get(0);
    }

    public void setCurrentKingdom(Kingdom currentKingdom) {
        this.currentKingdom = currentKingdom;
    }

    public Kingdom getCurrentKingdom() {
        return currentKingdom;
    }

    public void setSelectedUnits(ArrayList<Army> selectedUnits) {
        selectedUnits.clear();
        selectedUnits.addAll(selectedUnits);
    }

    public ArrayList<Army> getSelectedUnits() {
        return selectedUnits;
    }

    public Map getMap() {
        return map;
    }

    public void removeKingdom(Kingdom kingdom) {
        kingdoms.remove(kingdom);
    }
}
