package controller;

import controller.functionalcontrollers.Pair;
import javafx.stage.Stage;
import model.Kingdom;
import model.User;
import model.UserInfo;
import model.databases.Database;
import model.databases.GameDatabase;
import model.map.Map;
import view.menus.login.*;
import view.menus.main.MainMenu;
import view.menus.profile.*;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.EventListener;

public class AppController {
    private static final Database DATABASE = new Database();
    private static User loggedInUser;
    private static GameDatabase gameDatabase;
    private static MenusName currentMenu;
    private final Database database;
    private Stage stage;
    private UserInfo userInfo;
    private User currentUser;
    private ArrayList<PropertyChangeListener> updateListeners;

    public AppController(Stage stage) {
        currentMenu = MenusName.LOGIN_MENU;
        this.stage = stage;
        this.database = new Database();
        this.updateListeners = new ArrayList<>();
    }

    public static MenusName getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(MenusName currentMenu) {
        AppController.currentMenu = currentMenu;
        DATABASE.saveDataIntoFile();
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        AppController.loggedInUser = loggedInUser;
    }

    public void saveUserInfo(String username, String password, String nickname, String slogan, String email) {
        userInfo = new UserInfo(username, password, nickname, slogan, email);
    }

    public void saveUserRecovery(int questionNumber, String answer) {
        userInfo.setRecovery(new Pair<>(questionNumber, MainController.getSHA256(answer)));
    }

    public void saveUser() {
        database.addUser(userInfo.toUser());
        database.saveDataIntoFile();
    }

    public void setCurrentUser(String username) {
        currentUser = database.getUserByUsername(username);
        if (currentUser == null) System.out.println("not found");
    }

    public void setCurrentUserPassword(String password) {
        database.getUserByUsername(currentUser.getUsername()).changePasswords(MainController.getSHA256(password));
        database.saveDataIntoFile();
    }

    public void addListener(PropertyChangeListener listener) {
        this.updateListeners.add(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        this.updateListeners.remove(listener);
    }

    public void updateListeners(String name) {
        for (PropertyChangeListener updateListener : this.updateListeners)
            updateListener.propertyChange(new PropertyChangeEvent(this, name, 1, 1));
    }

    public static void makeNewGameDatabase(ArrayList<Kingdom> kingdoms, Map map) {
        gameDatabase = new GameDatabase(kingdoms, map);
    }


    public void run(MenusName currentMenu) throws Exception {
        DATABASE.loadDataFromFile();
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

    public Controller getControllerForMenu(ControllersName name) {
        switch (name) {
            case LOGIN:
                return new LoginController(database);
            case REGISTER:
                return new RegisterController(database);
            case PROFILE:
                return new ProfileController(database, database.getUserByUsername(currentUser.getUsername()));
        }
        return null;
    }

    private void checkLoggedInUSer() {
        loggedInUser = DATABASE.getStayedLoggedInUser();
//        if (database.getStayedLoggedInUser() != null) setCurrentMenu(MenusName.MAIN_MENU);
    }

    public void saveData() {
        database.saveDataIntoFile();
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
