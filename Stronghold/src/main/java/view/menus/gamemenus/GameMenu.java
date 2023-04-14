package view.menus.gamemenus;

import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.ShowMapController;
import controller.gamecontrollers.UnitController;

import java.util.regex.Matcher;

public class GameMenu {
    private KingdomController kingdomController;
    private ShowMapController showMapController;
    private UnitController unitController;

    public GameMenu(KingdomController kingdomController, UnitController unitController) {
        this.kingdomController = kingdomController;
        this.unitController = unitController;
    }

    public void run() {
        //TODO get inputs from GetInput class and check input of user
    }

    private void showMap() {
        //TODO set current menu as showMap
    }

    private void showPopularityFactors() {
        //TODO connect KingdomController and sout result
    }

    private void showPopularity() {
        //TODO connect KingdomController and sout result
    }

    private void setFoodRate(Matcher matcher) {
        //TODO connect KingdomController and sout result
    }

    private void showFoodRate() {
        //TODO connect KingdomController and sout result
    }

    private void setTaxRate(Matcher matcher) {
        //TODO connect KingdomController and sout result
    }

    private void showTaxRate() {
        //TODO connect KingdomController and sout result
    }

    private void setFearRate(Matcher matcher) {
        //TODO connect KingdomController and sout result
    }

    private void checkPopulationGrowth() {
        //TODO connect UnitController
    }

    private void selectUnit(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void moveUnit(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void patrolUnit(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void stopUnitsPatrol(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void setModeForUnits(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void attackEnemy(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void archerAttack(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void pourOil(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void digTunnel(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void buildEquipment(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void disbandUnit() {
        //TODO connect UnitController and sout result
    }
}