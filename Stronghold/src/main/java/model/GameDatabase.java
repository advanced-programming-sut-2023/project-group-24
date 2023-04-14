package model;

import java.util.ArrayList;

public class GameDatabase {
    private User currentPlayer;
    private ArrayList<User> players;
    private ArrayList<Army> selectedUnits;
    private Map map;

    public GameDatabase(ArrayList<User> players, Map map) {
        this.players = new ArrayList<User>(players);
        selectedUnits = new ArrayList<Army>();
        currentPlayer = players.get(0);
        this.map = map;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
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
}
