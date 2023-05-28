package controller;

import controller.gamecontrollers.*;
import model.Kingdom;
import model.User;
import model.databases.Database;
import model.databases.GameDatabase;
import model.map.Map;
import view.menus.*;
import view.menus.gamemenus.*;

import java.util.ArrayList;

public class AppController {
    private static final Database database = new Database();
    private static User loggedInUser;
    private static GameDatabase gameDatabase;
    private static MenusName currentMenu;

    public AppController() {
        currentMenu = MenusName.LOGIN_MENU;
    }

    public static MenusName getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(MenusName currentMenu) {
        AppController.currentMenu = currentMenu;
        database.saveDataIntoFile();
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        AppController.loggedInUser = loggedInUser;
    }

    public static void makeNewGameDatabase(ArrayList<Kingdom> kingdoms, Map map) {
        gameDatabase = new GameDatabase(kingdoms, map);
    }


    public void run(String[] args) {
        database.loadDataFromFile();
        checkLoggedInUSer();
        while (true) {
            switch (currentMenu) {
                case LOGIN_MENU -> {
                    LoginMenuController loginMenuController = new LoginMenuController(database);
                    LoginMenu loginMenu = new LoginMenu(loginMenuController);
                    loginMenu.run();
                }
                case PROFILE_MENU -> {
                    ProfileMenuController profileMenuController = new ProfileMenuController(database);
                    ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
                    profileMenu.run();
                }
                case REGISTER_MENU -> {
                    RegisterMenuController registerMenuController = new RegisterMenuController(database);
                    RegisterMenu registerMenu = new RegisterMenu(registerMenuController);
                    registerMenu.run();
                }
                case RESET_PASSWORD_MENU -> {
                    ResetPasswordMenuController resetPasswordMenuController = new ResetPasswordMenuController(database);
                    ResetPasswordMenu resetPasswordMenu = new ResetPasswordMenu(resetPasswordMenuController);
                    resetPasswordMenu.run();
                }
                case MAIN_MENU -> {
                    MainMenuController mainMenuController = new MainMenuController(database);
                    MainMenu mainMenu = new MainMenu(mainMenuController);
                    mainMenu.run();
                }
                case GAME_MENU -> {
                    GameController gameController = new GameController(gameDatabase);
                    KingdomController kingdomController = new KingdomController(gameDatabase);
                    UnitController unitController = new UnitController(gameDatabase);
                    BuildingController buildingController = new BuildingController(gameDatabase);
                    GameMenu gameMenu =
                            new GameMenu(kingdomController, unitController, buildingController, gameController);
                    gameMenu.run();
                }
                case SHOW_MAP_MENU -> {
                    ShowMapController showMapController = new ShowMapController(gameDatabase);
                    MiniMap.gameDatabase = gameDatabase;
                    ShowMapMenu showMapMenu = new ShowMapMenu(showMapController, args);
                    showMapMenu.run();
                }
                case TRADE_MENU -> {
                    TradeController tradeController = new TradeController(gameDatabase);
                    KingdomController kingdomController = new KingdomController(gameDatabase);
                    TradeMenu tradeMenu = new TradeMenu(tradeController, kingdomController);
                    tradeMenu.run();
                }
                case SHOP_MENU -> {
                    ShopController shopController = new ShopController(gameDatabase);
                    KingdomController kingdomController = new KingdomController(gameDatabase);
                    ShopMenu shopMenu = new ShopMenu(shopController, kingdomController);
                    shopMenu.run();
                }
                case CREATE_MAP_MENU -> {
                    CreateMapController createMapController = new CreateMapController(database);
                    CreateMapMenu createMapMenu = new CreateMapMenu(createMapController);
                    createMapMenu.run();
                }
            }
        }
    }

    private void checkLoggedInUSer() {
        loggedInUser = database.getStayedLoggedInUser();
        if (database.getStayedLoggedInUser() != null) setCurrentMenu(MenusName.MAIN_MENU);
    }

}
