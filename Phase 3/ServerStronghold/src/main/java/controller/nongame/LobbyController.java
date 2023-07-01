package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Lobby;
import model.Packet;
import model.User;
import model.databases.LobbyDatabase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyController {

    private LobbyDatabase lobbyDatabase;
    private Socket socket;
    private ArrayList<Socket> sockets;
    private User currentUser;

    public LobbyController(LobbyDatabase lobbyDatabase, Socket socket, ArrayList<Socket> sockets, User currentUser) {
        this.currentUser = currentUser;
        this.lobbyDatabase = lobbyDatabase;
        this.socket = socket;
        this.sockets = sockets;
    }

    private void addLobby(Lobby lobby) {
        lobbyDatabase.addLobby(lobby.getUsers().get(0), lobby.getCapacity(), lobby.getId(), lobby.isPrivateLobby());
    }

    private void removeLobby(int index) {
        lobbyDatabase.removeLobby(index);
    }

    private void changeAdmin(int index) {
        lobbyDatabase.getLobbies().get(index).changeAdmin();
    }

    public void handlePacket(Packet packet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        switch (packet.getSubject()) {
            case "addLobby":
                addLobby(gson.fromJson(packet.getValue(), Lobby.class));
                sendDataToAllSockets(new Packet("lobbyDatabase", "addLobby", null, packet.getValue()));
                break;
            case "removeLobby":
                removeLobby(Integer.parseInt(gson.fromJson(packet.getValue(), String.class)));
                sendDataToAllSockets(new Packet("lobbyDatabase", "removeLobby", null, packet.getValue()));
                break;
            case "changeAdmin":
                changeAdmin(Integer.parseInt(gson.fromJson(packet.getValue(), String.class)));
                sendDataToAllSockets(new Packet("lobbyDatabase", "changeAdmin", null, packet.getValue()));
            case "addUser":
                addUser(Integer.parseInt(gson.fromJson(packet.getValue(), String.class)));
                sendDataToAllSockets(new Packet("lobbyDatabase", "addUser", new String[]{currentUser.getUsername()}, packet.getValue()));
                break;
            case "changeState":
                changeState(Integer.parseInt(gson.fromJson(packet.getValue(), String.class)));
                sendDataToAllSockets(new Packet("lobbyDatabase", "changeState", null, packet.getValue()));
                break;
            case "removeUser":
                removeUser(Integer.parseInt(gson.fromJson(packet.getValue(), String.class)));
                sendDataToAllSockets(new Packet("lobbyDatabase", "removeUser", new String[]{currentUser.getUsername()}, packet.getValue()));
                break;
        }
    }

    private void removeUser(int index) {
        Lobby lobby = lobbyDatabase.getLobbies().get(index);
        lobby.removeUser(currentUser);
        if (lobby.getSize() == 0)
            removeLobby(index);
        else
            lobby.changeAdmin();
    }

    private void changeState(int index) {
        lobbyDatabase.getLobbies().get(index).changeState();
    }

    private void addUser(int index) {
        lobbyDatabase.getLobbies().get(index).addUser(currentUser);
    }

    public void sendDataToAllSockets(Packet packet) {
        if (sockets == null) return;
        for (Socket socket : sockets) {
            if (socket.equals(this.socket)) continue;
            try {
                DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
                stream.writeUTF(packet.toJson());
            } catch (IOException e) {
//                System.out.println("couldn't send data to " + socket.getInetAddress() + ":" + socket.getPort());
            }
        }
    }
}
