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
import model.chat.Reaction;
import model.databases.ChatDatabase;
import model.databases.Database;
import view.enums.messages.CommonMessages;
import view.modelview.MessageBox;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Vector<Message> messages = new Vector<>();
        for (Message message : currentChat.getMessages())
            if (!message.toString(currentUser).equals(""))
                messages.add(message);
        return messages;
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
        Packet packet;
        if (currentChat instanceof PrivateChat)
            packet = new Packet("chat", "send message",
                    new String[]{((PrivateChat) currentChat).getId(currentUser)}, message);
        else packet = new Packet("chat", "send message", new String[]{currentChat.getId()}, message);
        System.out.println(packet.getArgs()[0]);
        System.out.println(currentChat.getClass());
        System.out.println(currentChat.getId());
        System.out.println(currentChat instanceof PrivateChat);
        System.out.println(((PrivateChat) currentChat).getId(currentUser));
        ioHandler.sendPacket(packet);
    }

    public String getChatName() {
        if (currentChat instanceof PrivateChat) return ((PrivateChat) currentChat).getId(currentUser);
        return currentChat.getId();
    }

    public String getChatName(Chat chat) {
        if (chat instanceof PrivateChat) return ((PrivateChat) chat).getId(currentUser);
        return chat.getId();
    }

    public void edit(Message message, String text) {
        if (!message.getSenderUsername().equals(currentUser.getUsername())) return;
        message.setMessage(text);
        Packet packet = new Packet("chat", "edit",
                new String[]{String.valueOf(currentChat.getMessages().indexOf(message)), currentChat.getId()}, text);
        ioHandler.sendPacket(packet);
    }

    public void deleteForMe(Message message) {
        message.addToBannedList(currentUser);
        Packet packet = new Packet("chat", "delete for me",
                new String[]{String.valueOf(currentChat.getMessages().indexOf(message)), currentChat.getId()}, "");
        ioHandler.sendPacket(packet);
    }

    public void deleteForAll(Message message) {
        message.remove();
        Packet packet = new Packet("chat", "delete for all",
                new String[]{String.valueOf(currentChat.getMessages().indexOf(message)), currentChat.getId()}, "");
        ioHandler.sendPacket(packet);
    }

    public void react(Message message, int i) {
        for (Reaction reaction : message.getReactions())
            if (reaction.getReactorUsername().equals(currentUser.getUsername())) {
                message.getReactions().remove(reaction);
                break;
            }
        message.getReactions().add(new Reaction(currentUser.getUsername(), i));
        Packet packet = new Packet("chat", "react", new String[]{String.valueOf(currentChat.getMessages().indexOf(message)),
                currentChat.getId(), String.valueOf(i), currentUser.getUsername()}, "");
        ioHandler.sendPacket(packet);
    }

    public boolean newPrivateChat(String text) {
        User user = database.getUserByUsername(text);
        if (user == null) return false;
        for (PrivateChat privateChat : chatDatabase.getPrivateChats())
            if (privateChat.getId(currentUser).equals(text)) return false;
        PrivateChat privateChat = new PrivateChat(
                currentUser.getUsername() + ":" + user.getUsername(), currentUser, user);
        chatDatabase.getPrivateChats().add(privateChat);
        Packet packet = new Packet("chat", "new private chat", new String[] {currentUser.getUsername(), text}, "");
        ioHandler.sendPacket(packet);
        return true;
    }

    public Vector<Chat> getAllChats() {
        return chatDatabase.getAllChats(currentUser);
    }

    public void selectChat(String chat) {
        Chat selectedChat = chatDatabase.getChatById(currentUser, chat);
        if (selectedChat != null) currentChat = selectedChat;
    }
}
