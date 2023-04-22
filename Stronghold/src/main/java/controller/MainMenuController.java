package controller;

import model.Database;
import model.User;
import view.menus.messages.MainMenuMessages;

public class MainMenuController {

    Database database;

    public MainMenuController(Database database) {
        this.database = database;
    }

    public MainMenuMessages enterProfileMenu() {
        return null;
    }

    public MainMenuMessages enterGameMenu(User[] opponents) {
        return null;
    }

    public MainMenuMessages enterCreateMapMenu() {
        return null;
    }
}
