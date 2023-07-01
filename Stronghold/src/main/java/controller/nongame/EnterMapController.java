package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import model.User;
import model.databases.Database;
import model.map.Map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EnterMapController implements Controller {
    private Database database;
    private ArrayList<User> users;
    private Map map;

    public EnterMapController(Database database, User currentUser) {
        this.database = database;
        this.users = new ArrayList<>();
        users.add(currentUser);
    }

    public boolean mapIdExists(String mapId) {
        return database.getMapById(mapId) != null;
    }

    public boolean isInvalidUser(String username) {
        User user = database.getUserByUsername(username);
        return user == null || users.contains(user) || map.getKingdoms().size() == users.size();
    }

    public boolean addUser(String username) {
        if (isInvalidUser(username)) return false;
        users.add(database.getUserByUsername(username));
        return true;
    }

    public String getAvatarPath(String username) {
        return database.getCurrentAvatarPath(database.getUserByUsername(username));
    }

    public boolean startGame() {
        if (map == null || users.size() != map.getKingdoms().size()) return false;

        String directory;
        if (new File("").getAbsolutePath().endsWith("Stronghold")) directory = "../";
        else directory = "./";

        File saveMap = new File(directory + "map.json");
        File saveUsers = new File(directory + "users.json");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        try {
            FileWriter fileWriterMap = new FileWriter(saveMap.getAbsolutePath());
            FileWriter fileWriterUsers = new FileWriter(saveUsers.getAbsolutePath());
            fileWriterMap.write(gson.toJson(map));
            fileWriterMap.flush();
            fileWriterUsers.write(gson.toJson(users));
            fileWriterUsers.flush();
            ProcessBuilder builder = new ProcessBuilder("run game.bat");
            builder.start();
            return true;
        } catch (IOException e) {
            System.out.println("save failed");
            e.printStackTrace();
            return false;
        }
    }

    public void selectMap(String mapId) {
        this.map = database.getMapById(mapId);
    }
}
