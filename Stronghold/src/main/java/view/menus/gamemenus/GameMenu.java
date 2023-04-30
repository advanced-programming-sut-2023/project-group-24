package view.menus.gamemenus;

import controller.MainController;
import controller.gamecontrollers.BuildingController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.UnitController;
import view.enums.messages.UnitControllerMessages;

import java.util.regex.Matcher;

public class GameMenu {
    private final KingdomController kingdomController;
    private final UnitController unitController;
    private final BuildingController buildingController;

    public GameMenu(KingdomController kingdomController, UnitController unitController, BuildingController buildingController) {
        this.kingdomController = kingdomController;
        this.unitController = unitController;
        this.buildingController = buildingController;
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

    private void dropBuilding(Matcher matcher) {
        //TODO connect BuildingController and sout result
    }

    private void selectBuilding(Matcher matcher) {
        //TODO connect BuildingController and sout result
    }

    private void createUnit(Matcher matcher) {
        //TODO connect BuildingController and sout result
    }

    private void repair() {
        //TODO connect BuildingController and sout result
    }

    private void createResources(Matcher matcher) {
        //TODO connect BuildingController and sout result
    }

    private void selectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = MainController.removeDoubleQuotation(matcher.group("type"));
        UnitControllerMessages message = unitController.selectUnit(x, y, type);
        switch (message) {
            case SUCCESS -> System.out.println("You successfully select units!");
            case INVALID_LOCATION -> System.out.println("You entered invalid location!");
            case INVALID_TYPE -> System.out.println("You entered invalid type of soldier!");
        }
    }

    private void moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.moveUnit(x, y);
        switch (message) {
            case SUCCESS -> System.out.println("Your units will move in this location!");
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
            case INVALID_LOCATION -> System.out.println("You entered invalid location!");
            case BLOCK -> System.out.println("You can not move to that cell!");
            case ALREADY_IN_DESTINATION -> System.out.println("Your armies already is in this location!");
        }
    }

    private void patrolUnit(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void stopUnitsPatrol(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void setStateForUnits(Matcher matcher) {
        String state = matcher.group("state");
        UnitControllerMessages message = unitController.setStateForUnits(state);
        switch (message) {
            case SUCCESS -> System.out.println("Successfully set!");
            case INVALID_STATE -> System.out.println("Invalid state!");
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
        }
    }

    private void attackEnemy(Matcher matcher) {
        int enemyX = Integer.parseInt(matcher.group("enemyX"));
        int enemyY = Integer.parseInt(matcher.group("enemyY"));
        UnitControllerMessages message = unitController.attackEnemy(enemyX, enemyY);
        switch (message) {
            case INVALID_LOCATION -> System.out.println("You entered invalid location!");
            case NOT_ENEMY -> System.out.println("There aren't any enemy in that cell!");
            case SUCCESS -> System.out.println("Your armies will attack that enemies!");
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
            case BLOCK -> System.out.println("You can not move to that cell!");
            case ALREADY_IN_DESTINATION -> System.out.println("Your armies already is in this location!");
        }
    }

    private void archerAttack(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.archerAttack(x, y);
        switch (message) {
            case INVALID_LOCATION -> System.out.println("You entered invalid location!");
            case SUCCESS -> System.out.println("Your archers will attack that enemies!");
            case NOT_ARCHER -> System.out.println("You can not attack this cell with this units!");
        }
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

    private void digMoat(Matcher matcher) {
        //TODO connect unit controller
    }

    private void removeMoat(Matcher matcher) {
        //TODO connect unit controller
    }

    private void fillMoat(Matcher matcher) {
        //TODO connect unit controller
    }
}