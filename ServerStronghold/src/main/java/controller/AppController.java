package controller;

import controller.network.NodeController;
import model.databases.ChatDatabase;
import model.databases.Database;
import org.w3c.dom.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AppController {
    private ServerSocket serverSocket;
    private Database database;
    private ChatDatabase chatDatabase;
    private ArrayList<Socket> sockets;
    
    public AppController() {
        try {
            this.serverSocket = new ServerSocket(1717);
            this.database = new Database();
            this.chatDatabase = new ChatDatabase();
            saveDatabases();
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
                new NodeController(socket, database, chatDatabase, sockets).start();

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void saveDatabases() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                chatDatabase.saveData();
                database.saveDataIntoFile();
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 5000, 5000);
    }
}
