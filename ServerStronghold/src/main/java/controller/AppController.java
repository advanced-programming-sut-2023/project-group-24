package controller;

import controller.network.NodeController;
import model.databases.Database;
import org.w3c.dom.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AppController {
    private ServerSocket serverSocket;
    private Database database;
    private ArrayList<Socket> sockets;
    
    public AppController() {
        try {
            this.serverSocket = new ServerSocket(1717);
            this.database = new Database();
        } catch (IOException e) {
            System.out.println("Could not make the serverSocket");
            e.printStackTrace();
        }
    }

    public void start() {
        sockets = new ArrayList<>();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection form: " + socket.getInetAddress() + ":" + socket.getPort());
                sockets.add(socket);
                new NodeController(socket, database, sockets).start();

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
