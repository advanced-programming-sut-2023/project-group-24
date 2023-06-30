package controller.nongame;

import controller.Controller;
import javafx.scene.image.Image;
import model.Packet;
import model.User;
import model.chat.Chat;
import model.chat.Message;
import model.databases.ChatDatabase;
import model.databases.Database;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class ChatController implements Controller {
    private Database database;
    private ChatDatabase chatDatabase;
    private Socket socket;
    private ArrayList<Socket> sockets;
    private User currentUser;
    private Chat currentChat;

    public ChatController(Database database, ChatDatabase chatDatabase,
                          User currentUser, Socket socket, ArrayList<Socket> sockets) {
        this.database = database;
        this.chatDatabase = chatDatabase;
        this.currentUser = currentUser;
        this.socket = socket;
        this.sockets = sockets;
        currentChat = chatDatabase.getPublicChat();
    }

    public Vector<Message> readAllMessages() {
        if (currentChat == null) return null;
        currentChat.readMessages(currentUser);
        return currentChat.getMessages();
    }

    public void sendMessage(String message) {
        if (currentChat == null) return;
        LocalDateTime now = LocalDateTime.now();
        Message message1 = new Message(currentUser.getUsername(), now.getHour(), now.getMinute(), message);
        currentChat.addMessage(message1);
    }

    public void edit(Message message, String text) {
        message.setMessage(text);
    }

    public void handlePacket(Packet packet) {
        switch (packet.getSubject()) {
            case "send message":
                System.out.println(packet.getArgs()[0]);
                currentChat = chatDatabase.getChatById(packet.getArgs()[0]);
                sendMessage(packet.getValue());
                sendDataToAllSockets(new Packet("chat", "send message",
                        new String[]{currentUser.getUsername(), currentChat.getId()}, packet.getValue()));
                break;
            case "edit":
                currentChat = chatDatabase.getChatById(packet.getArgs()[1]);
                Message message = currentChat.getMessages().get(Integer.parseInt(packet.getArgs()[0]));
                edit(message, packet.getValue());
                sendDataToAllSockets(packet);
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
