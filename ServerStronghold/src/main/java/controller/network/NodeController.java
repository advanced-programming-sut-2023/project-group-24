package controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Packet;
import model.User;
import model.databases.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NodeController extends Thread {
    private Database database;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private User user;

    public NodeController(Socket socket, Database database) throws IOException {
        this.database = database;
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
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
    }
}
