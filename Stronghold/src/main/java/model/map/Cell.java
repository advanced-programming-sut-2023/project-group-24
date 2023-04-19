package model.map;

import model.army.Army;
import model.army.ArmyType;

import java.util.ArrayList;

public class Cell {
    private int x;
    private int y;
    private Texture texture;
    private Building existingBuilding = null;
    private ArrayList<Army> armies = new ArrayList<>();
    private boolean isRock;
    private Direction direction;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = Texture.Ground;
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

    public ArrayList<Army> selectUnits() {
        //TODO ...
        return null;
    }
    public ArrayList<Army> selectUnits(ArmyType armyType) {
        //TODO ...
        return null;
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
