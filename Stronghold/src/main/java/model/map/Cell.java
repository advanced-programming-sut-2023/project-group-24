package model.map;

import model.enums.Direction;
import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.buildings.Building;

import java.util.ArrayList;

public class Cell {
    private final int x;
    private final int y;
    private final ArrayList<Army> armies;
    private Texture texture;
    private Building existingBuilding;
    private boolean isRock;
    private Tree tree;
    private Direction direction;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
        existingBuilding = null;
        armies = new ArrayList<>();
    }

    public Texture getTexture() {
        return texture;
    }

    public void changeTexture(Texture texture) {
        this.texture = texture;
    }

    public Building getExistingBuilding() {
        return existingBuilding;
    }

    public void setExistingBuilding(Building existingBuilding) {
        this.existingBuilding = existingBuilding;
    }

    public boolean isRock() {
        return isRock;
    }

    public void setRock(boolean rock) {
        isRock = rock;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Army> selectUnits(Kingdom owner) {
        ArrayList<Army> selectedUnit = new ArrayList<>();
        for (Army army : armies)
            if (army.getOwner().equals(owner))
                selectedUnit.add(army);
        return selectedUnit;
    }

    public ArrayList<Army> selectUnits(ArmyType armyType, Kingdom owner) {
        ArrayList<Army> selectedUnit = new ArrayList<>();
        for (Army army : armies)
            if (army.getOwner().equals(owner) && army.getArmyType().equals(armyType))
                selectedUnit.add(army);
        return selectedUnit;
    }

    public void addArmy(Army army) {
        armies.add(army);
    }

    public void removeArmy(Army army) {
        armies.remove(army);
    }

    public void clear(Map map) {
        clearArmies(map);
        existingBuilding.getKingdom().removeBuilding(existingBuilding);
        existingBuilding = null;
        texture = Texture.GROUND;
        isRock = false;
    }

    public void clearArmies(Map map) {
        for (Kingdom kingdom : map.getKingdoms())
            kingdom.removeArmies(armies);
        armies.clear();
    }

    public ArrayList<Army> getArmies() {
        return armies;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public boolean canBuild() {
        return tree == null && !isRock && existingBuilding == null && texture.isCanBuild();
    }

    public boolean canDropUnit() {
        return tree == null && !isRock && texture.isCanPass();
    }

    public boolean canMove(Direction direction, Cell cell) {
        if (!(tree == null && !isRock && texture.isCanPass()))
            return false;
        if (wallHandle())
            return false;
        return true;
    }

    private boolean wallHandle() {
    }
}
