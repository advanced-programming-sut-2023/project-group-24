package controller.gamecontrollers;

import model.GameDatabase;
import utils.enums.Direction;
import utils.enums.messages.UnitControllerMessages;

public class UnitController {
    private GameDatabase gameDatabase;

    public UnitController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public void checkPopulationGrowth() {
        //TODO check factors and add population
    }

    public UnitControllerMessages selectUnit(int x, int y /*TODO add unit type*/) {
        //TODO check selected cell and save selected unit in game database
        return null;
    }

    public UnitControllerMessages moveUnit(int x, int y) {
        //TODO move selected unit into that location
        return null;
    }

    public UnitControllerMessages patrolUnit(int x1, int y1, int x2, int y2) {
        //TODO move unit between these two location
        return null;
    }

    public UnitControllerMessages stopUnitsPatrol(int x, int y) {
        //TODO stop units which is in that location
        return null;
    }

    public UnitControllerMessages setModeForUnits(int x, int y, UnitsMode unitsMode) {
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

    public UnitControllerMessages pourOil(Direction direction) {
        //TODO pour oil in that direction
        return null;
    }

    public UnitControllerMessages digTunnel(int x, int y) {
        //TODO dig tunnel
        return null;
    }

    public UnitControllerMessages buildEquipment(/*TODO add equipment type*/) {
        return null;
    }

    public void disbandUnit() {
        //ma ke nafahmidim doc chi mige
    }
}