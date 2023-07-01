package model.databases;

import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.map.Map;
import model.map.Texture;

import java.util.ArrayList;

public class GameDatabase {
    private final ArrayList<Army> selectedUnits;
    private final Map map;
    private final ArrayList<Kingdom> kingdoms;
    private Kingdom currentKingdom;
    private Building currentBuilding;
    private BuildingType selectedBuilding;
    private Texture texture;
    private ArmyType selectedArmy;
    private boolean undo;
    private boolean delete;
    private int turnPlayed;
    private int roundPlayed;

    public GameDatabase(ArrayList<Kingdom> kingdoms, Map map) {
        selectedUnits = new ArrayList<>();
        undo = false;
        delete = false;
        this.map = map;
        turnPlayed = 0;
        roundPlayed = 0;
        this.kingdoms = new ArrayList<>(kingdoms);
        currentKingdom = kingdoms.get(0);
    }

    public boolean isUndo() {
        return undo;
    }

    public void setUndo(boolean undo) {
        this.undo = undo;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public ArmyType getSelectedArmy() {
        return selectedArmy;
    }

    public void setSelectedArmy(ArmyType selectedArmy) {
        this.selectedArmy = selectedArmy;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public BuildingType getSelectedBuilding() {
        return selectedBuilding;
    }

    public void setSelectedBuilding(BuildingType buildingType) {
        this.selectedBuilding = buildingType;
    }

    public int getTurnPlayed() {
        return turnPlayed;
    }

    public int getRoundPlayed() {
        return roundPlayed;
    }

    public void nextTurn() {
        if (kingdoms.get(kingdoms.size() - 1).equals(currentKingdom)) roundPlayed++;
        currentKingdom = kingdoms.get((kingdoms.indexOf(currentKingdom) + 1) % kingdoms.size());
        turnPlayed++;
    }

    public void removeKingdom(Kingdom kingdom) {
        kingdoms.remove(kingdom);
    }

    public Kingdom getCurrentKingdom() {
        return currentKingdom;
    }

    public ArrayList<Army> getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(ArrayList<Army> selectedUnits) {
        this.selectedUnits.clear();
        this.selectedUnits.addAll(selectedUnits);
    }

    public void addSelectedUnits(Army army) {
        if (!selectedUnits.contains(army))
            this.selectedUnits.add(army);
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
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
