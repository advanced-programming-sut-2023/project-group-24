package view.menus;

import controller.RegisterMenuController;

import java.util.regex.Matcher;

public class RegisterMenu {

    private RegisterMenuController registerMenuController;

    public RegisterMenu(RegisterMenuController registerMenuController) {
        this.registerMenuController = registerMenuController;
    }

    public void run() {
        //TODO get input from user and check type of input
    }

    private void checkRegisterErrors(Matcher matcher) {
        //TODO connect it to controller and print errors
    }

    private String getRecoveryQuestion() {
        //TODO type questions and get question from another function and pass it to controller to check errors
        return null;
    }

    private void registerUser(String username, String password,
                              String nickname, String email,
                              String slogan, int securityQuestionNumber,
                              String securityAnswer) {
        //TODO call controller and register user
    }
}
