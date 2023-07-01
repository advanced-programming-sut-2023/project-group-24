package model.army;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.buildings.Building;
import model.map.Cell;

import java.util.ArrayList;

public abstract class Army {
    private final ArrayList<Cell> path;
    private transient final Kingdom owner;
    private ArmyType armyType;
    private Pair<Cell, Cell> patrol;
    private Army target;
    private Cell targetCell;
    private Building targetBuilding;
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
        location.addArmy(this);
        owner.addArmy(this);
        unitState = UnitState.STANDING;
    }

    public void isDead() {
        this.getLocation().removeArmy(this);
        this.getOwner().removeArmy(this);
        if (armyType == ArmyType.KNIGHT) owner.killHorse();
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
        if (path.size() != 0) {
            location.removeArmy(this);
            location = path.get(0);
            path.remove(0);
            location.addArmy(this);
        }
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

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
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
        if (this.armyType == ArmyType.ENGINEER)
            this.armyType = ArmyType.ENGINEER_WITH_OIL;
        else if (this.armyType == ArmyType.ENGINEER_WITH_OIL)
            this.armyType = ArmyType.ENGINEER;
    }

    public boolean canAttack() {
        if (target == null)
            return false;
        if (armyType.getRange() > 0) {
            int fireRange = 0;
            if (location.getExistingBuilding() != null)
                fireRange = location.getExistingBuilding().getBuildingType().getAttackPoint();
            if (target.location.getExistingBuilding() != null)
                fireRange -= target.location.getExistingBuilding().getBuildingType().getAttackPoint();
            return getDistance() <= armyType.getRange() + fireRange;
        }
        return getDistance() <= armyType.getRange();
    }

    public int getDistance() {
        int x = location.getX();
        int y = location.getY();
        int x1 = target.location.getX();
        int y1 = target.location.getY();
        return (int) (Math.sqrt(Math.pow((x - x1), 2) + Math.pow((y - y1), 2)));
    }

    public boolean canBurn() {
        return this.armyType.canBurn();
    }
}
