package controller.gamecontrollers;

import model.GameDatabase;

public class GameController {
    GameDatabase gameDatabase;

    public GameController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public void nextTurn() {
        //TODO call annother funcs
    }

    private void checkPopulationGrowth() {
        //TODO check factors and add population
    }

    private void moveCurrentPlayerUnits() {
        //TODO just move units
    }

    private void checkWarsAndFight() {
        //TODO check wars and it's needed call fight func
    }

    private void fight(Cell cell) {
        //TODO fight soldiers
    }

    private void checkAndUSeTrap(Cell cell) {
        //TODO call doesTrapExist func and if it's needed call activeTrap func
    }

    private boolean doesTrapExist(Cell cell) {
        //TODO just check that trap existed in that cell or not
        return true;
    }

    private void activeTrap(Cell cell) {
        //TODO active trap and remove that and take damage units
    }

    private boolean canPitchDitchBurn(Cell cell) {
        //check that
        return true;
    }

    private void checkAndBurnOil(Cell cell) {
        //TODO if it's ok call burn func
    }

    private void burnOil(Cell cell) {
        //TODO burn oil
    }

    private void canGateBeCaptured(Cell cell) {
        //TODO if it's ok call capure func
    }

    private void captureGate(Cell cell) {
        //TODO open gate
    }

    private void checkTunnelUnderCell(Cell cell) {
        //TODO if it's correct call destroy building func
    }

    private void destroyBuilding(Cell cell) {
        //TODO destroy building in that cell
    }

    private void removeArmy(Army army) {
        //TODO remove Army
    }

    private void willSoldierDrown(Cell cell) {
        //TODO if it's ok call removeArmy func
    }

    private void hasKingdomsFallen() {

    }

    private void removeKingdom(Kingdom kingdom) {
        //TODO remove kingdom and its soldiers and its building
    }
}
