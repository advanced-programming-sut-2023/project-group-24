package controller.gamecontrollers;

import model.GameDatabase;

import java.util.ArrayList;

public class GameController {
    GameDatabase gameDatabase;

    public GameController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public void nextTurn() {
        //TODO call functions which have to call when we go next turn
    }

    private void moveCurrentPlayerUnits() {
        //TODO move units' of current player
    }

    private void checkWarsAndFight() {
        //TODO check all cells has fight and do them
    }

    private boolean willWarHappenedInThisCell(Cell cell) {
        //TODO check that if war will happen in this cell return true
        return true;
    }

    private void fight(Cell cell) {
        //TODO fight all units in that cell
    }

    private boolean doesTrapExist(Cell cell) {
        //TODO if trap existed in that cell return true
        return true;
    }

    private boolean canPitchDitchLightOn(Cell cell) {
        //TODO check that can pith bitch light on or not
        return true;
    }

    private void activeTrap(Cell cell) {
        //TODO damage the armies which in that cell and remove trap
    }

    private void removeArmyFromCell(Cell cell, Army army) {
        //TODO remove unit from that cell if it's dead
    }

    private void checkBurnOilAndBurn(Cell cell) {
        //TODO check that if oil can burn in that cell and call burn oil func
    }

    private void burnOil(Cell cell) {
        //TODO if boilingOil and engineer existed it happen
    }

    private void hasSoldierDrowned(Cell cell) {
        //TODO check that if pitch is in that cell soldiers will die
    }

    private void checkCaptureGateAndCapture(Cell cell) {
        //TODO check that can gate captured and call capture gate
    }

    private void captureGate(Cell cell) {
        //TODO capture gate and open it
    }

    private void checkTunnelUnderBuilding(Cell cell) {
        //TODO call destroy function
    }

    private void destroyBuilding(Cell cell) {
        //TODO just destroy in that cell
    }

    private void checkFallenKingdom() {
        //TODO check that which kingdom had fallen
    }

    private void removeKingdom(Kingdom kingdom) {
        //TODO remove kingdom and its buildings and units
    }

}
