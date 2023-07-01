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

    public Pair<Integer, String> getRecovery() {
        return recovery;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getEmail() {
        return email;
    }

    public User toUser() {
        return new User(username, MainController.getSHA256(password), nickname, slogan, email, recovery);
    }
}
