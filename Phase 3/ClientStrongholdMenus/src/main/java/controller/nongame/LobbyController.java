package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import controller.InputOutputHandler;
import model.Lobby;
import model.Packet;
import model.User;
import model.databases.LobbyDatabase;

import java.util.ArrayList;

public class LobbyController implements Controller {

    private final InputOutputHandler ioHandler;
    private final LobbyDatabase lobbyDatabase;
    private final User currentUser;

    public LobbyController(InputOutputHandler ioHandler, LobbyDatabase lobbyDatabase, User currentUser) {
        this.currentUser = currentUser;
        this.ioHandler = ioHandler;
        this.lobbyDatabase = lobbyDatabase;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbyDatabase.getLobbies();
    }

    public ArrayList<String> getRandomLobbies() {
        return lobbyDatabase.getRandomLobbies();
    }

    public Lobby getPrivateLobby(String id) {
        for (Lobby lobby : lobbyDatabase.getLobbies()) {
            if (lobby.isPrivateLobby() && lobby.getId().equals(id)) return lobby;
        }
        return null;
    }

    public Lobby getCurrentLobby() {
        return lobbyDatabase.getCurrentLobby();
    }

    public void setCurrentLobby(String admin) {
        lobbyDatabase.setCurrentLobby(lobbyDatabase.getLobby(admin));
    }

    public void addLobby(int capacity, boolean privateLobby, String id) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        lobbyDatabase.addLobby(currentUser, capacity, id, privateLobby);
        Packet packet = new Packet("lobbyDatabase", "addLobby",
                null, gson.toJson(lobbyDatabase.getLobbies().get(lobbyDatabase.getLobbies().size() - 1)));
        ioHandler.sendPacket(packet);
    }

    public void addUser(Lobby lobby) {
        if (lobby.getSize() == lobby.getCapacity()) return;
        lobby.addUser(currentUser);
        Packet packet = new Packet("lobbyDatabase", "addUser", null, String.valueOf(lobbyDatabase.getLobbies().indexOf(lobby)));
        ioHandler.sendPacket(packet);
    }

    public void removeUser(Lobby lobby) {
        int index = lobbyDatabase.getLobbies().indexOf(lobby);
        lobby.removeUser(currentUser.getUsername());
        Packet packet;
        packet = new Packet("lobbyDatabase", "removeUser", null, String.valueOf(index));
        ioHandler.sendPacket(packet);
        if (lobby.getAdmin().equals(currentUser)) {
            if (lobby.changeAdmin()) return;
            else lobbyDatabase.removeLobby(lobbyDatabase.getLobbies().indexOf(lobby));
        }
    }

    public void changeState(Lobby lobby) {
        int index = lobbyDatabase.getLobbies().indexOf(lobby);
        lobby.changeState();
        Packet packet = new Packet("lobbyDatabase", "changeState", null, String.valueOf(index));
        ioHandler.sendPacket(packet);
    }
}
