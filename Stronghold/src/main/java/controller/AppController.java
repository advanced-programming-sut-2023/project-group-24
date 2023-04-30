package controller;

import controller.gamecontrollers.*;
import model.databases.Database;
import model.databases.GameDatabase;
import model.User;
import model.map.Map;
import utils.enums.MenusName;
import view.menus.gamemenus.GameMenu;
import view.menus.gamemenus.ShopMenu;
import view.menus.gamemenus.ShowMapMenu;
import view.menus.gamemenus.TradeMenu;
import view.menus.*;

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
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        AppController.loggedInUser = loggedInUser;
    }

    public static void makeNewGameDatabase(ArrayList<User> players, Map map) {
        gameDatabase = new GameDatabase(players, map);
    }

    public void run() {
        database.loadDataFromFile();
        checkLoggedInUSer();
        while (true) {
            switch (currentMenu) {
                case LOGIN_MENU:
                    LoginMenuController loginMenuController = new LoginMenuController(database);
                    LoginMenu loginMenu = new LoginMenu(loginMenuController);
                    loginMenu.run();
                    break;
                case PROFILE_MENU:
                    ProfileMenuController profileMenuController = new ProfileMenuController(database);
                    ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
                    profileMenu.run();
                    break;
                case REGISTER_MENU:
                    RegisterMenuController registerMenuController = new RegisterMenuController(database);
                    RegisterMenu registerMenu = new RegisterMenu(registerMenuController);
                    registerMenu.run();
                    break;
                case RESET_PASSWORD_MENU:
                    ResetPasswordMenuController resetPasswordMenuController = new ResetPasswordMenuController(database);
                    ResetPasswordMenu resetPasswordMenu = new ResetPasswordMenu(resetPasswordMenuController);
                    resetPasswordMenu.run();
                case MAIN_MENU:
                    MainMenuController mainMenuController = new MainMenuController(database);
                    MainMenu mainMenu = new MainMenu(mainMenuController);
                    mainMenu.run();
                    break;
                case GAME_MENU: {
                    KingdomController kingdomController = new KingdomController(gameDatabase);
                    UnitController unitController = new UnitController(gameDatabase);
                    BuildingController buildingController = new BuildingController(gameDatabase);
                    GameMenu gameMenu = new GameMenu(kingdomController, unitController, buildingController);
                    gameMenu.run();
                    break;
                }
                case SHOW_MAP_MENU:
                    ShowMapController showMapController = new ShowMapController(gameDatabase);
                    ShowMapMenu showMapMenu = new ShowMapMenu(showMapController);
                    showMapMenu.run();
                    break;
                case TRADE_MENU:
                    TradeController tradeController = new TradeController(gameDatabase);
                    KingdomController kingdomController = new KingdomController(gameDatabase);
                    TradeMenu tradeMenu = new TradeMenu(tradeController, kingdomController);
                    tradeMenu.run();
                    break;
                case SHOP_MENU:
                    ShopController shopController = new ShopController(gameDatabase);
                    ShopMenu shopMenu = new ShopMenu(shopController);
                    shopMenu.run();
                    break;
                case CREATE_MAP_MENU:
                    CreateMapController createMapController = new CreateMapController(database);
                    CreateMapMenu createMapMenu = new CreateMapMenu(createMapController);
                    createMapMenu.run();
            }
        }
    }

    private void checkLoggedInUSer() {
        loggedInUser = database.getStayedLoggedInUser();
        if (database.getStayedLoggedInUser() != null) setCurrentMenu(MenusName.MAIN_MENU);
    }

}
