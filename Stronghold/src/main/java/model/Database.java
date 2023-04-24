package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
    private static final String FILE_TO_SAVE_MAPS = DIRECTORY_TO_SAVE_INFO + "/maps.json";
    private static final String FILE_TO_SAVE_STAYED_LOGGED_IN_USER = DIRECTORY_TO_SAVE_INFO + "/loggedInUser.json";

    private Vector<User> allUsers;
    private Vector<Map> maps;
    private User stayedLoggedInUser;

    public Database() {
        allUsers = new Vector<>();
        maps = new Vector<>();
        stayedLoggedInUser = null;
    }

    public void setStayedLoggedInUser(User user) throws IOException {
        stayedLoggedInUser = user;
        checkForSavingDirectory();
        saveObjectToFile(FILE_TO_SAVE_STAYED_LOGGED_IN_USER, stayedLoggedInUser);
    }

    public User getStayedLoggedInUser() {
        return stayedLoggedInUser;
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
        Type mapsType = new TypeToken<Vector<Map>>(){}.getType();

        try {
            allUsers = gson.fromJson(fileToString(FILE_TO_SAVE_ALL_USERS), allUsersType);
            maps = gson.fromJson(fileToString(FILE_TO_SAVE_MAPS), mapsType);
            stayedLoggedInUser = gson.fromJson(fileToString(FILE_TO_SAVE_STAYED_LOGGED_IN_USER), User.class);
        } catch (FileNotFoundException ignored) {
            allUsers = new Vector<>();
            maps = new Vector<>();
            stayedLoggedInUser = null;
        }
    }

    private static String fileToString(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine())
            builder.append(scanner.nextLine()).append('\n');
        return builder.toString();
    }

    public void saveDataIntoFile() throws IOException {
        checkForSavingDirectory();
        saveObjectToFile(FILE_TO_SAVE_ALL_USERS, allUsers);
        saveObjectToFile(FILE_TO_SAVE_MAPS, maps);
        saveObjectToFile(FILE_TO_SAVE_STAYED_LOGGED_IN_USER, stayedLoggedInUser);
    }

    private static void saveObjectToFile(String filePath, Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(gson.toJson(object));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkForSavingDirectory() throws IOException {
        File directory = new File(DIRECTORY_TO_SAVE_INFO);
        if (!directory.exists()) if (!directory.mkdirs()) throw new IOException("couldn't make directory to save files");
    }

    public User getUserByUsername(String username) {
        for (User user : allUsers)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public void addUser(String username, String password,
                        String nickname, String slogan, String email,
                        int recoveryQuestionNumber, String recoveryAnswer) {
        User newUser = new User(username, password, nickname, slogan, email, recoveryQuestionNumber, recoveryAnswer);
        allUsers.add(newUser);
    }

    public Map getMapById(String id) {
        for (Map map : maps)
            if (map.getId().equals(id))
                return map;
        return null;
    }

    public void addMap(Map map) {
        maps.add(map);
    }
}
