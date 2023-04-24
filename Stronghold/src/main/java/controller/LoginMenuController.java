package controller;

import model.Database;
import view.menus.messages.LoginMenuMessages;

public class LoginMenuController {
    private final Database database;

    public LoginMenuController(Database database) {
        this.database = database;
    }

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        //TODO check user and check captcha and login user
        return null;
    }

    private void makeDelayForIncorrectPassword(int delayTime) {
    }
}
