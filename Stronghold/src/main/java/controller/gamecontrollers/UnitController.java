package controller.gamecontrollers;

import controller.functionalcontrollers.PathFinder;
import model.People;
import model.army.*;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.databases.GameDatabase;
import model.enums.Direction;
import model.map.Cell;
import utils.Pair;
import view.enums.messages.UnitControllerMessages;

import java.util.ArrayList;

public class UnitController {
    private final GameDatabase gameDatabase;

    public UnitController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public UnitControllerMessages selectUnit(int x, int y, String unitType) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        ArmyType armyType = null;
        ArrayList<Army> armies;
        if (unitType != null) {
            armyType = ArmyType.stringToEnum(unitType);
            if (armyType == null) return UnitControllerMessages.INVALID_TYPE;
        }
        Cell cell = gameDatabase.getMap().getMap()[x][y];
        if (armyType == null)
            armies = cell.selectUnits(gameDatabase.getCurrentKingdom());
        else
            armies = cell.selectUnits(armyType, gameDatabase.getCurrentKingdom());
        gameDatabase.setSelectedUnits(armies);
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages moveUnit(int x, int y) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        ArrayList<Army> selectedUnit = gameDatabase.getSelectedUnits();
        if (selectedUnit == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        Cell startingCell = gameDatabase.getSelectedUnits().get(0).getLocation();
        Pair<Integer, Integer> startLocation = new Pair<>(startingCell.getX(), startingCell.getY());
        Pair<Integer, Integer> destination = new Pair<>(x, y);
        boolean isAssassin = isAssassin(selectedUnit);
        PathFinder pathFinder = new PathFinder(gameDatabase.getMap(), startLocation, isAssassin);
        PathFinder.OutputState outputState = pathFinder.search(destination);
        switch (outputState) {
            case BLOCKED:
                return UnitControllerMessages.BLOCK;
            case ALREADY_AT_DESTINATION:
                return UnitControllerMessages.ALREADY_IN_DESTINATION;
        }
        ArrayList<Cell> path = pathFinder.findPath();
        for (Army e : gameDatabase.getSelectedUnits())
            e.setPath(path);
        return UnitControllerMessages.SUCCESS;
    }

    private boolean isAssassin(ArrayList<Army> selectedUnit) {
        for (Army e : selectedUnit) {
            if (!e.getArmyType().equals(ArmyType.ASSASSIN))
                return false;
        }
        return true;
    }

    public UnitControllerMessages patrolUnit(int x, int y) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        if (gameDatabase.getSelectedUnits() == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        UnitControllerMessages moveMessage = moveUnit(x, y);
        if (!moveMessage.equals(UnitControllerMessages.SUCCESS)) return moveMessage;
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        Cell startLocation = selectedUnits.get(0).getLocation();
        Cell destination = gameDatabase.getMap().getMap()[x][y];
        Pair<Cell, Cell> patrolWay = new Pair<>(startLocation, destination);
        for (Army e : gameDatabase.getSelectedUnits())
            e.setPatrol(patrolWay);
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages stopUnitsPatrol() {
        if (gameDatabase.getSelectedUnits() == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        boolean isPatrolling = false;
        for (Army e : selectedUnits) {
            if (e.getPatrol() != null) {
                isPatrolling = true;
                e.setPatrol(null);
            }
        }
        if (!isPatrolling) return UnitControllerMessages.NOT_PATROL;
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages setStateForUnits(String unitState) {
        if (gameDatabase.getSelectedUnits() == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        UnitState state = UnitState.stringToEnum(unitState);
        if (state == null) return UnitControllerMessages.INVALID_STATE;
        for (Army e : gameDatabase.getSelectedUnits()) {
            e.changeState(state);
        }
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages attackEnemy(int enemyX, int enemyY) {
        if (checkXY(enemyX, enemyY)) return UnitControllerMessages.INVALID_LOCATION;
        Cell enemyCell = gameDatabase.getMap().getMap()[enemyX][enemyY];
        ArrayList<Army> enemies = enemyCell.getArmies();
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (enemies == null) return UnitControllerMessages.NOT_ENEMY;
        boolean isEnemyExist = false;
        boolean outOfRange = true;
        int distance = getDistance(enemyX, enemyY);
        for (Army e : enemies) {
            if (!e.getOwner().equals(gameDatabase.getCurrentKingdom())) {
                int range = e.getLocation().getExistingBuilding().getBuildingType().getAttackPoint() -
                        enemyCell.getExistingBuilding().getBuildingType().getAttackPoint();
                if (e.getArmyType().getRange() > 0 && e.getArmyType().getRange() + range < distance)
                    outOfRange = false;
                isEnemyExist = true;
                UnitControllerMessages moveMessage = moveUnit(enemyX, enemyY);
                if (!moveMessage.equals(UnitControllerMessages.SUCCESS)) return moveMessage;
                for (Army f : selectedUnits)
                    f.setTarget(e);
                break;
            }
        }
        if (!outOfRange) return UnitControllerMessages.OUT_OF_RANGE;
        else if (!isEnemyExist) return UnitControllerMessages.NOT_ENEMY;
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages archerAttack(int x, int y) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        boolean isArcherExist = false;
        boolean canArcherAttack = false;
        Cell targetCell = gameDatabase.getMap().getMap()[x][y];
        int distance = getDistance(x, y);
        for (Army e : gameDatabase.getSelectedUnits()) {
            int range = e.getLocation().getExistingBuilding().getBuildingType().getAttackPoint() -
                    targetCell.getExistingBuilding().getBuildingType().getAttackPoint();
            if (e.getArmyType().getRange() > 0) {
                isArcherExist = true;
                if (distance <= e.getArmyType().getRange() + range) {
                    canArcherAttack = true;
                    e.setTargetCell(targetCell);
                }
            }
        }
        if (!isArcherExist) return UnitControllerMessages.NOT_ARCHER;
        if (!canArcherAttack) return UnitControllerMessages.OUT_OF_RANGE;
        return UnitControllerMessages.SUCCESS;
    }

    private int getDistance(int x2, int y2) {
        int x1 = gameDatabase.getSelectedUnits().get(0).getLocation().getX();
        int y1 = gameDatabase.getSelectedUnits().get(0).getLocation().getY();
        return (int) (Math.sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2));
    }

    public UnitControllerMessages pourOil(String stringDirection) {
        ArrayList<Army> selectedArmies = gameDatabase.getSelectedUnits();
        for (Army e : selectedArmies)
            if (!e.getArmyType().equals(ArmyType.ENGINEER_WITH_OIL))
                return UnitControllerMessages.NOT_SELECT_OIL;
        Direction direction = Direction.stringToEnum(stringDirection);
        if (direction == null) return UnitControllerMessages.INVALID_DIRECTION;
        Cell currentCell = selectedArmies.get(0).getLocation();
        int x = currentCell.getX();
        int y = currentCell.getY();
        if (!checkValidPourOil(direction, x, y)) return UnitControllerMessages.CAN_NOT_POUR_OIL;
        Cell targetCell = getCellForPourOil(direction, x, y);
        if (currentCell.getExistingBuilding().getBuildingType().getHeight()
                >= targetCell.getExistingBuilding().getBuildingType().getHeight())
            killThemAll(targetCell);
        changeEngineerState(selectedArmies, new Pair<>(x, y));
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages digTunnel(int x, int y) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        //add pathfinder
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages buildEquipment(String equipmentType) {
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (selectedUnits == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : selectedUnits) {
            if (!e.getArmyType().equals(ArmyType.ENGINEER)) return UnitControllerMessages.NOT_SELECTED_ENGINEER;
        }
        WarMachineType warMachineType = WarMachineType.stringToEnum(equipmentType);
        if (warMachineType == null) return UnitControllerMessages.INVALID_TYPE;
        new Building(gameDatabase.getCurrentKingdom(), selectedUnits.get(0).getLocation(), BuildingType.SIEGE_TENT);
        //TODO check type of tent
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages digMoat(int x, int y) {
        //TODO dig moat
        return null;
    }

    public UnitControllerMessages removeMoat(int x, int y) {
        //TODO remove moat
        return null;
    }

    public UnitControllerMessages fillMoat(int x, int y) {
        //TODO fil opponent moat
        return null;
    }

    public UnitControllerMessages stop() {
        if (gameDatabase.getSelectedUnits() == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits()) {
            e.setPath(null);
            e.setPatrol(null);
            e.setTarget(null);
            e.setTargetCell(null);
        }
        return UnitControllerMessages.SUCCESS;
    }

    private void changeEngineerState(ArrayList<Army> armies, Pair<Integer, Integer> currentLocation) {
        PathFinder pathFinder = new PathFinder(gameDatabase.getMap(), currentLocation, false);
        ArrayList<Cell> pathToOil = new ArrayList<>();
        for (Building e : gameDatabase.getCurrentKingdom().getBuildings()) {
            if (e.getBuildingType().equals(BuildingType.OIL_SMELTER)) {
                pathFinder.search(new Pair<>(e.getLocation().getX(), e.getLocation().getY()));
                ArrayList<Cell> path = pathFinder.findPath();
                if (pathToOil.size() == 0 || pathToOil.size() < path.size())
                    pathToOil = path;
            }
        }
        for (Army e : armies) {
            e.changeEngineerState();
            e.setPath(pathToOil);
        }
    }

    private void killThemAll(Cell cell) {
        for (Army e : cell.getArmies()) {
            e.takeDamage(1000);
        }
    }

    private boolean checkValidPourOil(Direction direction, int x, int y) {
        int mapSize = gameDatabase.getMap().getSize();
        return (!direction.equals(Direction.UP) || x != 0) &&
                (!direction.equals(Direction.LEFT) || y != 0) &&
                (!direction.equals(Direction.RIGHT) || y != mapSize - 1) &&
                (!direction.equals(Direction.DOWN) || x != mapSize - 1);
    }

    private boolean checkXY(int x, int y) {
        return x < 0 || x >= gameDatabase.getMap().getSize() ||
                y < 0 || y >= gameDatabase.getMap().getSize();
    }

    private Cell getCellForPourOil(Direction direction, int x, int y) {
        Cell[][] cells = gameDatabase.getMap().getMap();
        if (direction.equals(Direction.UP))
            return cells[x + 1][y];
        else if (direction.equals(Direction.DOWN))
            return cells[x - 1][y];
        else if (direction.equals(Direction.RIGHT))
            return cells[x][y + 1];
        else
            return cells[x][y - 1];
    }

    public UnitControllerMessages disbandUnit() {
        if (gameDatabase.getSelectedUnits() == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits()) {
            if (e instanceof Soldier) {
                new People(e.getOwner());
                e.isDead();
            }
        }
        return UnitControllerMessages.SUCCESS;
    }
}
