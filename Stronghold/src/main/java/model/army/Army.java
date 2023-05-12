package model.army;

import model.Kingdom;
import model.map.Cell;
import utils.Pair;

import java.util.ArrayList;

public abstract class Army {
    private final ArrayList<Cell> path;
    private final Kingdom owner;
    private ArmyType armyType;
    private Pair<Cell, Cell> patrol;
    private Army target;
    private Cell targetCell;
    private Cell location;
    private UnitState unitState;
    private int hp;

    public Army(Cell location, ArmyType armyType, Kingdom owner) {
        this.armyType = armyType;
        this.location = location;
        this.hp = armyType.getMaxHp();
        path = new ArrayList<>();
        patrol = null;
        targetCell = null;
        this.owner = owner;
        target = null;
        unitState = UnitState.STANDING;
        location.addArmy(this);
        owner.addArmy(this);
    }

    public void isDead() {
        this.getLocation().removeArmy(this);
        this.getOwner().removeArmy(this);
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

    public void takeDamage(int amount) {
        hp -= amount;
        if (hp <= 0)
            isDead();
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

    public Cell getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public void changeEngineerState() {
        if (this.getArmyType().equals(ArmyType.ENGINEER))
            this.setArmyType(ArmyType.ENGINEER_WITH_OIL);
        if (this.getArmyType().equals(ArmyType.ENGINEER_WITH_OIL))
            this.setArmyType(ArmyType.ENGINEER);
    }

    public void setArmyType(ArmyType armyType) {
        this.armyType = armyType;
    }
}
