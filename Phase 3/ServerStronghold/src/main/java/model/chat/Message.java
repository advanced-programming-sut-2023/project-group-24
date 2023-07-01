package model.chat;

import model.User;

import java.util.Vector;

public class Message {
    private String senderUsername;
    private int hour;
    private int minute;
    private String message;
    private Vector<Reaction> reactions;
    private boolean isSeen;
    private boolean isSent;
    private Vector<String> bannedList;
    private boolean isRemoved;

    public Message(String senderUsername, int hour, int minute, String message) {
        this.senderUsername = senderUsername;
        this.hour = hour;
        this.minute = minute;
        this.message = message;
        this.reactions = new Vector<>();
        this.isSeen = false;
        this.isSent = true;
        this.bannedList = new Vector<>();
        this.isRemoved = false;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Vector<Reaction> getReactions() {
        return reactions;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public void addToBannedList(User user) {
        bannedList.add(user.getUsername());
    }

    public String toString(User user) {
        if (isRemoved || bannedList.contains(user.getUsername())) return "";
        return hour + ":" + minute + "|" + senderUsername + ": " + message + "|" + reactions + "|" + isSeen;
    }

    public void remove() {
        this.isRemoved = true;
    }
}
