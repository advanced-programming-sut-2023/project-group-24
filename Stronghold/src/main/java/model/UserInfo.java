package model;

import controller.MainController;
import controller.functionalcontrollers.Pair;

public class UserInfo {
    private Pair<Integer, String> recovery;
    private String username;
    private String password;
    private String nickname;
    private String slogan;
    private String email;

    public UserInfo(String username, String password, String nickname, String slogan, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
    }

    public void setRecovery(Pair<Integer, String> recovery) {
        this.recovery = recovery;
    }

    public User toUser() {
        return new User(username, MainController.getSHA256(password), nickname, slogan, email, recovery);
    }
}
