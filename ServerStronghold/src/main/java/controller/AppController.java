package controller;

import controller.network.NodeController;
import model.databases.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AppController {
    private ServerSocket serverSocket;
    private Database database;
    
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
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection form: " + socket.getInetAddress() + ":" + socket.getPort());
                new NodeController(socket, database).start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
