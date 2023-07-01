package controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.nongame.ChatController;
import controller.nongame.LoginController;
import controller.nongame.ProfileController;
import controller.nongame.RegisterController;
import model.Packet;
import model.User;
import model.databases.ChatDatabase;
import model.databases.Database;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class NodeController extends Thread {
    private Database database;
    private ChatDatabase chatDatabase;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private User user;
    private ArrayList<Socket> sockets;

    public NodeController(Socket socket, Database database, ChatDatabase chatDatabase, ArrayList<Socket> sockets)
            throws IOException {
        this.database = database;
        this.chatDatabase = chatDatabase;
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.sockets = sockets;
    }

    @Override
    public void run() {
        manageDatabase();

        while (true) {
            try {
                String data = inputStream.readUTF();
                Packet packet = Packet.fromJson(data);
                handlePacket(packet);
            } catch (IOException e) {
                System.out.println("disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
                if (user != null) {
                    user.setOnline(false);
                    user.setLastSessionToNow();
                }
                break;
            }
        }
    }

    private void manageDatabase() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Packet databasePacket = new Packet("app", "database", null, gson.toJson(database));
        Packet chatDatabasePacket = new Packet("app", "chat database", null, gson.toJson(chatDatabase));
        try {
            outputStream.writeUTF(databasePacket.toJson());
            outputStream.writeUTF(chatDatabasePacket.toJson());
            System.out.println("database sent");
        } catch (IOException e) {
            System.out.println("disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
            if (user != null) {
                user.setOnline(false);
                user.setLastSessionToNow();
            }
        }
    }

    private synchronized void handlePacket(Packet packet) {
        if (packet.getSubject().equals("login")) {
            user = database.getUserByUsername(packet.getValue());
            user.setOnline(true);
            System.out.println("user " + user.getUsername() + " logged in from port: " + socket.getPort());
            sendDataToAllSockets(new Packet("database", "login", null, user.getUsername()));
            return;
        }
        switch (packet.getTopic()) {
            case "register":
                RegisterController registerController = new RegisterController(database, socket, sockets);
                registerController.handlePacket(packet);
                break;
            case "profile":
                ProfileController profileController = new ProfileController(database, user, socket, sockets);
                profileController.handlePacket(packet);
                break;
            case "login":
                LoginController loginController = new LoginController(database, socket, sockets);
                loginController.handlePacket(packet);
                break;
            case "chat":
                ChatController chatController = new ChatController(database, chatDatabase, user, socket, sockets);
                chatController.handlePacket(packet);
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
            } catch (IOException e) {
                System.out.println("couldn't send data to " + socket.getInetAddress() + ":" + socket.getPort());
            }
        }
    }
}
