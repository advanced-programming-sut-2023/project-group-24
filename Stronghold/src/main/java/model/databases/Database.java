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
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {
    private static final String DIRECTORY_TO_SAVE_INFO = "info";
    private static final String FILE_TO_SAVE_ALL_USERS = DIRECTORY_TO_SAVE_INFO + "/allUsers.json";
    private static final String FILE_TO_SAVE_A_MAP = DIRECTORY_TO_SAVE_INFO + "/map1.json";
    private static final String FILE_TO_SAVE_STAYED_LOGGED_IN_USER = DIRECTORY_TO_SAVE_INFO + "/loggedInUser.json";

    private Vector<User> allUsers;
    private User stayedLoggedInUser;
    private Vector<Map> maps;

    public Database() {
        loadDataFromFile();
    }

    private synchronized static String fileToString(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine())
            builder.append(scanner.nextLine()).append('\n');
        return builder.toString();
    }

    private synchronized static void saveObjectToFile(String filePath, Object object) {
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
        Vector<User> users = new Vector<>(allUsers);
        Collections.sort(users);
        return users;
    }

    public synchronized void loadDataFromFile() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type allUsersType = new TypeToken<Vector<User>>() {}.getType();

        try {
            allUsers = gson.fromJson(fileToString(FILE_TO_SAVE_ALL_USERS), allUsersType);
            stayedLoggedInUser = gson.fromJson(fileToString(FILE_TO_SAVE_STAYED_LOGGED_IN_USER), User.class);
            maps = new Vector<>();
        } catch (FileNotFoundException ignored) {
            allUsers = new Vector<>();
            maps = new Vector<>();
            stayedLoggedInUser = null;
        }
        if (allUsers == null) allUsers = new Vector<>();
    }

    public synchronized void saveDataIntoFile() {
        try {
            checkForSavingDirectory();
            saveObjectToFile(FILE_TO_SAVE_ALL_USERS, allUsers);
            saveObjectToFile(FILE_TO_SAVE_STAYED_LOGGED_IN_USER, stayedLoggedInUser);
            if (maps.size() > 0) saveObjectToFile(FILE_TO_SAVE_A_MAP, maps.get(0));
        } catch (IOException ignored) {
            System.out.println("error");
        }
    }

    private synchronized void checkForSavingDirectory() throws IOException {
        File directory = new File(DIRECTORY_TO_SAVE_INFO);
        if (directory.mkdirs()) throw new IOException("couldn't make directory");
    }

    public synchronized User getUserByUsername(String username) {
        if (allUsers == null) return null;
        for (User user : allUsers) if (user.getUsername().equals(username)) return user;
        return null;
    }

    public synchronized void addUser(String username, String password,
                        String nickname, String slogan, String email,
                        Pair<Integer, String> recovery) {
        User newUser = new User(username, password, nickname, slogan, email, recovery);
        allUsers.add(newUser);
    }

    public synchronized void addUser(User user) {
        allUsers.add(user);
    }

    public synchronized Map getMapById(String id) {
        for (Map map : maps) {
            if (map.getId().equals(id))
                return map;
        }
        return null;
    }

    public synchronized void addMap(Map map) {
        maps.add(map);
    }

    public synchronized boolean mapIdExists(String id) {
        for (Map map : maps) {
            if (map.getId().equals(id)) return true;
        }
        return false;
    }

    public synchronized String[] getAvatarsPathsForUser(User user) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        if (path.mkdirs()) return new String[0];
        File[] files = path.listFiles();
        if (files == null) return new String[0];
        String[] output = new String[files.length - 1];
        try {
            for (int i = 1; i < files.length; i++) {
                Matcher matcher = Pattern.compile("(?<number>\\d+)\\.png").matcher(files[i].getName());
                if (!matcher.find()) System.out.println("hi");
                output[Integer.parseInt(matcher.group("number")) - 1] = files[i].toURI().toURL().toExternalForm();
            }
            return output;
        } catch (MalformedURLException ignored) {
            return new String[0];
        }
    }

    public synchronized String getCurrentAvatarPath(User user) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        File pic = new File(path.getAbsoluteFile() + "/0.png");
        if (path.mkdirs() || !pic.exists()) {
            try {
                for (int i = 0; i < 6; i++)
                    Files.copy(
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

    public synchronized void addAvatarPicture(User user, String path) {
        File dir = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        File dest = new File(dir.getAbsolutePath() + "/" + Objects.requireNonNull(dir.listFiles()).length +
                ".png");
        try {
            Files.copy(new File(path).toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void setCurrentAvatar(User user, URI path) {
        setCurrentAvatar(user, new File(path));
    }

    public synchronized void setCurrentAvatar(User user, File path) {
        File dest = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars" +
                "/0.png");
        if (dest.exists()) if (!dest.delete()) return;
        try {
            Files.copy(path.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getAvatarNumber(User user) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");
        File current = new File(path.getAbsolutePath() + "/0.png");

        return getAvatarNumber(path, current);
    }

    public synchronized int getAvatarNumber(User user, File target) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(user) + "/avatars");

        return getAvatarNumber(path, target);
    }

    private synchronized int getAvatarNumber(File path, File target) {
        try {
            for (int i = 1; i < Objects.requireNonNull(path.listFiles()).length; i++) {
                if (FileUtils.contentEquals(new File(path.getAbsolutePath() + "/" + i + ".png"), target))
                    return i;
            }
        } catch (IOException ignored) {
            System.out.println("Error in get avatar number");
        }
        return -1;
    }

    public synchronized void copyAvatar(User to, User from) {
        File path = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(to) + "/avatars");
        File current = new File(path.getAbsolutePath() + "/0.png");
        File nextAvatar = new File(DIRECTORY_TO_SAVE_INFO + "/allUsers/" + allUsers.indexOf(from) +
                "/avatars/0.png");

        try {
            int index = getAvatarNumber(path, nextAvatar);
            if (index == -1) addAvatarPicture(to, nextAvatar.getAbsolutePath());
            if (current.exists()) if (!current.delete()) return;
            Files.copy(nextAvatar.toPath(), current.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
