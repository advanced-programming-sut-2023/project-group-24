package model;

import controller.functionalcontrollers.Pair;

public class User implements Comparable<User> {
    private final Pair<Integer, String> recovery;
    private String username;
    private String password;
    private String nickname;
    private String slogan;
    private String email;
    private int highScore;

    public User(String username, String password, String nickname, String slogan,
                String email, Pair<Integer, String> recovery) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.recovery = recovery;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

    public void changePasswords(String newPassword) {
        this.password = newPassword;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public boolean isRecoveryAnswerCorrect(String answer) {
        return this.recovery.getObject2().equals(answer);
    }

    public int getRecoveryQuestionNumber() {
        return recovery.getObject1();
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }


    @Override
    public int compareTo(User o) {
        return o.highScore - highScore;
    }
}
