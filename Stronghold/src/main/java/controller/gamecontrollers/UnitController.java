package controller.gamecontrollers;

import controller.functionalcontrollers.PathFinder;
import model.army.Army;
import model.army.ArmyType;
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
        boolean isAssassin = true;
        for (Army e : gameDatabase.getSelectedUnits())
            if (!e.getArmyType().equals(ArmyType.ASSASSIN)) {
                isAssassin = false;
                break;
            }
        Pair<Integer, Integer> startLocation = new Pair<>(startingCell.getX(), startingCell.getY());
        Pair<Integer, Integer> destination = new Pair<>(x, y);
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

    public UnitControllerMessages patrolUnit(int x1, int y1, int x2, int y2) {
        //TODO move unit between these two location
        return null;
    }

    public UnitControllerMessages stopUnitsPatrol(int x, int y) {
        //TODO stop units which is in that location
        return null;
    }

    public UnitControllerMessages setModeForUnits(int x, int y, String unitState) {
        //TODO set mode for units
        return null;
    }

    public UnitControllerMessages attackEnemy(int enemyX, int enemyY) {
        //TODO selected unit attack enemy
        return null;
    }

    public UnitControllerMessages archerAttack(int x, int y) {
        //TODO archers attack that location
        return null;
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
