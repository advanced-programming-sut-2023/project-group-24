package view.menus;

import controller.AppController;
import controller.MainMenuController;
import utils.enums.MenusName;
import view.enums.commands.Commands;

import java.util.regex.Matcher;

public class MainMenu {

    MainMenuController mainMenuController;

    public MainMenu(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu() == MenusName.MAIN_MENU) {
            command = GetInputFromUser.getUserInput();
            if (Commands.getMatcher(command, Commands.LOGOUT) != null)
                enterLoginMenu();
            else if (Commands.getMatcher(command, Commands.ENTER_PROFILE_MENU) != null)
                enterProfileMenu();
            else if (Commands.getMatcher(command, Commands.ENTER_CREATE_MAP) != null)
                enterCreateMapMenu();
            else if ((matcher = Commands.getMatcher(command, Commands.START_GAME)) != null)
                enterGameMenu(matcher);
        }
    }

    private void enterProfileMenu() {
        AppController.setCurrentMenu(MenusName.PROFILE_MENU);
    }

    private void enterGameMenu(Matcher matcher) {
        int numberOfUser = matcher.groupCount();
        String[] usernames = new String[numberOfUser];
        for (int i = 0; i < numberOfUser; i++) {
            usernames[i] = matcher.group(i);
        }
        mainMenuController.enterGameMenu(usernames);
    }

    private void enterCreateMapMenu() {
        AppController.setCurrentMenu(MenusName.CREATE_MAP_MENU);
        System.out.println("You are in map making menu!");
    }

    private void enterLoginMenu() {
        AppController.setCurrentMenu(MenusName.LOGIN_MENU);
        System.out.println("User successfully logged out!\nYou are in login menu!");
    }
}
