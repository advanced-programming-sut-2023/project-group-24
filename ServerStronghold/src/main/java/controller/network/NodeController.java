package controller.network;

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
}
