package controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.nongame.RegisterController;
import model.Packet;
import model.User;
import model.databases.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
            e.printStackTrace();
        }

        while (true) {
            try {
                String data = inputStream.readUTF();
                Packet packet = gson.fromJson(data, Packet.class);
                handlePacket(packet);
            } catch (IOException e) {
                System.out.println("disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
                e.printStackTrace();
                break;
            }
        }
    }

    private void handlePacket(Packet packet) {
        switch (packet.getTopic()) {
            case "register":
                RegisterController registerController = new RegisterController(database, socket, sockets);
                registerController.handlePacket(packet);
        }
    }
}
