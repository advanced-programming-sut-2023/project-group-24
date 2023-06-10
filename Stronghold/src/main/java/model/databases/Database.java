package model.databases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.functionalcontrollers.Pair;
import model.User;
import model.map.Map;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
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
        loadDataFromFile();
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
        Type allUsersType = new TypeToken<Vector<User>>() {
        }.getType();

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
        } catch (IOException ignored) {
            System.out.println("error");
        }
    }

    private void checkForSavingDirectory() throws IOException {
        File directory = new File(DIRECTORY_TO_SAVE_INFO);
        if (directory.mkdirs()) throw new IOException("couldn't make directory");
    }

    public User getUserByUsername(String username) {
        if (allUsers == null) return null;
        for (User user : allUsers) if (user.getUsername().equals(username)) return user;
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

    public String[] getAvatarsPathsForUser(User user) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        if (path.mkdirs()) return new String[0];
        File[] files = path.listFiles();
        if (files == null) return new String[0];
        String[] output = new String[files.length - 1];
        try {
            for (int i = 0; i < output.length; i++) output[i] = files[i + 1].toURI().toURL().toExternalForm();
            return output;
        } catch (MalformedURLException ignored) {
            return new String[0];
        }
    }

    public String getCurrentAvatarPath(User user) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        File pic = new File(path.getAbsoluteFile() + "/0.png");
        if (path.mkdirs() || !pic.exists()) {
            try {
                for (int i = 0; i < 6; i++) Files.copy(
                        Paths.get(getClass().getResource("/images/avatars/" + i + ".png").toURI()),
                            new File(path.getAbsolutePath() + "/" + (i + 1) + ".png").toPath());
                Files.copy(Paths.get(getClass().getResource("/images/avatars/0.png").toURI()),
                        new File(path.getAbsolutePath() + "/0.png").toPath());
            } catch (IOException | URISyntaxException ignored) {
                System.out.println("Error while copying avatars info the user's folder");
            }
            return getClass().getResource("/images/avatars/0.png").toExternalForm();
        }
        try {
            return pic.toURI().toURL().toExternalForm();
        } catch (MalformedURLException ignored) {
            return getClass().getResource("/images/avatars/0.png").toExternalForm();
        }
    }

    public void addAvatarPicture(User user, String path) {
        File dir = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        File dest = new File(dir.getAbsolutePath() + "/" + Objects.requireNonNull(dir.listFiles()).length +
                ".png");
        try {
            Files.copy(new File(path).toPath(), dest.toPath());
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public void setCurrentAvatar(User user, URI path) {
        File dest = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars" +
                "/0.png");
        if (dest.exists()) if (!dest.delete()) return;
        try {
            Files.copy(new File(path).toPath(), dest.toPath());
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public int getAvatarNumber(User user) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        File current = new File(path.getAbsolutePath() + "/0.png");

        try {
            for (int i = 1; i < Objects.requireNonNull(path.listFiles()).length; i++) {
                if (FileUtils.contentEquals(new File(path.getAbsolutePath() + "/" + i + ".png"), current))
                    return i;
            }
        } catch (IOException ignored) {
            System.out.println("Error in get avatar number");
        }
        return -1;
    }
}
