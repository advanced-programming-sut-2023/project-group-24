package view.menus;

import controller.AppController;
import controller.LoginMenuController;
import view.enums.messages.LoginMenuMessages;
import view.menus.commands.Commands;

import java.util.regex.Matcher;

public class LoginMenu {

    LoginMenuController loginMenuController;

    public LoginMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu() == MenusName.LOGIN_MENU) {
            command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.LOGIN_USER)) != null)
                loginUser(matcher);
            else if (Commands.getMatcher(command, Commands.FORGOT_PASSWORD) != null)
                enterResetPasswordMenu();
            else if (Commands.getMatcher(command, Commands.ENTER_REGISTER_MENU) != null)
                enterRegisterMenu();
            else
                System.out.println("Invalid command!");
        }
    }

    private void loginUser(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
        LoginMenuMessages message = loginMenuController.loginUser(username, password, stayLoggedIn);
        switch (message) {
            case SUCCESS -> {
                System.out.println("user logged in successfully!");
                enterMainMenu();
            }
            case USER_NOT_FOUND -> System.out.println("User not found!");
            case INCORRECT_PASSWORD -> {
                System.out.println("Incorrect password!\nYou have to wait for a while");
                loginMenuController.makeDelayForIncorrectPassword();
            }
            case INCORRECT_CAPTCHA -> System.out.println("You entered the captcha code incorrectly!");
        }
    }

    private void enterResetPasswordMenu() {
        AppController.setCurrentMenu(MenusName.RESET_PASSWORD_MENU);
    }

    private void enterRegisterMenu() {
        AppController.setCurrentMenu(MenusName.REGISTER_MENU);
    }

    private void enterMainMenu() {
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
    }
}
