package view.modelview;

import javafx.scene.image.Image;

public class UserInfo {
    private Image avatar;
    private String username;
    private int highscore;
    private int rank;

    public UserInfo(Image avatar, String username, int highscore, int rank) {
        this.avatar = avatar;
        this.username = username;
        this.highscore = highscore;
        this.rank = rank;
    }

    public UserInfo(String username, int highscore, int rank) {
        this.username = username;
        this.highscore = highscore;
        this.rank = rank;
    }

    public Image getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public int getHighscore() {
        return highscore;
    }

    public int getRank() {
        return rank;
    }
}
