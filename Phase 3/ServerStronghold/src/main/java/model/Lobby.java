package model;

import java.util.ArrayList;

public class Lobby {
    private final ArrayList<User> users;
    private String id;
    private final int capacity;
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean isPrivateLobby() {
        return privateLobby;
    }

    public void changeState() {
        privateLobby = !privateLobby;
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

    public void removeUser(User user) {
        users.remove(user);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void changeAdmin() {
        admin = users.get(0);
    }
}
