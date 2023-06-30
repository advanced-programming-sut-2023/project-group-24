package model.databases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;
import model.chat.Chat;
import model.chat.PrivateChat;
import model.chat.PublicChat;
import model.chat.Room;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ChatDatabase {
    private Vector<User> users;
    private PublicChat publicChat;
    private Vector<Room> rooms;
    private Vector<PrivateChat> privateChats;

    public ChatDatabase() {
        ChatDatabase chatDatabase = loadChatDatabase();
        if (chatDatabase != null) {
            this.users = chatDatabase.users;
            this.publicChat = chatDatabase.publicChat;
            this.rooms = chatDatabase.rooms;
            this.privateChats = chatDatabase.privateChats;
        } else {
            this.users = new Vector<>();
            this.publicChat = new PublicChat();
            this.rooms = new Vector<>();
            this.privateChats = new Vector<>();
        }
    }

    public ChatDatabase loadChatDatabase() {
        File file = new File("info/chatDatabase.json");
        if (file.exists()) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            try {
                return gson.fromJson(new FileReader(file), this.getClass());
            } catch (Exception e) {
                return null;
            }
        } else return null;
    }

    public void saveData() {
        File file = new File("info/chatDatabase.json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(this);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PublicChat getPublicChat() {
        return publicChat;
    }

    public Vector<Room> getRooms() {
        return rooms;
    }

    public Vector<PrivateChat> getPrivateChats() {
        return privateChats;
    }

    public Vector<Chat> getAllChats(User user) {
        Vector<Chat> output = new Vector<>();
        output.add(publicChat);
        for (PrivateChat privateChat : privateChats)
            if (privateChat.getUser1().equals(user) || privateChat.getUser2().equals(user)) output.add(privateChat);
        for (Room room : rooms)
            if (room.getUsers().contains(user)) output.add(room);
        return output;
    }

    public Chat getChatById(User currentUser, String id) {
        if (id.equals("publicChat")) return publicChat;
        for (Room room : rooms) if (room.getId().equals(id) && room.getUsers().contains(currentUser)) return room;
        for (PrivateChat privateChat : privateChats)
            if (privateChat.getId().equals(id + ":" + currentUser.getUsername())
                    || privateChat.getId().equals(currentUser.getUsername() + ":" + id)) return privateChat;
        return null;
    }

    public Chat getChatById(String id) {
        if (id.equals("publicChat")) return publicChat;
        for (Room room : rooms) if (room.getId().equals(id)) return room;
        return null;
    }

    public User getUserFromUsername(String username) {
        for (User user : users) if (user.getUsername().equals(username)) return user;
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
