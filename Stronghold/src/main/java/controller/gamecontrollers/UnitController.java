package controller.gamecontrollers;

import controller.functionalcontrollers.PathFinder;
import model.army.Army;
import model.army.ArmyType;
import model.army.UnitState;
import model.databases.GameDatabase;
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
        if (x < 0 || x >= gameDatabase.getMap().getSize() ||
                y < 0 || y >= gameDatabase.getMap().getSize())
            return UnitControllerMessages.INVALID_LOCATION;
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
        if (x < 0 || x >= gameDatabase.getMap().getSize() ||
                y < 0 || y >= gameDatabase.getMap().getSize())
            return UnitControllerMessages.INVALID_LOCATION;
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
        //TODO move unit between these two location
        return null;
    }

    public UnitControllerMessages stopUnitsPatrol(int x, int y) {
        //TODO stop units which is in that location
        return null;
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
        if (enemyX < 0 || enemyX >= gameDatabase.getMap().getSize() ||
                enemyY < 0 || enemyY >= gameDatabase.getMap().getSize())
            return UnitControllerMessages.INVALID_LOCATION;
        ArrayList<Army> armies = gameDatabase.getMap().getMap()[enemyX][enemyY].getArmies();
        ArrayList<Army> selectedUnits = gameDatabase.getSelectedUnits();
        if (armies == null) return UnitControllerMessages.NOT_ENEMY;
        boolean isEnemyExist = false;
        for (Army e : armies) {
            if (!e.getOwner().equals(gameDatabase.getCurrentKingdom())) {
                isEnemyExist = true;
                UnitControllerMessages moveMessage = moveUnit(enemyX, enemyY);
                if (!moveMessage.equals(UnitControllerMessages.SUCCESS)) return moveMessage;
                for (Army f : selectedUnits)
                    f.setTarget(e);
                break;
            }
        }
        if (!isEnemyExist) return UnitControllerMessages.NOT_ENEMY;
        return UnitControllerMessages.SUCCESS;
    }

    public UnitControllerMessages archerAttack(int x, int y) {
        if (x < 0 || x >= gameDatabase.getMap().getSize() ||
                y < 0 || y >= gameDatabase.getMap().getSize())
            return UnitControllerMessages.INVALID_LOCATION;
        boolean isArcherExist = false;
        Cell targetCell = gameDatabase.getMap().getMap()[x][y];
        int distance = getDistance(x, y);
        for (Army e : gameDatabase.getSelectedUnits()) {
            if (e.getArmyType().getRange() >= distance) {
                isArcherExist = true;
                e.setTargetCell(targetCell);
            }
        }
        if (!isArcherExist) return UnitControllerMessages.NOT_ARCHER;
        return UnitControllerMessages.SUCCESS;
    }

    private int getDistance(int x2, int y2) {
        int x1 = gameDatabase.getSelectedUnits().get(0).getLocation().getX();
        int y1 = gameDatabase.getSelectedUnits().get(0).getLocation().getY();
        return (int) (Math.sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2));
    }

    public UnitControllerMessages pourOil(String direction) {
        //TODO pour oil in that direction
        return null;
    }

    public UnitControllerMessages digTunnel(int x, int y) {
        //TODO dig tunnel
        return null;
    }

    public UnitControllerMessages buildEquipment(String equipmentType) {
        return null;
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

    public void disbandUnit() {
        //ma ke nafahmidim doc chi mige
    }
}
