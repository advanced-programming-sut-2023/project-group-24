package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import controller.InputOutputHandler;
import model.Packet;
import model.User;
import model.databases.Database;
import model.map.Map;
import view.controls.Control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EnterMapController implements Controller {
    private final InputOutputHandler inputOutputHandler;
    private ArrayList<User> users;
    private Map map;

    public EnterMapController(InputOutputHandler inputOutputHandler, Control control) {
        this.inputOutputHandler = inputOutputHandler;
        inputOutputHandler.addListener(evt -> control.listener().propertyChange(evt));
    }

    public void mapIdExists(String mapId) {
        Packet packet = new Packet("enter map", "map id exists", null, mapId);
        inputOutputHandler.sendPacket(packet);
    }

    public void isInvalidUser(String username) {
        Packet packet = new Packet("enter map", "is invalid user", null, username);
        inputOutputHandler.sendPacket(packet);
    }

    public void addUser(String username) {
        Packet packet = new Packet("enter map", "add user", null, username);
        inputOutputHandler.sendPacket(packet);
    }

    public String getAvatarPath(String username) {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarPath = new File(path + "info/avatars/" + username + ".png");
        if (avatarPath.exists()) return path + "info/avatars/" + username + ".png";
        return path + "info/avatars/a@a.png";
    }

    public void startGame() {
        if (map == null || users.size() != map.getKingdoms().size()) return;

        Packet packet = new Packet("enter map", "start game", null, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void selectMap(String mapId) {
        Packet packet = new Packet("enter map", "select map", null, mapId);
        inputOutputHandler.sendPacket(packet);
        //TODO select map after it is selected
    }
}
