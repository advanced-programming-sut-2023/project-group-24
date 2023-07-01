package model.chat;

import model.User;

import java.util.Vector;

public class Chat {
    private String id;
    private Vector<Message> messages;

    public Chat(String id) {
        this.id = id;
        this.messages = new Vector<>();
    }

    public String getId() {
        return id;
    }

    public Vector<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public void readMessages(User user) {
        for (Message message : messages)
            if (!message.getSenderUsername().equals(user.getUsername())) message.setSeen(true);
    }
}
