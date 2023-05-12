package model.databases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;
import model.map.Map;
import utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Database {
    private static final String DIRECTORY_TO_SAVE_INFO = "info";
    private static final String DIRECTORY_TO_SAVE_MAPS = DIRECTORY_TO_SAVE_INFO + "/maps";
    private static final String FILE_TO_SAVE_ALL_USERS = DIRECTORY_TO_SAVE_INFO + "/allUsers.json";
    private static final String FILE_TO_SAVE_MAP_IDS = DIRECTORY_TO_SAVE_INFO + "/maps.json";
    private static final String FILE_TO_SAVE_STAYED_LOGGED_IN_USER = DIRECTORY_TO_SAVE_INFO + "/loggedInUser.json";

    private Vector<User> allUsers;
    private Vector<String> mapIds;
    private User stayedLoggedInUser;

    public Database() {
        allUsers = new Vector<>();
        mapIds = new Vector<>();
        stayedLoggedInUser = null;
    }

    private static String fileToString(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine())
            builder.append(scanner.nextLine()).append('\n');
        return builder.toString();
    }

    private static void saveObjectToFile(String filePath, Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(gson.toJson(object));
            file.flush();
        } catch (IOException ignored) {

        }
    }

    public User getStayedLoggedInUser() {
        return stayedLoggedInUser;
    }

    public void setStayedLoggedInUser(User user) {
        stayedLoggedInUser = user;
        checkForSavingDirectory();
        saveObjectToFile(FILE_TO_SAVE_STAYED_LOGGED_IN_USER, stayedLoggedInUser);
    }

    public Vector<User> getAllUsers() {
        return allUsers;
    }

    public Vector<User> getAllUsersByRank() {
        Collections.sort(allUsers);
        return allUsers;
    }

    public void loadDataFromFile() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type allUsersType = new TypeToken<Vector<User>>() {}.getType();
        Type mapsType = new TypeToken<Vector<String>>() {}.getType();

        try {
            allUsers = gson.fromJson(fileToString(FILE_TO_SAVE_ALL_USERS), allUsersType);
            mapIds = gson.fromJson(fileToString(FILE_TO_SAVE_MAP_IDS), mapsType);
            stayedLoggedInUser = gson.fromJson(fileToString(FILE_TO_SAVE_STAYED_LOGGED_IN_USER), User.class);
        } catch (FileNotFoundException ignored) {
            allUsers = new Vector<>();
            mapIds = new Vector<>();
            stayedLoggedInUser = null;
        }
    }

    public void saveDataIntoFile() {
        checkForSavingDirectory();
        saveObjectToFile(FILE_TO_SAVE_ALL_USERS, allUsers);
        saveObjectToFile(FILE_TO_SAVE_MAP_IDS, mapIds);
        saveObjectToFile(FILE_TO_SAVE_STAYED_LOGGED_IN_USER, stayedLoggedInUser);
    }

    private void checkForSavingDirectory() {
        File directory = new File(DIRECTORY_TO_SAVE_INFO);
        File maps = new File(DIRECTORY_TO_SAVE_MAPS);
        if (!directory.exists()) {
            directory.mkdirs();
            maps.mkdirs();
        }
    }

    public User getUserByUsername(String username) {
        for (User user : allUsers)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public void addUser(String username, String password,
                        String nickname, String slogan, String email,
                        Pair<Integer, String> recovery) {
        User newUser = new User(username, password, nickname, slogan, email, recovery);
        allUsers.add(newUser);
    }

    public Map getMapById(String id) {
        if (!mapIds.contains(id)) return null;

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        try {
            String content = fileToString(FILE_TO_SAVE_ALL_USERS);
            return gson.fromJson(content, Map.class);
        } catch (FileNotFoundException ignored) {
            return null;
        }
    }

    public void addMap(Map map) {
        mapIds.add(map.getId());
        checkForSavingDirectory();
        saveObjectToFile(DIRECTORY_TO_SAVE_MAPS + "/" + map.getId() + ".json", map);
    }

    public boolean mapIdExists(String id) {
        return mapIds.contains(id);
    }
}
