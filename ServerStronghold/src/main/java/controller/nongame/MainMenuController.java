package controller.nongame;

import controller.AppController;
import model.User;
import model.databases.Database;
import model.map.Map;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;

public class MainMenuController {

    private Database database;

    public MainMenuController(Database database) {
        this.database = database;
    }

    public int numberOfPlayerInMap(String mapId) {
        if (database.getMapById(mapId) == null) return -1;
        return database.getMapById(mapId).getKingdoms().size();
    }

    public MainMenuMessages checkDuplicationOfUsername(String newUsername, ArrayList<String> usernames) {
        if (usernames.contains(newUsername))
            return MainMenuMessages.DUPLICATE_USERNAME;
        return MainMenuMessages.SUCCESS;
    }
}
