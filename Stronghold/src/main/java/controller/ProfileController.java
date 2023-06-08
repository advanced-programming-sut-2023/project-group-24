package controller;

import model.User;
import model.databases.Database;
import view.enums.messages.CommonMessages;
import view.enums.messages.ProfileMenuMessages;
import view.oldmenus.CaptchaMenu;

import javax.naming.ldap.Control;
import java.util.Vector;

public class ProfileController implements Controller {
    private final Database database;
    private User currentUser;

    public ProfileController(Database database) {
        this.database = database;
        currentUser = database.getUserByUsername(AppController.getLoggedInUser().getUsername());
    }

    public ProfileController(Database database, User currentUser) {
        this.database = database;
        this.currentUser = currentUser;
    }

    public ProfileMenuMessages changeUsername(String newUsername) {
        ProfileMenuMessages message = checkChangeUsernameErrors(newUsername);
        if (message != null) return message;
        currentUser.setUsername(newUsername);
        database.saveDataIntoFile();
        return ProfileMenuMessages.SUCCESS;
    }

    private ProfileMenuMessages checkChangeUsernameErrors(String newUsername) {
        if (newUsername == null) return ProfileMenuMessages.NULL_FIELD;
        else if (MainController.isUsernameValid(newUsername)) return ProfileMenuMessages.INVALID_USERNAME;
        else if (database.getUserByUsername(newUsername) != null) return ProfileMenuMessages.DUPLICATE_USERNAME;
        return null;
    }

    public ProfileMenuMessages changeNickname(String newNickname) {
        if (newNickname == null) return ProfileMenuMessages.NULL_FIELD;
        currentUser.setNickName(newNickname);
        database.saveDataIntoFile();
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
        database.saveDataIntoFile();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeEmail(String newEmail) {
        ProfileMenuMessages x = checkChangeEmailErrors(newEmail);
        if (x != null) return x;
        currentUser.setEmail(newEmail);
        database.saveDataIntoFile();
        return ProfileMenuMessages.SUCCESS;
    }

    private ProfileMenuMessages checkChangeEmailErrors(String newEmail) {
        if (newEmail == null) return ProfileMenuMessages.NULL_FIELD;
        else if (MainController.isEmailValid(newEmail)) return ProfileMenuMessages.INVALID_EMAIL_FORMAT;
        for (User e : database.getAllUsers())
            if (e.getEmail().equalsIgnoreCase(newEmail)) return ProfileMenuMessages.DUPLICATE_EMAIL;
        return null;
    }

    public ProfileMenuMessages changeSlogan(String newSlogan) {
        if (newSlogan == null) return ProfileMenuMessages.NULL_FIELD;
        currentUser.setSlogan(newSlogan);
        database.saveDataIntoFile();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages removeSlogan() {
        if (currentUser.getSlogan() == null) return ProfileMenuMessages.EMPTY_SLOGAN;
        currentUser.setSlogan(null);
        database.saveDataIntoFile();
        return ProfileMenuMessages.SUCCESS;
    }

    public int showHighScore() {
        return currentUser.getHighScore();
    }

    public int showRank() {
        Vector<User> sortedUsers = database.getAllUsersByRank();
        for (int i = 0; i < sortedUsers.size(); i++) {
            if (sortedUsers.get(i).equals(currentUser))
                return i + 1;
        }
        return 0;
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

    public String getCurrentUser(String info) {
        switch (info) {
            case "username":
                return currentUser.getUsername();
            case "nickname":
                return currentUser.getNickname();
            case "email":
                return currentUser.getEmail();
            case "slogan":
                return currentUser.getSlogan();
        }
        return "";
    }
}
