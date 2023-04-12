package controller;

import model.Database;
import view.enums.RecoveryQuestion;
import view.enums.messages.LoginMenuMessages;
import view.enums.messages.ResetPasswordMessages;

public class ResetPasswordMenuController {

    Database database;

    public ResetPasswordMenuController(Database database) {
        this.database = database;
    }

    public ResetPasswordMessages checkUserUsername(String username) {
        //TODO check answer and if it's correct change pass
        return null;
    }

    public LoginMenuMessages checkUserRecoveryAnswer(String username, String password, String passwordConfirm) {
        //TODO check errors and if it was correct change password
        return null;
    }

    public void changePassword(String newPassword) {
        //TODO change password
    }
}
