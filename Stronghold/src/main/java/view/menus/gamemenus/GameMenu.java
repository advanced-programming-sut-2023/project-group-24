package view.menus.gamemenus;

import controller.AppController;
import controller.MainController;
import controller.MenusName;
import controller.gamecontrollers.BuildingController;
import controller.gamecontrollers.GameController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.UnitController;
import view.enums.commands.Commands;
import view.enums.messages.BuildingControllerMessages;
import view.enums.messages.UnitControllerMessages;
import view.menus.GetInputFromUser;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameMenu {
    private final KingdomController kingdomController;
    private final UnitController unitController;
    private final BuildingController buildingController;
    private final GameController gameController;

    public GameMenu(KingdomController kingdomController, UnitController unitController,
                    BuildingController buildingController, GameController gameController) {
        this.kingdomController = kingdomController;
        this.unitController = unitController;
        this.buildingController = buildingController;
        this.gameController = gameController;

    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu().equals(MenusName.GAME_MENU)) {
            command = GetInputFromUser.getUserInput();
            if (Commands.getMatcher(command, Commands.OPEN_SHOW_MAP_MENU) != null)
                showMap();
            else if (Commands.getMatcher(command, Commands.OPEN_TRADE_MENU) != null)
                tradeMenu();
            else if (Commands.getMatcher(command, Commands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (Commands.getMatcher(command, Commands.SHOW_POPULARITY) != null)
                showPopularity();
            else if ((matcher = Commands.getMatcher(command, Commands.FOOD_RATE)) != null)
                setFoodRate(matcher);
            else if (Commands.getMatcher(command, Commands.FOOD_RATE_SHOW) != null)
                showFoodRate();
            else if (Commands.getMatcher(command, Commands.NEXT_TURN) != null)
                nexTurn();
            else if (Commands.getMatcher(command, Commands.ROUND_PLAYED) != null)
                roundPlayed();
            else if ((matcher = Commands.getMatcher(command, Commands.TAX_RATE)) != null)
                setTaxRate(matcher);
            else if (Commands.getMatcher(command, Commands.SHOW_FOOD_LIST) != null)
                showFoodList();
            else if (Commands.getMatcher(command, Commands.TAX_RATE_SHOW) != null)
                showTaxRate();
            else if ((matcher = Commands.getMatcher(command, Commands.PRODUCE_ITEM)) != null)
                selectItemToProduce(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.SELECT_BUILDING)) != null)
                selectBuilding(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.CREATE_UNIT)) != null)
                createUnit(matcher);
            else if (Commands.getMatcher(command, Commands.REPAIR) != null)
                repair();
            else if ((matcher = Commands.getMatcher(command, Commands.ASSIGN_ENGINEER_OIL)) != null)
                assignEngineer(matcher);
            else if (Commands.getMatcher(command, Commands.SHOW_BUILDING_DETAILS) != null)
                showDetail();
            else if (Commands.getMatcher(command, Commands.OPEN_DOG_CAGE) != null)
                openDogCage();
            else if (Commands.getMatcher(command, Commands.CHANGE_GATE_STATE) != null)
                changeGateClosedState();
            else if ((matcher = Commands.getMatcher(command, Commands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if (Commands.getMatcher(command, Commands.PRODUCE_LEATHER) != null)
                produceLeather();
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
            else if (Commands.getMatcher(command, Commands.DIG_TUNNEL) != null)
                digTunnel();
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
            else if (Commands.getMatcher(command, Commands.SHOW_CURRENT_MENU) != null)
                System.out.println("Game menu");
            else if (Commands.getMatcher(command, Commands.TURN_PLAYED) != null)
                turnPlayed();
            else if ((matcher = Commands.getMatcher(command, Commands.ATTACK_WALL)) != null)
                attackWall(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.ATTACK_BUILDING)) != null)
                attackBuilding(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.SET_LADDER)) != null)
                setLadder(matcher);
            else
                System.out.println("Invalid command!");
        }
    }

    private void assignEngineer(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.assignEngineer(x, y);
        switch (message) {
            case SUCCESS:
                System.out.println("Your engineer will work there!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case BUILDING_NOT_FOUND:
                System.out.println("There is no oil smelter");
                break;
            case ALREADY_WORKING:
                System.out.println("This building has enough worker!");
                break;
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case BLOCK:
                System.out.println("You engineer can't move there!");
                break;
            case IRRELEVANT_UNIT:
                System.out.println("You can not do that with these units!");
                break;
        }
    }

    private void attackWall(Matcher matcher) {
        String direction = matcher.group("direction");
        UnitControllerMessages message = unitController.attackWall(direction);
        switch (message) {
            case SUCCESS:
                System.out.println("Successful!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any building!");
                break;
            case INVALID_DIRECTION:
                System.out.println("You entered invalid location!");
                break;
            case IRRELEVANT_UNIT:
                System.out.println("You can not do this with these units!");
                break;
            case NO_BUILDING:
                System.out.println("There is no building to connect to it!");
                break;
        }
    }

    private void setLadder(Matcher matcher) {
        String direction = matcher.group("direction");
        UnitControllerMessages message = unitController.setLadder(direction);
        switch (message) {
            case SUCCESS:
                System.out.println("Ladder add successfully!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any building!");
                break;
            case NO_BUILDING:
                System.out.println("There is no building in that direction!");
                break;
            case IRRELEVANT_UNIT:
                System.out.println("You can not do this with these units!");
                break;
            case INVALID_DIRECTION:
                System.out.println("You entered invalid direction!");
                break;
        }
    }

    private void attackBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.attackBuilding(x, y);
        switch (message) {
            case CANNOT_ATTACK:
                System.out.println("Building is far from you, Please go near that!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case NULL_SELECTED_BUILDING:
                System.out.println("There is no building in that location!");
                break;
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case OUT_OF_RANGE:
                System.out.println("The building is out of range!");
                break;
            case SUCCESS:
                System.out.println("Your unit have this building as a target!");
                break;
            case BLOCK:
                System.out.println("You can not target that building!");
                break;
        }
    }

    private void nexTurn() {
        gameController.nextTurn(kingdomController);
        if (gameController.isGameDone()) {
            System.out.println("User " + gameController.getWinner() + " win the game!");
            AppController.setCurrentMenu(MenusName.MAIN_MENU);
        } else
            System.out.println("It's " + gameController.getCurrentUser() + " turn now!");
    }

    private void turnPlayed() {
        int result = kingdomController.turnPlayed();
        System.out.println("You played " + result + " turns!");
    }

    private void roundPlayed() {
        int result = kingdomController.roundPlayed();
        System.out.println("you played " + result + " rounds!");
    }

    private void showMap() {
        AppController.setCurrentMenu(MenusName.SHOW_MAP_MENU);
        System.out.println("You are in show map menu!");
    }

    private void tradeMenu() {
        AppController.setCurrentMenu(MenusName.TRADE_MENU);
        System.out.println("You are in trade menu!");
    }

    private void shopMenu() {
        AppController.setCurrentMenu(MenusName.SHOP_MENU);
        System.out.println("You are in shop menu!");
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
        int foodRate = Integer.parseInt(matcher.group("rateNumber"));
        BuildingControllerMessages message = buildingController.setFoodRate(foodRate, kingdomController);
        switch (message) {
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any building!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You can not set food rate in this building!");
                break;
            case INVALID_NUMBER:
                System.out.println("You entered invalid rate!");
                break;
            case SUCCESS:
                System.out.println("Successfully!");
                break;
        }
    }

    private void showFoodList() {
        String result = kingdomController.showFoodList();
        System.out.println(result);
    }

    private void showFoodRate() {
        int result = kingdomController.showFoodRate();
        System.out.println("FoodRate: " + result);
    }

    private void setTaxRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));
        BuildingControllerMessages message = buildingController.setTaxRate(rateNumber, kingdomController);
        switch (message) {
            case SUCCESS:
                System.out.println("Successful!");
                break;
            case INVALID_NUMBER:
                System.out.println("You entered invalid rate!");
                break;
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any building!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You can not do this in current selected building!");
                break;
        }
    }

    private void showTaxRate() {
        int taxRate = kingdomController.showTaxRate();
        System.out.println("Tax rate : " + taxRate);
    }

    private void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        BuildingControllerMessages message = buildingController.dropBuilding(x, y, type, kingdomController);
        switch (message) {
            case LOCATION_OUT_OF_BOUNDS:
                System.out.println("You entered invalid location!");
                break;
            case INVALID_TYPE:
                System.out.println("You entered invalid type of building!");
                break;
            case CANNOT_BUILD_HERE:
                System.out.println("You can not drop building here!");
                break;
            case NOT_ENOUGH_MATERIAL:
                System.out.println("You don't have enough material to build this building!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You cannot build the building!");
                break;
            case NOT_ENOUGH_GOLD:
                System.out.println("You don't have enough gold to build this building!");
                break;
            case SUCCESS:
                System.out.println("The building was successfully built!");
                break;
        }
    }

    private void selectBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        BuildingControllerMessages message = buildingController.selectBuilding(x, y);
        switch (message) {
            case LOCATION_OUT_OF_BOUNDS:
                System.out.println("You entered invalid location!");
                break;
            case NO_BUILDINGS:
                System.out.println("There is no building to select!");
                break;
            case NOT_OWNER:
                System.out.println("You are not the owner of this building!");
                break;
            case SUCCESS:
                System.out.println("The building was successfully selected!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You cannot select a moat");
                break;
            case MARKET:
                shopMenu();
                break;
        }
    }

    private void createUnit(Matcher matcher) {
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        BuildingControllerMessages message = buildingController.createUnit(type, count, kingdomController);
        switch (message) {
            case INCORRECT_BUILDING:
                System.out.println("This building cannot make troops!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You cannot create this unit in selected building!");
                break;
            case INCORRECT_COUNT:
                System.out.println("You entered invalid count!");
                break;
            case INVALID_TYPE:
                System.out.println("Unit with this type does not exist!");
                break;
            case NOT_ENOUGH_MATERIAL:
                System.out.println("You don't have enough weapons to create this unit!");
                break;
            case NOT_ENOUGH_PEOPLE:
                System.out.println("Your kingdom does not have unemployed people");
                break;
            case SUCCESS:
                System.out.println("The units were successfully created!");
                break;
        }
    }

    private void repair() {
        BuildingControllerMessages message = buildingController.repair(kingdomController);
        switch (message) {
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any building!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You can not repair this building!");
                break;
            case NOT_ENOUGH_MATERIAL:
                System.out.println("You don't have enough material to repair this building!");
                break;
            case ENEMY_IS_NEARBY:
                System.out.println("You can not repair this building, enemy is nearby your building!");
                break;
            case SUCCESS:
                System.out.println("Your building was successfully repaired!");
                break;
        }
    }

    private void changeGateClosedState() {
        BuildingControllerMessages message = buildingController.changeGateClosedState();
        switch (message) {
            case SUCCESS:
                System.out.println("Success!");
                break;
            case ENEMY_IS_NEARBY:
                System.out.println("Enemy is nearby!");
                break;
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any buildings!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You didn't select a gate!");
                break;
        }
    }

    private void openDogCage() {
        BuildingControllerMessages message = buildingController.openDogCage();
        switch (message) {
            case SUCCESS:
                System.out.println("Successful!");
                break;
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any buildings!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You didn't select a dog cage!");
                break;
        }
    }

    private void showDetail() {
        ArrayList<String> details = buildingController.showDetails();
        for (String e : details)
            System.out.println(e);
    }

    private void produceLeather() {
        BuildingControllerMessages message = buildingController.produceLeather(kingdomController);
        switch (message) {
            case SUCCESS:
                System.out.println("Successful!");
                break;
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any buildings!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You didn't select dairy farm!");
                break;
            case NOT_ENOUGH_SPACE:
                System.out.println("Not enough space for the leather armor");
                break;
            case NOT_ENOUGH_COWS:
                System.out.println("You don't have enough cow to produce leather!");
                break;
        }
    }

    private void selectItemToProduce(Matcher matcher) {
        String name = MainController.removeDoubleQuotation(matcher.group("name"));
        BuildingControllerMessages message = buildingController.selectItemToProduce(name);
        switch (message) {
            case SUCCESS:
                System.out.println("Successful!");
                break;
            case NO_BUILDINGS_SELECTED:
                System.out.println("You didn't select any buildings!");
                break;
            case IRRELEVANT_BUILDING:
                System.out.println("You didn't select a dog cage!");
                break;
            case ITEM_DOES_NOT_EXIST:
                System.out.println("This item doesn't exist!");
                break;
            case CANNOT_PRODUCE_ITEM:
                System.out.println("This building can not produce this item!");
                break;
        }
    }

    private void selectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = MainController.removeDoubleQuotation(matcher.group("type"));
        UnitControllerMessages message = unitController.selectUnit(x, y, type);
        switch (message) {
            case SUCCESS:
                System.out.println("You successfully select units!");
                break;
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case INVALID_TYPE:
                System.out.println("You entered invalid type of soldier!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("There is no unit to select!");
                break;
        }
    }

    private void moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.moveUnit(x, y);
        switch (message) {
            case SUCCESS:
                System.out.println("Your units will move in this location!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case BLOCK:
                System.out.println("You can not move to that cell!");
                break;
            case ALREADY_IN_DESTINATION:
                System.out.println("Your armies already is in this location!");
                break;
        }
    }

    private void patrolUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.patrolUnit(x, y);
        switch (message) {
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case BLOCK:
                System.out.println("You can not move to that cell!");
                break;
            case ALREADY_IN_DESTINATION:
                System.out.println("Your armies already is in this location!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case SUCCESS:
                System.out.println("Successful!");
                break;
        }
    }

    private void stopUnitsPatrol() {
        UnitControllerMessages message = unitController.stopUnitsPatrol();
        switch (message) {
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case NOT_PATROL:
                System.out.println("Selected units aren't patrolling!");
                break;
            case SUCCESS:
                System.out.println("Patrolling of selected units has been stopped");
                break;
        }
    }

    private void setStateForUnits(Matcher matcher) {
        String state = matcher.group("state");
        UnitControllerMessages message = unitController.setStateForUnits(state);
        switch (message) {
            case SUCCESS:
                System.out.println("Successfully set!");
                break;
            case INVALID_STATE:
                System.out.println("Invalid state!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
        }
    }

    private void attackEnemy(Matcher matcher) {
        int enemyX = Integer.parseInt(matcher.group("enemyX"));
        int enemyY = Integer.parseInt(matcher.group("enemyY"));
        UnitControllerMessages message = unitController.attackEnemy(enemyX, enemyY);
        switch (message) {
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case NOT_ENEMY:
                System.out.println("There aren't any enemy in that cell!");
                break;
            case SUCCESS:
                System.out.println("Your armies will attack that enemies!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case BLOCK:
                System.out.println("You can not move to that cell!");
                break;
            case ALREADY_IN_DESTINATION:
                System.out.println("Your armies already is in this location!");
                break;
            case OUT_OF_RANGE:
                System.out.println("Enemy is out of range!");
                break;
        }
    }

    private void archerAttack(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        UnitControllerMessages message = unitController.archerAttack(x, y);
        switch (message) {
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case INVALID_LOCATION:
                System.out.println("You entered invalid location!");
                break;
            case SUCCESS:
                System.out.println("Your archers will attack that enemies!");
                break;
            case NOT_ARCHER:
                System.out.println("You can not attack this cell with this units!");
                break;
            case OUT_OF_RANGE:
                System.out.println("Enemy is out of range!");
                break;
        }
    }

    private void stop() {
        UnitControllerMessages message = unitController.stop();
        switch (message) {
            case SUCCESS:
                System.out.println("The selected units have been stopped successfully!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
        }
    }

    private void pourOil(Matcher matcher) {
        String direction = matcher.group("direction");
        UnitControllerMessages message = unitController.pourOil(direction);
        switch (message) {
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case NOT_SELECT_OIL:
                System.out.println("You didn't select engineer with oil!");
                break;
            case INVALID_DIRECTION:
                System.out.println("You entered invalid direction!");
                break;
            case CAN_NOT_POUR_OIL:
                System.out.println("You can not pour oil in that direction!");
                break;
            case SUCCESS:
                System.out.println("The oil was successfully poured!");
                break;
        }
    }

    private void digTunnel() {
        UnitControllerMessages message = unitController.digTunnel();
        switch (message) {
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case IRRELEVANT_UNIT:
                System.out.println("You can not dig tunnel with this unit!");
                break;
            case SUCCESS:
                System.out.println("Tunnel has been dug!");
                break;
            case BUILDING_NOT_FOUND:
                System.out.println("There is no building to destroy!");
                break;
        }
    }

    private void buildEquipment(Matcher matcher) {
        String equipmentType = MainController.removeDoubleQuotation(matcher.group("equipmentType"));
        UnitControllerMessages message = unitController.buildEquipment(equipmentType);
        switch (message) {
            case SUCCESS:
                System.out.println("Tent has been set up");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case NOT_SELECTED_ENGINEER:
                System.out.println("You didn't select engineer!");
                break;
            case INVALID_TYPE:
                System.out.println("You entered invalid type of equipment!");
                break;
        }
    }

    private void disbandUnit() {
        UnitControllerMessages message = unitController.disbandUnit();
        switch (message) {
            case SUCCESS:
                System.out.println("Selected units successfully disband!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
        }
    }

    private void digMoat(Matcher matcher) {
        String direction = matcher.group("direction");
        UnitControllerMessages message = unitController.digMoat(direction);
        switch (message) {
            case SUCCESS:
                System.out.println("Moat is digging!");
                break;
            case INVALID_DIRECTION:
                System.out.println("You entered invalid direction!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case IRRELEVANT_UNIT:
                System.out.println("You can not dig moat with these units!");
                break;
            case CANNOT_BUILD:
                System.out.println("You can't dig moat here!");
                break;
        }
    }

    private void removeMoat(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        BuildingControllerMessages message = buildingController.removeMoat(x, y);
        switch (message) {
            case LOCATION_OUT_OF_BOUNDS:
                System.out.println("You entered invalid location!");
                break;
            case NO_MOATS_HERE:
                System.out.println("There isn't any moat!");
                break;
            case NOT_OWNER:
                System.out.println("You're not the owner of this building!");
                break;
            case SUCCESS:
                System.out.println("Moat has been removed successfully!");
                break;
        }
    }

    private void fillMoat(Matcher matcher) {
        String direction = matcher.group("direction");
        UnitControllerMessages message = unitController.fillMoat(direction);
        switch (message) {
            case INVALID_DIRECTION:
                System.out.println("You entered invalid location!");
                break;
            case NULL_SELECTED_UNIT:
                System.out.println("You didn't select any unit!");
                break;
            case IRRELEVANT_UNIT:
                System.out.println("You can not fill moat with this unit!");
                break;
            case MOAT_DOES_NOT_EXIST:
                System.out.println("There is not ant moat!");
                break;
            case SUCCESS:
                System.out.println("Moat has been filled successfully!");
                break;
        }
    }
}