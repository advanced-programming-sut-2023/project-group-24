package controller.nongame;

import controller.Controller;
import controller.InputOutputHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import model.Packet;
import model.User;
import model.chat.Chat;
import model.chat.Message;
import model.chat.PrivateChat;
import model.databases.ChatDatabase;
import model.databases.Database;
import view.modelview.MessageBox;

import java.time.LocalDateTime;
import java.util.Vector;

public class ChatController implements Controller {
    private ChatDatabase chatDatabase;
    private Database database;
    private User currentUser;
    private InputOutputHandler ioHandler;
    private Chat currentChat;

    public ChatController(InputOutputHandler ioHandler, Database database, ChatDatabase chatDatabase, User currentUser) {
        this.chatDatabase = chatDatabase;
        this.database = database;
        this.currentUser = currentUser;
        this.ioHandler = ioHandler;
        currentChat = chatDatabase.getPublicChat();
    }

    public Vector<Message> readAllMessages() {
        if (currentChat == null) return null;
        currentChat.readMessages(currentUser);
        return currentChat.getMessages();
    }

    public MessageBox getMessageBox(Message message) {
        MessageBox messageBox = new MessageBox(message);
        messageBox.setImage(new Image(
                database.getCurrentAvatarPath(database.getUserByUsername(message.getSenderUsername()))));
        return messageBox;
    }

    public void sendMessage(String message) {
        if (currentChat == null) return;
        LocalDateTime now = LocalDateTime.now();
        Message message1 = new Message(currentUser.getUsername(), now.getHour(), now.getMinute(), message);
        currentChat.addMessage(message1);
        Packet packet = new Packet("chat", "send message", new String[]{currentChat.getId()}, message);
        ioHandler.sendPacket(packet);
    }

    public String getChatName() {
        if (currentChat instanceof PrivateChat) return ((PrivateChat) currentChat).getId(currentUser);
        return currentChat.getId();
    }

    public void edit(Message message, String text) {
        if (!message.getSenderUsername().equals(currentUser.getUsername())) return;
        message.setMessage(text);
        Packet packet = new Packet("chat", "edit",
                new String[]{String.valueOf(currentChat.getMessages().indexOf(message)), currentChat.getId()}, text);
        ioHandler.sendPacket(packet);
    }
}
