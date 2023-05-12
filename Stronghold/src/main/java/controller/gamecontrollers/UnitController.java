package controller.gamecontrollers;

import controller.functionalcontrollers.PathFinder;
import model.People;
import model.army.*;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.SiegeTent;
import model.databases.GameDatabase;
import model.enums.Direction;
import model.map.Cell;
import model.map.Map;
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
        if (armies.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        gameDatabase.setSelectedUnits(armies);
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages moveUnit(int x, int y) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        ArrayList<Army> selectedUnit = gameDatabase.getSelectedUnits();
        if (selectedUnit.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
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
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
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
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
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
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
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
        if (selectedUnits.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        boolean isEnemy = false;
        for (Army e : enemies)
            if (!e.getOwner().equals(gameDatabase.getCurrentKingdom())) {
                isEnemy = true;
                break;
            }
        if (!isEnemy)
            return UnitControllerMessages.NOT_ENEMY;
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
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
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
        if (selectedArmies.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : selectedArmies)
            if (!e.getArmyType().equals(ArmyType.ENGINEER_WITH_OIL))
                return UnitControllerMessages.NOT_SELECT_OIL;
        Direction direction = Direction.stringToEnum(stringDirection);
        if (direction == null) return UnitControllerMessages.INVALID_DIRECTION;
        Cell currentCell = selectedArmies.get(0).getLocation();
        int x = currentCell.getX();
        int y = currentCell.getY();
        if (!checkValidPourOil(direction, x, y)) return UnitControllerMessages.CAN_NOT_POUR_OIL;
        Cell targetCell = getCellWithDirection(direction, x, y);
        if (currentCell.getExistingBuilding().getBuildingType().getHeight()
                >= targetCell.getExistingBuilding().getBuildingType().getHeight())
            killThemAll(targetCell);
        changeEngineerState(selectedArmies, new Pair<>(x, y));
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages digTunnel() {
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits())
            if (!e.getArmyType().equals(ArmyType.TUNNELLER)) return UnitControllerMessages.IRRELEVANT_UNIT;
        for (int i = 1; i < 6; i++) {
            Building building = findBuilding(i);
            if (building == null) continue;
            building.takeDamage(400);
            if (building.getHp() <= 0) {
                building.getLocation().setExistingBuilding(null);
                building.getKingdom().removeBuilding(building);
            }
            gameDatabase.getSelectedUnits().get(0).isDead();
            return UnitControllerMessages.SUCCESS;
        }
        return UnitControllerMessages.BUILDING_NOT_FOUND;
    }

    public UnitControllerMessages buildEquipment(String equipmentType) {
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (selectedUnits.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : selectedUnits) {
            if (!e.getArmyType().equals(ArmyType.ENGINEER)) return UnitControllerMessages.NOT_SELECTED_ENGINEER;
        }
        WarMachineType warMachineType = WarMachineType.stringToEnum(equipmentType);
        if (warMachineType == null) return UnitControllerMessages.INVALID_TYPE;
        SiegeTent tent = (SiegeTent) Building.getBuildingFromBuildingType
                (gameDatabase.getCurrentKingdom(), selectedUnits.get(0).getLocation(), BuildingType.SIEGE_TENT);
        assert tent != null;
        tent.setProducingWarMachine(WarMachineType.stringToEnum(equipmentType));
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages digMoat(String stringDirection) {
        Direction direction = Direction.stringToEnum(stringDirection);
        if (direction == null) return UnitControllerMessages.INVALID_DIRECTION;
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (selectedUnits.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : selectedUnits)
            if (!(e instanceof Soldier && ((Soldier) e).getSoldierType().isCanDig()))
                return UnitControllerMessages.IRRELEVANT_UNIT;
        Cell targetCell = getCellWithDirection
                (direction, selectedUnits.get(0).getLocation().getX(), selectedUnits.get(0).getLocation().getY());
        if (!targetCell.canBuild()) return UnitControllerMessages.CANNOT_BUILD;
        Building.getBuildingFromBuildingType(gameDatabase.getCurrentKingdom(), targetCell, BuildingType.MOAT);
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages fillMoat(String stringDirection) {
        Direction direction = Direction.stringToEnum(stringDirection);
        if (direction == null) return UnitControllerMessages.INVALID_DIRECTION;
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (selectedUnits.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : selectedUnits)
            if (!(e instanceof Soldier && ((Soldier) e).getSoldierType().isCanDig()))
                return UnitControllerMessages.IRRELEVANT_UNIT;
        Cell targetCell = getCellWithDirection
                (direction, selectedUnits.get(0).getLocation().getX(), selectedUnits.get(0).getLocation().getY());
        if (!targetCell.getExistingBuilding().getBuildingType().equals(BuildingType.MOAT))
            return UnitControllerMessages.MOAT_DOES_NOT_EXIST;
        targetCell.getExistingBuilding().getKingdom().removeBuilding(targetCell.getExistingBuilding());
        targetCell.setExistingBuilding(null);
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages stop() {
        if (gameDatabase.getSelectedUnits() == null) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits()) {
            e.setPath(new ArrayList<>());
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

    private Cell getCellWithDirection(Direction direction, int x, int y) {
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
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits()) {
            if (e instanceof Soldier) {
                new People(e.getOwner());
                e.isDead();
            }
        }
        return UnitControllerMessages.SUCCESS;
    }

    private Building findBuilding(int radius) {
        Cell cell = gameDatabase.getSelectedUnits().get(0).getLocation();
        int cellX = cell.getX();
        int cellY = cell.getY();
        Map map = gameDatabase.getMap();
        for (int y = -1 * radius; y < 2 * radius; y++) {
            for (int x = -1 * radius; x < 2 * radius; x++) {
                if (!checkXY(cellX + x, cellY + y)) {
                    Building building = map.getMap()[cellX + x][cellY + y].getExistingBuilding();
                    if (building != null &&
                            !building.getKingdom().equals(gameDatabase.getCurrentKingdom()) &&
                            building.getBuildingType().canBeDestroyedByTunnels())
                        return building;
                }
            }
        }
        return null;
    }
}
