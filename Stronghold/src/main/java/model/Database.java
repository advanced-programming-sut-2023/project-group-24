package model;

import model.map.Map;

import java.util.ArrayList;
import java.util.Vector;

public class Database {
    private Vector<User> allUsers;
    private Vector<Map> maps;
    private User stayedLoggedInUser;

    public Database() {
        allUsers = new Vector<>();
    }

    public void setStayedLoggedInUser(User user) {
        stayedLoggedInUser = user;
        //TODO save it in file
    }

    public User getStayedLoggedInUser() {
        return stayedLoggedInUser;
    }

    public void loadDataFromFile() {

    }

    public void saveDataIntoFile() {

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
