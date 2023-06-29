package controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.nongame.LoginController;
import controller.nongame.ProfileController;
import controller.nongame.RegisterController;
import model.Packet;
import model.User;
import model.databases.Database;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class NodeController extends Thread {
    private Database database;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private User user;
    private ArrayList<Socket> sockets;

    public NodeController(Socket socket, Database database, ArrayList<Socket> sockets) throws IOException {
        this.database = database;
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.sockets = sockets;
    }

    @Override
    public void run() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Packet databasePacket = new Packet("app", "database", null, gson.toJson(database));
        try {
            outputStream.writeUTF(databasePacket.toJson());
            System.out.println("database sent");
        } catch (IOException e) {
            System.out.println("disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
            if (user != null) {
                user.setOnline(false);
                user.setLastSessionToNow();
            }
        }

        while (true) {
            try {
                String data = inputStream.readUTF();
                Packet packet = gson.fromJson(data, Packet.class);
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

    private synchronized void handlePacket(Packet packet) {
        if (packet.getSubject().equals("login")) {
            user = database.getUserByUsername(packet.getValue());
            System.out.println("user " + user.getUsername() + " logged in from port: " + socket.getPort());
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
        }
    }
}
