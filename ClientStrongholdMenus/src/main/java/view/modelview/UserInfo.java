package view.modelview;

import javafx.scene.image.Image;
import model.Session;
import model.User;

public class UserInfo {
    private static final Image[] avatars = new Image[] {
            new Image(UserInfo.class.getResource("/images/avatars/0.png").toExternalForm()),
            new Image(UserInfo.class.getResource("/images/avatars/1.png").toExternalForm()),
            new Image(UserInfo.class.getResource("/images/avatars/2.png").toExternalForm()),
            new Image(UserInfo.class.getResource("/images/avatars/3.png").toExternalForm()),
            new Image(UserInfo.class.getResource("/images/avatars/4.png").toExternalForm()),
            new Image(UserInfo.class.getResource("/images/avatars/5.png").toExternalForm()),
    };

    private Image avatar;
    private String username;
    private int highscore;
    private int rank;
    private boolean isOnline;
    private Session lastSeen;

    public UserInfo(Image avatar, String username, int highscore, int rank) {
        this.avatar = avatar;
        this.username = username;
        this.highscore = highscore;
        this.rank = rank;
    }

    public UserInfo(User user, int rank) {
        this.avatar = avatars[user.getCurrentAvatar()];
        this.username = user.getUsername();
        this.highscore = user.getHighScore();
        this.rank = rank;
        this.isOnline = user.isOnline();
        this.lastSeen = user.getLastSession();
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

    public String getLastSeen() {
        if (isOnline) return "Online";
        else return lastSeen.getMonth() + "/" + lastSeen.getDay() + "  " + lastSeen.getHour() + ":" + lastSeen.getMinute();
    }
}
