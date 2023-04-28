package controller;

import model.User;
import model.databases.Database;
import view.enums.messages.CommonMessages;
import view.enums.messages.ProfileMenuMessages;
import view.menus.CaptchaMenu;

import java.util.Vector;

public class ProfileMenuController {
    private final Database database;
    private final User currentUser;

    public ProfileMenuController(Database database) {
        this.database = database;
        currentUser = AppController.getLoggedInUser();
    }

    public ProfileMenuMessages changeUsername(String newUsername) {
        if (newUsername == null) return ProfileMenuMessages.NULL_FIELD;
        else if (MainController.isUsernameValid(newUsername)) return ProfileMenuMessages.INVALID_USERNAME;
        else if (database.getUserByUsername(newUsername) != null) return ProfileMenuMessages.DUPLICATE_USERNAME;
        currentUser.setUsername(newUsername);
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeNickname(String newNickname) {
        if (newNickname == null) return ProfileMenuMessages.NULL_FIELD;
        currentUser.setNickName(newNickname);
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages checkChangePasswordErrors(String oldPassword, String newPassword) {
        if (oldPassword == null || newPassword == null) return ProfileMenuMessages.NULL_FIELD;
        String oldPasswordSHA = MainController.getSHA256(oldPassword);
        if (!currentUser.isPasswordCorrect(oldPasswordSHA))
            return ProfileMenuMessages.INCORRECT_PASSWORD;
        CommonMessages message = MainController.whatIsPasswordProblem(newPassword);
        switch (message) {
            case OK:
                break;
            case SHORT_PASSWORD:
                return ProfileMenuMessages.SHORT_PASSWORD;
            case NON_CAPITAL_PASSWORD:
                return ProfileMenuMessages.NON_CAPITAL_PASSWORD;
            case NON_SMALL_PASSWORD:
                return ProfileMenuMessages.NON_SMALL_PASSWORD;
            case NON_NUMBER_PASSWORD:
                return ProfileMenuMessages.NON_NUMBER_PASSWORD;
            case NON_SPECIFIC_PASSWORD:
                return ProfileMenuMessages.NON_SPECIFIC_PASSWORD;
        }
        if (oldPassword.equals(newPassword)) return ProfileMenuMessages.DUPLICATE_PASSWORD;
        if (!CaptchaMenu.runCaptcha()) return ProfileMenuMessages.INCORRECT_CAPTCHA;
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changePassword(String newPassword, String newPasswordConfirm) {
        if (newPasswordConfirm == null) return ProfileMenuMessages.NULL_FIELD;
        if (!newPassword.equals(newPasswordConfirm)) return ProfileMenuMessages.INVALID_PASSWORD_CONFIRM;
        String newPasswordAsSHA = MainController.getSHA256(newPassword);
        currentUser.changePasswords(newPasswordAsSHA);
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeEmail(String newEmail) {
        if (newEmail == null) return ProfileMenuMessages.NULL_FIELD;
        else if (MainController.isEmailValid(newEmail)) return ProfileMenuMessages.INVALID_EMAIL_FORMAT;
        currentUser.setEmail(newEmail);
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeSlogan(String newSlogan) {
        if (newSlogan == null) return ProfileMenuMessages.NULL_FIELD;
        currentUser.setSlogan(newSlogan);
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages removeSlogan() {
        if (currentUser.getSlogan() == null) return ProfileMenuMessages.EMPTY_SLOGAN;
        currentUser.setSlogan(null);
        return ProfileMenuMessages.SUCCESS;
    }

    public String showHighScore() {
        int highScore = currentUser.getHighScore();
        return "Your highscore is " + highScore;
    }

    public String showRank() {
        Vector<User> sortedUsers = database.getAllUsersByRank();
        for (int i = 0; i < sortedUsers.size(); i++) {
            if (sortedUsers.get(i).equals(currentUser))
                return "Your rank is " + (i + 1);
        }
        return null;
    }

    public String showSlogan() {
        return currentUser.getSlogan();
    }

    public String showProfile() {
        String username = "Username: " + currentUser.getUsername();
        String nickname = "Nickname: " + currentUser.getNickname();
        String email = "Email: " + currentUser.getEmail();
        String rank = "Rank: " + showRank();
        String slogan = "Slogan: " + showSlogan();
        return username + "\n" + nickname + "\n" + email + "\n" + rank + "\n" + slogan;
    }
}
