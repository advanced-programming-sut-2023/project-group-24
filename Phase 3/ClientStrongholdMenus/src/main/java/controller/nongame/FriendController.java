package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import controller.InputOutputHandler;
import javafx.scene.image.Image;
import model.Friendship;
import model.Packet;
import model.User;
import model.databases.Database;

import java.util.Vector;

public class FriendController implements Controller {
    private final Database database;
    private User currentUser;
    private InputOutputHandler ioHandler;


    public FriendController(InputOutputHandler inputOutputHandler, Database database, User currentUser) {
        this.ioHandler = inputOutputHandler;
        this.database = database;
        this.currentUser = currentUser;
    }

    public void sendRequest(Friendship friendship) {
        database.getUserByUsername(friendship.getRequesterName()).getFriends().add(friendship);
        System.out.println(database.getUserByUsername(friendship.getRequesterName()).getFriends().size() + "osla");
        database.getUserByUsername(friendship.getAccepterName()).getFriends().add(friendship);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Packet packet = new Packet("friend", "send", null, gson.toJson(friendship));
        ioHandler.sendPacket(packet);
    }

    public void acceptRequest(Friendship friendship) {
        friendship.setAccept(true);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Packet packet = new Packet("friend", "accept", null, gson.toJson(friendship));
        ioHandler.sendPacket(packet);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void rejectAccept(Friendship friendship) {
        database.getUserByUsername(friendship.getRequesterName()).getFriends().remove(friendship);
        database.getUserByUsername(friendship.getAccepterName()).getFriends().remove(friendship);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Packet packet = new Packet("friend", "reject", null, gson.toJson(friendship));
        ioHandler.sendPacket(packet);
    }

    public Vector<User> getUsers() {
        return database.getAllUsers();
    }


    public Image getAvatar(User selectedUser) {
        return new Image(database.getCurrentAvatarPath(selectedUser));
    }
}
