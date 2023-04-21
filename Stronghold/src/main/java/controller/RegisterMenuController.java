package controller;

import model.Database;
import view.menus.messages.RegisterMenuMessages;

public class RegisterMenuController {

    Database database;

    public RegisterMenuController(Database database) {
        this.database = database;
    }

    public RegisterMenuMessages checkErrorsForRegister(String username, String password,
                                                       String passwordConfirmation, String nickname,
                                                       String email, String slogan) {
        //TODO check errors of registering
        return null;
    }

    public RegisterMenuMessages checkErrorsForSecurityQuestion(int securityQuestionNumber, String answer, String answerConfirm) {
        //TODO check that answer is correct and create new user
        return null;
    }

    public String makeRandomPassword() {
        //TODO make random password
        return null;
    }

    public String makeRandomSlogan() {
        //TODO make random slogan
        return null;
    }

    public void registerUser(String username, String password,
                             String nickname, String email,
                             String slogan, int securityQuestionNumber,
                             String securityAnswer) {
        //TODO register new user
    }
}
