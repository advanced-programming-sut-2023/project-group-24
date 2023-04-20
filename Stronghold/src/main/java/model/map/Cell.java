package model.map;

import model.Direction;
import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.buildings.Building;

import java.util.ArrayList;

public class Cell {
    private final int x;
    private final int y;
    private Texture texture;
    private Building existingBuilding = null;
    private final ArrayList<Army> armies = new ArrayList<>();
    private boolean isRock;
    private Direction direction;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
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
        for (int i = 0; i < armies.size(); i++)
            if (armies.get(i).equals(army))
                armies.remove(i);
    }


}
