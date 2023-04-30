package model.army;

import model.Kingdom;
import model.map.Cell;
import utils.Pair;

import java.util.ArrayList;

public abstract class Army {
    private final ArrayList<Cell> path;
    private Pair<Cell, Cell> patrol;
    private Army target;
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
        patrol = null;
        this.owner = owner;
        target = null;
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

    public Army getTarget() {
        return target;
    }

    public void setTarget(Army target) {
        this.target = target;
    }

    public Pair<Cell, Cell> getPatrol() {
        return patrol;
    }

    public void setPatrol(Pair<Cell, Cell> patrol) {
        this.patrol = patrol;
    }
}
