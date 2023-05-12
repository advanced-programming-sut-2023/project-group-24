package view.menus;

import controller.AppController;
import controller.MainMenuController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;

public class MainMenu {

    MainMenuController mainMenuController;

    public MainMenu(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void run() {
        String command;
        while (AppController.getCurrentMenu() == MenusName.MAIN_MENU) {
            command = GetInputFromUser.getUserInput();
            if (Commands.getMatcher(command, Commands.LOGOUT) != null)
                enterLoginMenu();
            else if (Commands.getMatcher(command, Commands.ENTER_PROFILE_MENU) != null)
                enterProfileMenu();
            else if (Commands.getMatcher(command, Commands.ENTER_CREATE_MAP) != null)
                enterCreateMapMenu();
            else if (Commands.getMatcher(command, Commands.START_GAME) != null)
                enterGameMenu();
            else
                System.out.println("Invalid command!");
        }
    }

    private void enterProfileMenu() {
        AppController.setCurrentMenu(MenusName.PROFILE_MENU);
    }

    private void enterGameMenu() {
        ArrayList<String> usernames = new ArrayList<>();
        System.out.print("Please enter map id: ");
        String mapId = GetInputFromUser.getUserInput();
        int numberOfPlayers = mainMenuController.numberOfPlayerInMap(mapId);
        if (numberOfPlayers == -1) {
            System.out.println("Invalid map id!");
            return;
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter username" + (i + 1) + " : ");
            usernames.add(GetInputFromUser.getUserInput());
        }
        MainMenuMessages message = mainMenuController.enterGameMenu(usernames, mapId);
        switch (message) {
            case INVALID_USERNAME -> System.out.println("Invalid username!");
            case SUCCESS -> {
                System.out.println("Game started!");
                AppController.setCurrentMenu(MenusName.GAME_MENU);
            }
        }
    }

    private void enterCreateMapMenu() {
        AppController.setCurrentMenu(MenusName.CREATE_MAP_MENU);
        System.out.println("You are in create map menu!");
    }

    private void enterLoginMenu() {
        AppController.setCurrentMenu(MenusName.LOGIN_MENU);
        System.out.println("User successfully logged out!\nYou are in login menu!");
    }
}
