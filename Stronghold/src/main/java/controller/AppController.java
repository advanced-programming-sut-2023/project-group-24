package controller;

import controller.gamecontrollers.*;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.Kingdom;
import model.User;
import model.databases.Database;
import model.databases.GameDatabase;
import model.map.Map;
import view.menus.login.*;
import view.menus.main.MainMenu;
import view.menus.profile.*;
import view.oldmenus.gamemenus.GameMenu;
import view.oldmenus.gamemenus.ShopMenu;
import view.oldmenus.gamemenus.ShowMapMenu;
import view.oldmenus.gamemenus.TradeMenu;

import java.util.ArrayList;

public class AppController {
    private static final Database database = new Database();
    private static User loggedInUser;
    private static GameDatabase gameDatabase;
    private static MenusName currentMenu;
    private Stage stage;

    public AppController(Stage stage) {
        currentMenu = MenusName.LOGIN_MENU;
        this.stage = stage;
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


    public void run(MenusName currentMenu) throws Exception {
        database.loadDataFromFile();
        checkLoggedInUSer();

        switch (currentMenu) {
            case LOGIN_MENU:
                LoginMenu loginMenu = new LoginMenu(this);
                loginMenu.start(stage);
                break;
            case FORGOT_PASSWORD_MENU:
                ForgotPasswordMenu forgotPasswordMenu = new ForgotPasswordMenu(this);
                forgotPasswordMenu.start(stage);
                break;
            case SECURITY_QUESTION_CONFIRM_MENU:
                SecurityQuestionConfirmMenu securityQuestionConfirmMenu = new SecurityQuestionConfirmMenu(this);
                securityQuestionConfirmMenu.start(stage);
                break;
            case REGISTER_MENU:
                RegisterMenu registerMenu = new RegisterMenu(this);
                registerMenu.start(stage);
                break;
            case SECURITY_QUESTION_CHOOSE_MENU:
                SecurityQuestionChooseMenu securityQuestionChooseMenu = new SecurityQuestionChooseMenu(this);
                securityQuestionChooseMenu.start(stage);
                break;
            case CAPTCHA_MENU:
                CaptchaMenu captchaMenu = new CaptchaMenu(this);
                captchaMenu.start(stage);
                break;
            case MAIN_MENU:
                MainMenu mainMenu = new MainMenu(this);
                mainMenu.start(stage);
                break;
            case PROFILE_MENU:
                ProfileMenu profileMenu = new ProfileMenu(this);
                profileMenu.start(stage);
                break;
            case CHANGE_PASSWORD_MENU:
                ChangePasswordMenu changePasswordMenu = new ChangePasswordMenu(this);
                changePasswordMenu.start(stage);
                break;
            case LEADER_BOARD_MENU:
                LeaderBoardMenu leaderBoardMenu = new LeaderBoardMenu(this);
                leaderBoardMenu.start(stage);
                break;
            case CHOOSE_AVATAR_MENU:
                ChooseAvatarMenu chooseAvatarMenu = new ChooseAvatarMenu(this);
                chooseAvatarMenu.start(stage);
                break;
            case COPY_AVATAR_MENU:
                CopyAvatarMenu copyAvatarMenu = new CopyAvatarMenu(this);
                copyAvatarMenu.start(stage);
                break;
        }
//        switch (currentMenu) {
//            case LOGIN_MENU:
//                LoginMe loginMenu = new LoginMenu()
//                break;
//            case PROFILE_MENU:
//                ProfileMenuController profileMenuController = new ProfileMenuController(database);
//                ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
//                profileMenu.run();
//                break;
//            case REGISTER_MENU:
//                RegisterMenuController registerMenuController = new RegisterMenuController(database);
//                RegisterMenu registerMenu = new RegisterMenu(registerMenuController);
//                registerMenu.run();
//                break;
//            case RESET_PASSWORD_MENU:
//                ResetPasswordMenuController resetPasswordMenuController = new ResetPasswordMenuController(database);
//                ResetPasswordMenu resetPasswordMenu = new ResetPasswordMenu(resetPasswordMenuController);
//                resetPasswordMenu.run();
//                break;
//            case MAIN_MENU:
//                MainMenuController mainMenuController = new MainMenuController(database);
//                MainMenu mainMenu = new MainMenu(mainMenuController);
//                mainMenu.run();
//                break;
//            case GAME_MENU: {
//                GameController gameController = new GameController(gameDatabase);
//                KingdomController kingdomController = new KingdomController(gameDatabase);
//                UnitController unitController = new UnitController(gameDatabase);
//                BuildingController buildingController = new BuildingController(gameDatabase);
//                GameMenu gameMenu =
//                        new GameMenu(kingdomController, unitController, buildingController, gameController);
//                gameMenu.run();
//                break;
//            }
//            case SHOW_MAP_MENU:
//                ShowMapController showMapController = new ShowMapController(gameDatabase);
//                ShowMapMenu showMapMenu = new ShowMapMenu(showMapController);
//                showMapMenu.run();
//                break;
//            case TRADE_MENU: {
//                TradeController tradeController = new TradeController(gameDatabase);
//                KingdomController kingdomController = new KingdomController(gameDatabase);
//                TradeMenu tradeMenu = new TradeMenu(tradeController, kingdomController);
//                tradeMenu.run();
//                break;
//            }
//            case SHOP_MENU:
//                ShopController shopController = new ShopController(gameDatabase);
//                KingdomController kingdomController = new KingdomController(gameDatabase);
//                ShopMenu shopMenu = new ShopMenu(shopController, kingdomController);
//                shopMenu.run();
//                break;
//            case CREATE_MAP_MENU:
//                CreateMapController createMapController = new CreateMapController(database);
//                CreateMapMenu createMapMenu = new CreateMapMenu(createMapController);
//                createMapMenu.run();
//                break;
//        }
    }

    public Controller getControllerForMenu(MenusName name) {
        switch (name) {
            case LOGIN_MENU:
                return new LoginController(database);
        }

        return null;
    }

    private void checkLoggedInUSer() {
        loggedInUser = database.getStayedLoggedInUser();
//        if (database.getStayedLoggedInUser() != null) setCurrentMenu(MenusName.MAIN_MENU);
    }

}
