package controller;

import model.Database;
import model.User;
import utils.enums.MenusName;
import view.enums.messages.LoginMenuMessages;
import view.menus.CaptchaMenu;

import java.io.IOException;

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
        if (stayLoggedIn) {
            try {
                database.setStayedLoggedInUser(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        AppController.setLoggedInUser(user);
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
        return LoginMenuMessages.SUCCESS;
    }

    public void makeDelayForIncorrectPassword() {
        int delayTime = (numberOfIncorrectPassword * 5) * 1000;
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
