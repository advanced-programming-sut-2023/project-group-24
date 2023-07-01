package model.chat;

import model.User;

public class PrivateChat extends Chat {
    private User user1;
    private User user2;

    public PrivateChat(String id, User user1, User user2) {
        super(id);
        this.user1 = user1;
        this.user2 = user2;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public String getId(User user) {
        if (user.equals(user1)) return user2.getUsername();
        else if (user.equals(user2)) return user1.getUsername();
        else return getId();
    }
}
