package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import controller.functionalcontrollers.PathFinder;
import model.People;
import model.army.*;
import model.buildings.*;
import model.databases.GameDatabase;
import model.enums.Direction;
import model.enums.MovingType;
import model.map.Cell;
import model.map.Map;
import view.enums.messages.CreateMapMessages;
import view.enums.messages.UnitControllerMessages;

import java.util.ArrayList;

public class UnitController {
    private final GameDatabase gameDatabase;

    public UnitController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    private static int getRange(Cell enemyCell, Army e) {
        int ourHeight = 0, enemyHeight = 0;
        if (e.getLocation().getExistingBuilding() != null)
            ourHeight = e.getLocation().getExistingBuilding().getBuildingType().getHeight();
        if (enemyCell.getExistingBuilding() != null)
            enemyHeight = enemyCell.getExistingBuilding().getBuildingType().getHeight();
        return ourHeight - enemyHeight;
    }

    private static void setTarget(ArrayList<Army> selectedUnits, Army e) {
        for (Army f : selectedUnits) {
            f.setTargetBuilding(null);
            f.setTarget(null);
            f.setTargetCell(null);
            f.setTarget(e);
        }
    }

    private static boolean setArcherTarget(boolean canArcherAttack, Cell targetCell, int distance, Army e) {
        if (distance <= e.getArmyType().getRange() + getRange(targetCell, e)) {
            canArcherAttack = true;
            e.setTargetBuilding(null);
            e.setTarget(null);
            e.setTargetCell(null);
            e.setTargetCell(targetCell);
        }
        return canArcherAttack;
    }

    private static boolean hasEnoughHeight(Cell currentCell, Cell targetCell) {
        int height = 0, targetHeight = 0;
        if (currentCell.getExistingBuilding() != null)
            height = currentCell.getExistingBuilding().getBuildingType().getHeight();
        if (targetCell.getExistingBuilding() != null)
            targetHeight = targetCell.getExistingBuilding().getBuildingType().getHeight();
        return height >= targetHeight;
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
        MovingType movingType = getMovingType(selectedUnit);
        PathFinder pathFinder = new PathFinder(gameDatabase.getMap(), startLocation, movingType);
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

    public MovingType getMovingType(ArrayList<Army> armies) {
        boolean isAssassin = true;
        boolean canClimbLadder = true;
        for (Army army : armies) {
            if (!army.getArmyType().equals(ArmyType.ASSASSIN))
                isAssassin = false;
            if (army instanceof Soldier)
                if (!((Soldier) army).getSoldierType().isCanClimbLadder())
                    canClimbLadder = false;
        }
        if (isAssassin)
            return MovingType.ASSASSIN;
        if (canClimbLadder)
            return MovingType.CAN_CLIMB_LADDER;
        return MovingType.CAN_NOT_CLIMB_LADDER;
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
        for (Army e : gameDatabase.getSelectedUnits()) {
            e.setTargetBuilding(null);
            e.setTarget(null);
            e.setTargetCell(null);
            e.setPatrol(patrolWay);
        }
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
                e.setTargetBuilding(null);
                e.setTarget(null);
                e.setTargetCell(null);
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
            e.setTargetBuilding(null);
            e.setTarget(null);
            e.setTargetCell(null);
        }
        return UnitControllerMessages.SUCCESS;
    }

    private int getDistance(int x2, int y2) {
        int x1 = gameDatabase.getSelectedUnits().get(0).getLocation().getX();
        int y1 = gameDatabase.getSelectedUnits().get(0).getLocation().getY();
        return (int) (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
    }

    public CreateMapMessages dropUnit(Cell cell, String type, int count) {
        ArmyType armyType = ArmyType.stringToEnum(type);
        if (armyType == null)
            return CreateMapMessages.INVALID_TYPE;
        if (count < 1)
            return CreateMapMessages.INVALID_COUNT;
        if (!cell.canDropUnit())
            return CreateMapMessages.NOT_HERE;
        if (SoldierType.stringToEnum(type) != null) for (int i = 0; i < count; i++)
            new Soldier(cell, armyType, gameDatabase.getCurrentKingdom(), SoldierType.stringToEnum(type));
        else for (int i = 0; i < count; i++)
            new WarMachine(cell, armyType, gameDatabase.getCurrentKingdom(), WarMachineType.stringToEnum(type));
        return CreateMapMessages.SUCCESS;
    }

    public UnitControllerMessages attackEnemy(int enemyX, int enemyY) {
        if (checkXY(enemyX, enemyY)) return UnitControllerMessages.INVALID_LOCATION;
        Cell enemyCell = gameDatabase.getMap().getMap()[enemyX][enemyY];
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        if (!isEnemy(enemyCell.getArmies())) return UnitControllerMessages.NOT_ENEMY;
        int distance = getDistance(enemyX, enemyY);
        for (Army e : enemyCell.getArmies()) {
            if (isVisible(e)) continue;
            if (!e.getOwner().equals(gameDatabase.getCurrentKingdom())) {
                for (Army f : gameDatabase.getSelectedUnits())
                    if (f.getArmyType().getRange() > 0 && f.getArmyType().getRange() + getRange(enemyCell, e) < distance)
                        return UnitControllerMessages.OUT_OF_RANGE;
                for (Army f : gameDatabase.getSelectedUnits()) {
                    if (f.getArmyType().getRange() == 0) {
                        UnitControllerMessages moveMessage = moveUnit(enemyX, enemyY);
                        if (!moveMessage.equals(UnitControllerMessages.SUCCESS)) return moveMessage;
                    }
                }
                setTarget(gameDatabase.getSelectedUnits(), e);
                break;
            }
        }
        return UnitControllerMessages.SUCCESS;
    }

    private boolean isVisible(Army e) {
        return e.getArmyType().equals(ArmyType.ASSASSIN) && !e.getOwner().equals(gameDatabase.getCurrentKingdom())
                && !((Soldier) e).visibility();
    }

    private boolean isEnemy(ArrayList<Army> enemies) {
        for (Army e : enemies)
            if (!e.getOwner().equals(gameDatabase.getCurrentKingdom())) {
                if (isVisible(e))
                    continue;
                return true;
            }
        return false;
    }

    public UnitControllerMessages archerAttack(int x, int y) {
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        boolean isArcherExist = false;
        boolean canArcherAttack = false;
        Cell targetCell = gameDatabase.getMap().getMap()[x][y];
        int distance = getDistance(x, y);
        for (Army e : gameDatabase.getSelectedUnits()) {
            if (e.getArmyType().getRange() > 0) {
                isArcherExist = true;
                canArcherAttack = setArcherTarget(canArcherAttack, targetCell, distance, e);
            }
        }
        if (!isArcherExist) return UnitControllerMessages.NOT_ARCHER;
        if (!canArcherAttack) return UnitControllerMessages.OUT_OF_RANGE;
        return UnitControllerMessages.SUCCESS;
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
        if (targetCell.getExistingBuilding() != null) return UnitControllerMessages.CAN_NOT_POUR_OIL;
        if (hasEnoughHeight(currentCell, targetCell))
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
            if (building == null || building.getKingdom() == gameDatabase.getCurrentKingdom()) continue;
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
        int numberOfEngineer = 0;
        for (Army e : selectedUnits) {
            if (!e.getArmyType().equals(ArmyType.ENGINEER)) return UnitControllerMessages.NOT_SELECTED_ENGINEER;
            numberOfEngineer++;
        }
        WarMachineType warMachineType = WarMachineType.stringToEnum(equipmentType);
        if (warMachineType == null) return UnitControllerMessages.INVALID_TYPE;
        if (numberOfEngineer < warMachineType.getEngineerNeeded()) return UnitControllerMessages.NOT_ENOUGH_ENGINEER;
        SiegeTent tent = (SiegeTent) Building.getBuildingFromBuildingType
                (gameDatabase.getCurrentKingdom(), selectedUnits.get(0).getLocation(), BuildingType.SIEGE_TENT);
        assert tent != null;
        tent.setProducingWarMachine(WarMachineType.stringToEnum(equipmentType));
        for (int i = 0; i < warMachineType.getEngineerNeeded(); i++) {
            gameDatabase.getCurrentKingdom().removeArmy(selectedUnits.get(0));
            tent.getLocation().getArmies().remove(0);
            selectedUnits.remove(0);
        }
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
        if (targetCell.getExistingBuilding() == null
                || !targetCell.getExistingBuilding().getBuildingType().equals(BuildingType.MOAT))
            return UnitControllerMessages.MOAT_DOES_NOT_EXIST;
        targetCell.getExistingBuilding().getKingdom().removeBuilding(targetCell.getExistingBuilding());
        targetCell.setExistingBuilding(null);
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages stop() {
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits()) {
            e.setPath(new ArrayList<Cell>());
            e.setPatrol(null);
            e.setTarget(null);
            e.setTargetCell(null);
        }
        return UnitControllerMessages.SUCCESS;
    }

    private void changeEngineerState(ArrayList<Army> armies, Pair<Integer, Integer> currentLocation) {
        PathFinder pathFinder = new PathFinder(gameDatabase.getMap(), currentLocation, MovingType.CAN_NOT_CLIMB_LADDER);
        ArrayList<Cell> pathToOil = new ArrayList<>();
        for (Building e : gameDatabase.getCurrentKingdom().getBuildings()) {
            if (e.getBuildingType().equals(BuildingType.OIL_SMELTER) &&
                    ((EngineersNeededBuilding) e).hasEnoughWorkers()) {
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
        out:
        while (true) {
            for (Army e : cell.getArmies()) {
                e.takeDamage(1000);
                continue out;
            }
            break;
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
            return cells[x - 1][y];
        else if (direction.equals(Direction.DOWN))
            return cells[x + 1][y];
        else if (direction.equals(Direction.RIGHT))
            return cells[x][y + 1];
        else
            return cells[x][y - 1];
    }

    public UnitControllerMessages disbandUnit() {
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits()) {
            if (e instanceof Soldier) {
                new People();
                e.isDead();
            }
        }
        gameDatabase.setSelectedUnits(new ArrayList<Army>());
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

    public UnitControllerMessages attackBuilding(int x, int y) {
        ArrayList<Army> selectedArmies = gameDatabase.getSelectedUnits();
        if (checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        if (selectedArmies.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        Building building = gameDatabase.getMap().getMap()[x][y].getExistingBuilding();
        if (building == null || building.getKingdom().equals(gameDatabase.getCurrentKingdom()))
            return UnitControllerMessages.NULL_SELECTED_BUILDING;
        for (Army e : selectedArmies)
            if (e.getArmyType().getRange() > 0 && isOutOfRange(x, y, e))
                return UnitControllerMessages.OUT_OF_RANGE;
        for (Army e : selectedArmies)
            if (e.getArmyType().getRange() == 0 && getDistance(x, y) > 1)
                return UnitControllerMessages.CANNOT_ATTACK;
        for (Army e : selectedArmies)
            e.setTargetBuilding(building);
        return UnitControllerMessages.SUCCESS;
    }

    private boolean isOutOfRange(int x, int y, Army e) {
        int height = 0;
        if (e.getLocation().getExistingBuilding() != null)
            height = e.getLocation().getExistingBuilding().getBuildingType().getHeight();
        return e.getArmyType().getRange() > 0 && e.getArmyType().getRange() + height < getDistance(x, y);
    }

    public UnitControllerMessages setLadder(String stringDirection) {
        Direction direction = Direction.stringToEnum(stringDirection);
        if (direction == null) return UnitControllerMessages.INVALID_DIRECTION;
        if (gameDatabase.getSelectedUnits().size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : gameDatabase.getSelectedUnits())
            if (!e.getArmyType().equals(ArmyType.LADDER_MAN)) return UnitControllerMessages.IRRELEVANT_UNIT;
        Cell currentCell = gameDatabase.getSelectedUnits().get(0).getLocation();
        Cell targetCell = getCellWithDirection(direction, currentCell.getX(), currentCell.getY());
        if (!(targetCell.getExistingBuilding() instanceof DefenceBuilding)) return UnitControllerMessages.NO_BUILDING;
        ((DefenceBuilding) targetCell.getExistingBuilding()).addLadder(direction);
        currentCell.removeArmy(gameDatabase.getSelectedUnits().get(0));
        gameDatabase.getSelectedUnits().get(0).isDead();
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages attackWall(String stringDirection) {
        Direction direction = Direction.stringToEnum(stringDirection);
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (selectedUnits.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        if (direction == null) return UnitControllerMessages.INVALID_DIRECTION;
        for (Army e : selectedUnits)
            if (!(e instanceof WarMachine) || !((WarMachine) e).getWarMachineType().equals(WarMachineType.SIEGE_TOWER))
                return UnitControllerMessages.IRRELEVANT_UNIT;
        Cell currentCell = gameDatabase.getSelectedUnits().get(0).getLocation();
        Cell targetCell = getCellWithDirection(direction, currentCell.getX(), currentCell.getY());
        if (targetCell.getExistingBuilding() == null ||
                !(targetCell.getExistingBuilding().getBuildingType().equals(BuildingType.LOW_WALL) ||
                        targetCell.getExistingBuilding().getBuildingType().equals(BuildingType.HIGH_WALL)) ||
                targetCell.getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            return UnitControllerMessages.NO_BUILDING;
        currentCell.getArmies().remove(0);
        currentCell.setExistingBuilding(Building.getBuildingFromBuildingType
                (gameDatabase.getCurrentKingdom(), currentCell, BuildingType.STAIR));
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages assignEngineer(int x, int y) {
        if (!checkXY(x, y)) return UnitControllerMessages.INVALID_LOCATION;
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (selectedUnits.size() == 0) return UnitControllerMessages.NULL_SELECTED_UNIT;
        for (Army e : selectedUnits)
            if (!e.getArmyType().equals(ArmyType.ENGINEER)) return UnitControllerMessages.IRRELEVANT_UNIT;
        Building building = gameDatabase.getMap().getMap()[x][y].getExistingBuilding();
        if (building == null ||
                !building.getBuildingType().equals(BuildingType.OIL_SMELTER) ||
                !building.getKingdom().equals(gameDatabase.getCurrentKingdom()))
            return UnitControllerMessages.BUILDING_NOT_FOUND;
        if (((EngineersNeededBuilding) building).hasEnoughWorkers()) return UnitControllerMessages.ALREADY_WORKING;
        Pair<Integer, Integer> current =
                new Pair<>(selectedUnits.get(0).getLocation().getX(), selectedUnits.get(0).getLocation().getY());
        PathFinder pathFinder = new PathFinder(gameDatabase.getMap(), current, getMovingType(selectedUnits));
        PathFinder.OutputState moveMessage = pathFinder.search(new Pair<>(x, y));
        if (!moveMessage.equals(PathFinder.OutputState.NO_ERRORS)) return UnitControllerMessages.BLOCK;
        ArrayList<Cell> path = pathFinder.findPath();
        for (Army e : selectedUnits)
            e.setPath(path);
        return UnitControllerMessages.SUCCESS;
    }
}
