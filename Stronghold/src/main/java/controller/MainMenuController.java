package controller;

import model.User;
import model.databases.Database;
import model.map.Map;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;

public class MainMenuController {

    Database database;

    public MainMenuController(Database database) {
        this.database = database;
    }

    public int numberOfPlayerInMap(String mapId) {
        if (database.getMapById(mapId) == null) return -1;
        return database.getMapById(mapId).getKingdoms().size();
    }

    public MainMenuMessages enterGameMenu(ArrayList<String> usernames, String mapId) {
        for (String e : usernames) {
            if (database.getUserByUsername(e) == null)
                return MainMenuMessages.INVALID_USERNAME;
        }
        Map map = database.getMapById(mapId);
        for (int i = 0; i < usernames.size(); i++) {
            User user = database.getUserByUsername(usernames.get(i));
            map.getKingdoms().get(i).setOwner(user);
        }
        AppController.makeNewGameDatabase(map.getKingdoms(), map);
        return MainMenuMessages.SUCCESS;
    }

}
