package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Friendship;
import model.Packet;
import model.databases.Database;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class FriendShipController {
    private final Database database;
    private Socket socket;
    private ArrayList<Socket> sockets;

    public FriendShipController(Database database, Socket socket, ArrayList<Socket> sockets) {
        this.database = database;
        this.socket = socket;
        this.sockets = sockets;
    }

    public void sendRequest(Friendship friendship) {
        database.getUserByUsername(friendship.getRequesterName()).getFriends().add(friendship);
        database.getUserByUsername(friendship.getAccepterName()).getFriends().add(friendship);
        database.saveDataIntoFile();
    }

    public void acceptRequest(Friendship friendship) {
        friendship.setAccept(true);
        database.saveDataIntoFile();
    }

    public void rejectAccept(Friendship friendship) {
        database.getUserByUsername(friendship.getRequesterName()).getFriends().remove(friendship);
        database.getUserByUsername(friendship.getAccepterName()).getFriends().remove(friendship);
        database.saveDataIntoFile();
    }

    public void handlePacket(Packet packet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        switch (packet.getSubject()) {
            case "send":
                sendRequest(gson.fromJson(packet.getValue(), Friendship.class));
                sendDataToAllSockets(new Packet("friend", "send",null ,packet.getValue()));
                break;
            case "accept":
                acceptRequest(gson.fromJson(packet.getValue(), Friendship.class));
                sendDataToAllSockets(new Packet("friend", "accept",null ,packet.getValue()));
                break;
            case "reject":
                rejectAccept(gson.fromJson(packet.getValue(), Friendship.class));
                sendDataToAllSockets(new Packet("friend", "reject",null ,packet.getValue()));
                break;
        }
    }

    public void sendDataToAllSockets(Packet packet) {
        if (sockets == null) return;
        for (Socket socket : sockets) {
            if (socket.equals(this.socket)) continue;
            try {
                DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
                stream.writeUTF(packet.toJson());
                System.out.println("data sent to " + socket.getPort());
            } catch (IOException e) {
                System.out.println("couldn't send data to " + socket.getInetAddress() + ":" + socket.getPort());
            }
        }
    }

}
