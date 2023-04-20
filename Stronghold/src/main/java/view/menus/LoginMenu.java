package view.menus;

import controller.AppController;
import controller.LoginMenuController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.LoginMenuMessages;

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
            case SUCCESS:
                System.out.println("user logged in successfully!");
                break;
            case USER_NOT_FOUND:
                System.out.println("User not found!");
                break;
            case INCORRECT_PASSWORD:
                System.out.println("Incorrect password!\nYou have to wait for a while");
                loginMenuController.makeDelayForIncorrectPassword();
                break;
            case INCORRECT_CAPTCHA:
                System.out.println("You input incorrect captcha code!");
            default:
                break;
        }
    }

    private void enterResetPasswordMenu() {
        AppController.setCurrentMenu(MenusName.RESET_PASSWORD_MENU);
    }
}
