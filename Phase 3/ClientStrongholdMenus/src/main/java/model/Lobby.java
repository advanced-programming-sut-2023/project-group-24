package model;

import java.util.ArrayList;

public class Lobby {
    private final ArrayList<User> users;
    private final int capacity;
    private String id;
    private User admin;
    private boolean privateLobby;

    public Lobby(User admin, int capacity, String id, boolean privateLobby) {
        this.id = id;
        this.admin = admin;
        this.privateLobby = privateLobby;
        this.capacity = capacity;
        users = new ArrayList<>();
        users.add(admin);
    }

    public User getAdmin() {
        return admin;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean isPrivateLobby() {
        return privateLobby;
    }

    public void changeState() {
        privateLobby = !privateLobby;
        id = "";
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return users.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public void removeUser(String user) {
        for (User user1 : users) {
            if (user.equals(user1.getUsername())) {
                users.remove(user1);
                return;
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean changeAdmin() {
        if (users.size() == 0) return false;
        admin = users.get(0);
        System.out.println(admin.getNickname());
        return true;
    }
}
