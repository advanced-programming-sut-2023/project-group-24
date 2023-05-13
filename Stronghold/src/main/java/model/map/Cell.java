package model.map;

import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefenceBuilding;
import model.buildings.GateAndStairs;
import model.enums.Direction;
import model.enums.MovingType;

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
        if (existingBuilding != null)
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

    public boolean canMove(Direction direction, Cell startPoint, MovingType movingType) {
        int lastHeight = 0, nextHeight = 0;
        if (startPoint.existingBuilding != null) lastHeight = startPoint.existingBuilding.getBuildingType().getHeight();
        if (existingBuilding != null) nextHeight = existingBuilding.getBuildingType().getHeight();
        if (!(tree == null && !isRock && texture.isCanPass()) || nextHeight == -1) return false;
        if (nextHeight == lastHeight || movingType.equals(MovingType.ASSASSIN)) return true;
        if ((existingBuilding != null && existingBuilding.getBuildingType().equals(BuildingType.STAIR)) || (startPoint.
                existingBuilding != null && startPoint.existingBuilding.getBuildingType().equals(BuildingType.STAIR)) ||
                checkGate() || checkGate(startPoint)) return true;
        if (movingType.equals(MovingType.CAN_NOT_CLIMB_LADDER))
            return false;
        return handleClimber(startPoint, direction);
    }

    private boolean checkGate() {
        if (existingBuilding == null)
            return false;
        BuildingType type = existingBuilding.getBuildingType();
        if (type.equals(BuildingType.SMALL_STONE_GATEHOUSE) || type.equals(BuildingType.LARGE_STONE_GATEHOUSE))
            return !((GateAndStairs) existingBuilding).isClosed();
        return false;
    }

    private boolean checkGate(Cell cell) {
        if (cell.existingBuilding == null)
            return false;
        BuildingType type = cell.existingBuilding.getBuildingType();
        return type.equals(BuildingType.SMALL_STONE_GATEHOUSE) || type.equals(BuildingType.LARGE_STONE_GATEHOUSE);
    }

    private boolean handleClimber(Cell startPoint, Direction direction) {
        return checkLadder(startPoint, direction) || checkLadder(direction);
    }

    private boolean checkLadder(Cell cell, Direction direction) {
        if (cell.existingBuilding == null)
            return false;
        if (cell.existingBuilding instanceof DefenceBuilding)
            return ((DefenceBuilding) cell.existingBuilding).hasLadderState(backWardDirection(direction));
        return false;
    }

    private boolean checkLadder(Direction direction) {
        if (existingBuilding == null)
            return false;
        if (existingBuilding instanceof DefenceBuilding)
            return ((DefenceBuilding) existingBuilding).hasLadderState(direction);
        return false;
    }

    private Direction backWardDirection(Direction direction) {
        switch (direction) {
            case DOWN : return Direction.UP;
            case RIGHT: return Direction.LEFT;
            case LEFT: return Direction.RIGHT;
            default: return Direction.DOWN;
        }
    }

}