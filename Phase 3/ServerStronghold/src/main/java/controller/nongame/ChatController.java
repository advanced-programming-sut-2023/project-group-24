package controller.nongame;

import controller.Controller;
import model.Packet;
import model.User;
import model.chat.*;
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
        Message message;
        switch (packet.getSubject()) {
            case "read":
                System.out.println("entered here");
                currentChat = chatDatabase.getChatById(currentUser, packet.getArgs()[0]);
                Packet packet2;
                if (currentChat instanceof PrivateChat) packet2 = new Packet("chat", "read",
                        new String[]{currentUser.getUsername(), ((PrivateChat) currentChat).getId(currentUser)},
                        packet.getValue());
                else packet2 = new Packet("chat", "read",
                        new String[]{currentUser.getUsername(), currentChat.getId()}, packet.getValue());
                sendDataToAllSockets(packet2);
                break;
            case "send message":
                currentChat = chatDatabase.getChatById(currentUser, packet.getArgs()[0]);
                sendMessage(packet.getValue());
                Packet packet1;
                if (currentChat instanceof PrivateChat) packet1 = new Packet("chat", "send message",
                        new String[]{currentUser.getUsername(), ((PrivateChat) currentChat).getId(currentUser)},
                        packet.getValue());
                else packet1 = new Packet("chat", "send message",
                        new String[]{currentUser.getUsername(), currentChat.getId()}, packet.getValue());
                sendDataToAllSockets(packet1);
                break;
            case "edit":
                currentChat = chatDatabase.getChatById(packet.getArgs()[1]);
                message = currentChat.getMessages().get(Integer.parseInt(packet.getArgs()[0]));
                edit(message, packet.getValue());
                sendDataToAllSockets(packet);
                break;
            case "delete for me":
                currentChat = chatDatabase.getChatById(packet.getArgs()[1]);
                message = currentChat.getMessages().get(Integer.parseInt(packet.getArgs()[0]));
                message.addToBannedList(currentUser);
                break;
            case "delete for all":
                currentChat = chatDatabase.getChatById(packet.getArgs()[1]);
                message = currentChat.getMessages().get(Integer.parseInt(packet.getArgs()[0]));
                message.remove();
                sendDataToAllSockets(packet);
                break;
            case "react":
                currentChat = chatDatabase.getChatById(packet.getArgs()[1]);
                message = currentChat.getMessages().get(Integer.parseInt(packet.getArgs()[0]));
                for (Reaction reaction : message.getReactions())
                    if (reaction.getReactorUsername().equals(packet.getArgs()[3])) {
                        message.getReactions().remove(reaction);
                        break;
                    }
                message.getReactions().add(new Reaction(packet.getArgs()[3], Integer.parseInt(packet.getArgs()[2])));
                sendDataToAllSockets(packet);
                break;
            case "new private chat":
                User user = database.getUserByUsername(packet.getArgs()[1]);
                PrivateChat privateChat = new PrivateChat(
                        currentUser.getUsername() + ":" + user.getUsername(), currentUser, user);
                chatDatabase.getPrivateChats().add(privateChat);
                sendDataToAllSockets(packet);
                break;
            case "new room":
                Vector<User> users = new Vector<>();
                users.add(currentUser);
                String[] args = packet.getArgs();
                for (int i = 0; i < args.length - 1; i++) users.add(database.getUserByUsername(args[1 + i]));
                chatDatabase.getRooms().add(new Room(args[0], users));
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
