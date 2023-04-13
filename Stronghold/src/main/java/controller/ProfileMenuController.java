package controller;

import model.Database;
import model.User;
import view.enums.messages.ProfileMenuMessages;

public class ProfileMenuController {
    private final Database database;
    private final User currentUser;

    public ProfileMenuController(Database database) {
        this.database = database;
        currentUser = AppController.getLoggedInUser();
    }

    public ProfileMenuMessages changeUsername(String newUsername) {
        //TODO change username
        return null;
    }

    public ProfileMenuMessages changeNickname(String newNickname) {
        //TODO change nickname
        return null;
    }

    public ProfileMenuMessages checkChangePasswordErrors(String oldPassword, String newPassword) {
        //TODO just check errors
        return null;
    }

    public ProfileMenuMessages changePassword(String newPassword) {
        //TODO check errors and change pass
        return null;
    }

    public ProfileMenuMessages changeEmail(String newEmail) {
        //TODO change email
        return null;
    }

    public ProfileMenuMessages changeSlogan(String newSlogan) {
        //TODO change slogan
        return null;
    }

    public ProfileMenuMessages removeSlogan() {
        //TODO remove Slogan
        return null;
    }

    public String showHighScore() {
        //TODO get highScore and return it
        return null;
    }

    public String showRand() {
        //TODO get rank and return it
        return null;
    }

    public String showSlogan() {
        //TODO get slogan and return it
        return null;
    }

    public String showProfile() {
        //TODO get profile data and return it
        return null;
    }
}
