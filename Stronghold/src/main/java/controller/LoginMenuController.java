package controller;

import model.Database;
import view.enums.messages.LoginMenuMessages;

public class LoginMenuController {
    private Database database;

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
