package view.menus;

import controller.AppController;
import controller.LoginMenuController;

import java.util.regex.Matcher;

public class LoginMenu {

    LoginMenuController loginMenuController;

    public LoginMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    public void run() {
        while (AppController.getCurrentMenu() == MenusName.LOGIN_MENU) {
            //TODO get input from getCommands func and check type of input
        }
    }

    private void loginUser(Matcher matcher) {
        //TODO connect it to controller and print errors and enter mainMenu
    }

    private void enterResetPasswordMenu() {
        //TODO connect controller and set currentMenu resetPassMenu
    }
}
