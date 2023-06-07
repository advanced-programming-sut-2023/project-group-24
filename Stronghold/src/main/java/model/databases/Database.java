package model.databases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.functionalcontrollers.Pair;
import model.User;
import model.map.Map;

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
    private static final String FILE_TO_SAVE_ALL_USERS = DIRECTORY_TO_SAVE_INFO + "/allUsers.json";
    private static final String FILE_TO_SAVE_STAYED_LOGGED_IN_USER = DIRECTORY_TO_SAVE_INFO + "/loggedInUser.json";

    private Vector<User> allUsers;
    private User stayedLoggedInUser;
    private Vector<Map> maps;

    public Database() {
        allUsers = new Vector<>();
        stayedLoggedInUser = null;
        maps = new Vector<>();
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

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(gson.toJson(object));
            fileWriter.flush();
        } catch (IOException ignored) {

        }
    }

    public User getStayedLoggedInUser() {
        return stayedLoggedInUser;
    }

    public void setStayedLoggedInUser(User user) {
        stayedLoggedInUser = user;
        try {
            checkForSavingDirectory();
        } catch (IOException ignored) {

        }
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
        Type allUsersType = new TypeToken<Vector<User>>(){}.getType();

        try {
            allUsers = gson.fromJson(fileToString(FILE_TO_SAVE_ALL_USERS), allUsersType);
            stayedLoggedInUser = gson.fromJson(fileToString(FILE_TO_SAVE_STAYED_LOGGED_IN_USER), User.class);
        } catch (FileNotFoundException ignored) {
            allUsers = new Vector<>();
            maps = new Vector<>();
            stayedLoggedInUser = null;
        }
        if (allUsers == null) allUsers = new Vector<>();
    }

    public void saveDataIntoFile() {
        try {
            checkForSavingDirectory();
            saveObjectToFile(FILE_TO_SAVE_ALL_USERS, allUsers);
            saveObjectToFile(FILE_TO_SAVE_STAYED_LOGGED_IN_USER, stayedLoggedInUser);
        }
        catch (IOException ignored) {
            System.out.println("error");
        }
    }

    private void checkForSavingDirectory() throws IOException {
        File directory = new File(DIRECTORY_TO_SAVE_INFO);
        if (directory.mkdirs())
            throw new IOException("couldn't make directory");
    }

    public User getUserByUsername(String username) {
        if (allUsers == null) return null;
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

    public void addUser(User user) {
        allUsers.add(user);
    }

    public Map getMapById(String id) {
        for (Map map : maps) {
            if (map.getId().equals(id))
                return map;
        }
        return null;
    }

    public void addMap(Map map) {
        maps.add(map);
    }

    public boolean mapIdExists(String id) {
        for (Map map : maps) {
            if (map.getId().equals(id)) return true;
        }
        return false;
    }
}
