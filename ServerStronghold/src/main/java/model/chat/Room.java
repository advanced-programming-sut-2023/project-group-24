package model.chat;

import model.User;

import java.util.Vector;

public class Room extends Chat {
    private Vector<User> users;

    public Room(String id, Vector<User> users) {
        super(id);
        this.users = users;
    }

    public Vector<User> getUsers() {
        return users;
    }
}
