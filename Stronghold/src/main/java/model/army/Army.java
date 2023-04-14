package model.army;

import model.Kingdom;
import model.map.Cell;

import java.util.ArrayList;

public abstract class Army {
    private Cell location;
    private UnitState unitState;
    private ArrayList<Cell> path;
    private int hp;
    private Kingdom owner;

    public Army(Cell location, ArmyType armyType) {
        this.location = location;
        this.hp = armyType.getMaxHp();
        path = new ArrayList<>();
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Cell getLocation() {
        return location;
    }

    public void move(Cell location) {
        this.location = location;
    }

    public UnitState getState() {
        return unitState;
    }

    public void changeState(UnitState unitState) {
        this.unitState = unitState;
    }

    public int getHp() {
        return hp;
    }

    public boolean takeDamage(int amount){
        hp -= amount;
        return isDead();
    }

    public ArrayList<Cell> getPath() {
        return path;
    }

    public void setPath(ArrayList<Cell> path) {
        this.path.clear();
        this.path.addAll(path);
    }
}
