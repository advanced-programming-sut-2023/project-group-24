package model.army;

import model.Kingdom;
import model.map.Cell;

import java.util.ArrayList;

public class Army {
    private final ArrayList<Cell> path;
    //Todo patrol
    private final Kingdom owner;
    private final ArmyType armyType;
    private Cell location;
    private UnitState unitState;
    private int hp;

    public Army(Cell location, ArmyType armyType, Kingdom owner) {
        this.armyType = armyType;
        this.location = location;
        this.hp = armyType.getMaxHp();
        path = new ArrayList<>();
        this.owner = owner;
        location.addArmy(this);
        owner.addArmy(this);
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Cell getLocation() {
        return location;
    }

    public UnitState getUnitState() {
        return unitState;
    }

    public void changeState(UnitState unitState) {
        this.unitState = unitState;
    }

    public int getHp() {
        return hp;
    }

    public boolean takeDamage(int amount) {
        hp -= amount;
        return isDead();
    }

    public void moveArmy() {
        location = path.get(0);
        path.remove(0);
    }

    public ArrayList<Cell> getPath() {
        return path;
    }

    public void setPath(ArrayList<Cell> path) {
        this.path.clear();
        this.path.addAll(path);
    }

    public Kingdom getOwner() {
        return owner;
    }

    public ArmyType getArmyType() {
        return armyType;
    }
}
