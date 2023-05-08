package view.menus.gamemenus;

import controller.AppController;
import controller.MainController;
import controller.gamecontrollers.BuildingController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.UnitController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.BuildingControllerMessages;
import view.enums.messages.UnitControllerMessages;
import view.menus.GetInputFromUser;

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
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu().equals(MenusName.GAME_MENU)) {
            command = GetInputFromUser.getUserInput();
            if (Commands.getMatcher(command, Commands.SHOW_MAP) != null)
                showMap();
            else if (Commands.getMatcher(command, Commands.OPEN_TRADE_MENU) != null)
                tradeMenu();
            else if (Commands.getMatcher(command, Commands.OPEN_SHOP_MENU) != null)
                shopMenu();
            else if (Commands.getMatcher(command, Commands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (Commands.getMatcher(command, Commands.SHOW_POPULARITY) != null)
                showPopularity();
            else if ((matcher = Commands.getMatcher(command, Commands.FOOD_RATE)) != null)
                setFoodRate(matcher);
            else if (Commands.getMatcher(command, Commands.FOOD_RATE_SHOW) != null)
                showFoodRate();
            else if ((matcher = Commands.getMatcher(command, Commands.SET_TEXTURE)) != null)
                setTaxRate(matcher);
            else if (Commands.getMatcher(command, Commands.TAX_RATE_SHOW) != null)
                showTaxRate();
            else if ((matcher = Commands.getMatcher(command, Commands.FEAR_RATE)) != null)
                setFearRate(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.SELECT_BUILDING)) != null)
                selectBuilding(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.CREATE_UNIT)) != null)
                createUnit(matcher);
            else if (Commands.getMatcher(command, Commands.REPAIR) != null)
                repair();
                //CREATE RESOURCE???????????????
            else if ((matcher = Commands.getMatcher(command, Commands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.PATROL_UNIT)) != null)
                patrolUnit(matcher);
            else if (Commands.getMatcher(command, Commands.STOP_PATROL) != null)
                stopUnitsPatrol();
            else if ((matcher = Commands.getMatcher(command, Commands.SET_STATE)) != null)
                setStateForUnits(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.ATTACK)) != null)
                attackEnemy(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.ATTACK_ARCHER)) != null)
                archerAttack(matcher);
            else if (Commands.getMatcher(command, Commands.STOP) != null)
                stop();
            else if ((matcher = Commands.getMatcher(command, Commands.POUR_OIL)) != null)
                pourOil(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.BUILD)) != null)
                buildEquipment(matcher);
            else if (Commands.getMatcher(command, Commands.DISBAND) != null)
                disbandUnit();
            else if ((matcher = Commands.getMatcher(command, Commands.DIG_MOAT)) != null)
                digMoat(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.REMOVE_MOAT)) != null)
                removeMoat(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.FILL_MOAT)) != null)
                fillMoat(matcher);
        }
    }

    private void showMap() {
        AppController.setCurrentMenu(MenusName.SHOW_MAP_MENU);
    }

    private void tradeMenu() {
        AppController.setCurrentMenu(MenusName.TRADE_MENU);
    }

    private void shopMenu() {
        AppController.setCurrentMenu(MenusName.SHOP_MENU);
    }

    private void showPopularityFactors() {
        String result = kingdomController.showPopularityFactors();
        System.out.println(result);
    }

    private void showPopularity() {
        int result = kingdomController.showPopularity();
        System.out.println(result);
    }

    private void setFoodRate(Matcher matcher) {
        int foodRate = Integer.parseInt(matcher.group("RateNumber"));

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
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        BuildingControllerMessages message = buildingController.dropBuilding(x, y, type, kingdomController);
        switch (message) {
            case LOCATION_OUT_OF_BOUNDS -> System.out.println("You entered invalid location!");
            case INVALID_TYPE -> System.out.println("You entered invalid type of building!");
            case CANNOT_BUILD_HERE -> System.out.println("You can not drop building here!");
            case NOT_ENOUGH_MATERIAL -> System.out.println("You don't have enough material to build this building!");
            case NOT_ENOUGH_GOLD -> System.out.println("You don't have enough gold to build this building!");
            case SUCCESS -> System.out.println("The building was successfully built!");
        }
    }

    private void selectBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        BuildingControllerMessages message = buildingController.selectBuilding(x, y);
        switch (message) {
            case LOCATION_OUT_OF_BOUNDS -> System.out.println("You entered invalid location!");
            case NO_BUILDINGS -> System.out.println("There is no building to select!");
            case NOT_OWNER -> System.out.println("You are not the owner of this building!");
            case SUCCESS -> System.out.println("The building was successfully selected!");
        }
    }

    private void createUnit(Matcher matcher) {
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        BuildingControllerMessages message = buildingController.createUnit(type, count, kingdomController);
        switch (message) {
            case INCORRECT_BUILDING, IRRELEVANT_BUILDING ->
                    System.out.println("You can not create this unit in selected building!");
            case INCORRECT_COUNT -> System.out.println("You entered invalid count!");
            case INVALID_TYPE -> System.out.println("Unit with this type does not exist!");
            case NOT_ENOUGH_MATERIAL -> System.out.println("You don't have enough weapons to create this unit!");
            case SUCCESS -> System.out.println("The units were successfully created!");
        }
    }

    private void repair() {
        BuildingControllerMessages message = buildingController.repair(kingdomController);
        switch (message) {
            case NO_BUILDINGS_SELECTED -> System.out.println("You didn't select any building!");
            case IRRELEVANT_BUILDING -> System.out.println("You can not repair this building!");
            case NOT_ENOUGH_MATERIAL -> System.out.println("You don't have enough material to repair this building!");
            case ENEMY_IS_NEARBY -> System.out.println("You can not repair this building, enemy is nearby your building!");
            case SUCCESS -> System.out.println("Your building was successfully repaired!");
        }
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
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.patrolUnit(x, y);
        switch (message) {
            case INVALID_LOCATION -> System.out.println("You entered invalid location!");
            case BLOCK -> System.out.println("You can not move to that cell!");
            case ALREADY_IN_DESTINATION -> System.out.println("Your armies already is in this location!");
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
            case SUCCESS -> System.out.println("Successful!");
        }
    }

    private void stopUnitsPatrol() {
        UnitControllerMessages message = unitController.stopUnitsPatrol();
        switch (message) {
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
            case NOT_PATROL -> System.out.println("Selected units aren't patrolling!");
            case SUCCESS -> System.out.println("Patrolling of selected units has been stopped");
        }
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
            case OUT_OF_RANGE -> System.out.println("Enemy is out of range!");
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
            case OUT_OF_RANGE -> System.out.println("Enemy is out of range!");
        }
    }

    private void stop() {
        UnitControllerMessages message = unitController.stop();
        switch (message) {
            case SUCCESS -> System.out.println("The selected units have been stopped successfully!");
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
        }
    }

    private void pourOil(Matcher matcher) {
        String direction = matcher.group("direction");
        UnitControllerMessages message = unitController.pourOil(direction);
        switch (message) {
            case NOT_SELECT_OIL -> System.out.println("You didn't select engineer with oil!");
            case INVALID_DIRECTION -> System.out.println("You entered invalid direction!");
            case CAN_NOT_POUR_OIL -> System.out.println("You can not pour oil in that direction!");
            case SUCCESS -> System.out.println("The oil was successfully poured!");
        }
    }

    private void digTunnel(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void buildEquipment(Matcher matcher) {
        //TODO connect UnitController and sout result
    }

    private void disbandUnit() {
        UnitControllerMessages message = unitController.disbandUnit();
        switch (message) {
            case SUCCESS -> System.out.println("Selected units successfully disband!");
            case NULL_SELECTED_UNIT -> System.out.println("You didn't select any unit!");
        }
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