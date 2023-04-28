package controller;

import model.User;
import model.databases.Database;
import utils.enums.MenusName;
import view.enums.messages.LoginMenuMessages;
import view.menus.CaptchaMenu;
import view.menus.GetInputFromUser;

import java.util.Timer;
import java.util.TimerTask;

public class LoginMenuController {
    private final Database database;
    private int numberOfIncorrectPassword;

    public LoginMenuController(Database database) {
        this.database = database;
        this.numberOfIncorrectPassword = 0;
    }

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        User user = database.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USER_NOT_FOUND;
        else if (!user.isPasswordCorrect(MainController.getSHA256(password))) {
            numberOfIncorrectPassword++;
            return LoginMenuMessages.INCORRECT_PASSWORD;
        }
        numberOfIncorrectPassword = 0;
        if (!CaptchaMenu.runCaptcha()) return LoginMenuMessages.INCORRECT_CAPTCHA;
        if (stayLoggedIn) database.setStayedLoggedInUser(user);
        AppController.setLoggedInUser(user);
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
        return LoginMenuMessages.SUCCESS;
    }

    public void makeDelayForIncorrectPassword() {
        Timer timer = new Timer();
        int delayTime = (numberOfIncorrectPassword * 5) * 1000;
        long start = System.currentTimeMillis();
        long end = start + delayTime;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, end);
    }
}
