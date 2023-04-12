package view.menus;

import controller.ProfileMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    ProfileMenuController profileMenuController;

    public ProfileMenu(ProfileMenuController profileMenuController) {
        this.profileMenuController = profileMenuController;
    }

    public void run() {
        //TODO get inputs from function and check them and call functions
    }

    private void changeUsername(Matcher matcher) {
        //TODO call controller and print errors
    }

    private void changeNickname(Matcher matcher) {
        //TODO call controller and print errors
    }

    private void changePassword(Matcher matcher) {
        //TODO call controller and print errors
    }

    private void checkPasswordConfirmAndChangeThat(String password) {
        //TODO if password confirm is ok change password
    }

    private void changeEmail(Matcher matcher) {
        //TODO call controller and print errors
    }

    private void changeSlogan(Matcher matcher) {
        //TODO call controller and print errors
    }

    private void removeSlogan() {
        //TODO connect controller
    }

    private void showHighScore() {

    }

    private void showRank() {

    }

    private void showSlogan() {

    }

    private void showAllOfProfile() {
    }
}
