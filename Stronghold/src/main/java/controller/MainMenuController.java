package controller;

import model.databases.Database;
import view.enums.messages.MainMenuMessages;

public class MainMenuController {

    Database database;

    public MainMenuController(Database database) {
        this.database = database;
    }

    public MainMenuMessages enterGameMenu(String[] opponentsUsername) {
        //TODO connect to game controller
        return null;
    }

}
