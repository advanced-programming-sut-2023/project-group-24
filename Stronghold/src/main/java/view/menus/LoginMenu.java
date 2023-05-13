package view.menus;

import controller.AppController;
import controller.LoginMenuController;
import controller.MainController;
import controller.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.LoginMenuMessages;

import java.util.regex.Matcher;

public class LoginMenu {

    private final LoginMenuController loginMenuController;


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
            else if (Commands.getMatcher(command, Commands.SHOW_CURRENT_MENU) != null)
                System.out.println("Login menu");
            else
                System.out.println("Invalid command!");
        }
    }

    private void loginUser(Matcher matcher) {
        String username = MainController.removeDoubleQuotation(matcher.group("username"));
        String password = MainController.removeDoubleQuotation(matcher.group("password"));
        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
        LoginMenuMessages message = loginMenuController.loginUser(username, password, stayLoggedIn);
        switch (message) {
            case SUCCESS -> {
                System.out.println("user logged in successfully!\nNow you're in main menu!");
                enterMainMenu();
            }
            case USER_NOT_FOUND -> System.out.println("User not found!");
            case INCORRECT_PASSWORD -> {
                System.out.println("Incorrect password!\nYou have to wait for a while");
                loginMenuController.makeDelayForIncorrectPassword();
                System.out.println("Now you can enter the commands");
            }
            case INCORRECT_CAPTCHA -> System.out.println("You entered the captcha code incorrectly!");
        }
    }

    private void enterResetPasswordMenu() {
        AppController.setCurrentMenu(MenusName.RESET_PASSWORD_MENU);
        System.out.println("You are in reset password menu now!");
    }

    private void enterRegisterMenu() {
        AppController.setCurrentMenu(MenusName.REGISTER_MENU);
        System.out.println("Now you can sign up!");
    }

    private void enterMainMenu() {
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
    }
}
