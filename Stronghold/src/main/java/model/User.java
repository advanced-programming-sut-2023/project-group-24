package model;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String slogan;
    private String email;
    private int highScore;
    private final int recoveryQuestionNumber;
    private final String recoveryAnswer;

    public User(String username, String password, String nickname, String slogan,
                String email, int recoveryQuestionNumber, String recoveryAnswer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.recoveryQuestionNumber = recoveryQuestionNumber;
        this.recoveryAnswer = recoveryAnswer;
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

    public String getSlogan() {
        return slogan;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void changePasswords(String newPassword) {
        password = newPassword;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public boolean isRecoveryAnswerCorrect(String answer) {
        return this.recoveryAnswer.equals(answer);
    }

    public int getRecoveryQuestionNumber() {
        return recoveryQuestionNumber;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}