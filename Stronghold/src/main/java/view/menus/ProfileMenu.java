package view.menus;

import controller.AppController;
import controller.MainController;
import controller.ProfileMenuController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.ProfileMenuMessages;

import java.util.regex.Matcher;

public class ProfileMenu {

    ProfileMenuController profileMenuController;

    public ProfileMenu(ProfileMenuController profileMenuController) {
        this.profileMenuController = profileMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu() == MenusName.PROFILE_MENU) {
            command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.CHANGE_USERNAME)) != null)
                changeUsername(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.CHANGE_NICKNAME)) != null)
                changeNickname(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.CHANGE_PASSWORD)) != null)
                changePassword(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.CHANGE_EMAIL)) != null)
                changeEmail(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.CHANGE_SLOGAN)) != null)
                changeSlogan(matcher);
            else if (Commands.getMatcher(command, Commands.REMOVE_SLOGAN) != null)
                removeSlogan();
            else if (Commands.getMatcher(command, Commands.DISPLAY_HIGHSCORE) != null)
                showHighScore();
            else if (Commands.getMatcher(command, Commands.DISPLAY_RANK) != null)
                showRank();
            else if (Commands.getMatcher(command, Commands.DISPLAY_SLOGAN) != null)
                showSlogan();
            else if (Commands.getMatcher(command, Commands.PROFILE_DISPLAY) != null)
                showAllOfProfile();
            else if (Commands.getMatcher(command, Commands.EXIT) != null)
                enterMainMenu();
        }
    }

    private void enterMainMenu() {
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
        System.out.println("You are in main menu now!");
    }

    private void changeUsername(Matcher matcher) {
        String username = MainController.removeDoubleQuotation(matcher.group("username"));
        ProfileMenuMessages message = profileMenuController.changeUsername(username);
        switch (message) {
            case SUCCESS -> System.out.println("Your username has been successfully changed");
            case INVALID_USERNAME -> System.out.println("Your username format is invalid!");
            case DUPLICATE_USERNAME -> System.out.println("This username is already used");
            case NULL_FIELD -> System.out.println("Please enter new username");
            default -> {
            }
        }
    }

    private void changeNickname(Matcher matcher) {
        String nickname = MainController.removeDoubleQuotation(matcher.group("nickname"));
        ProfileMenuMessages message = profileMenuController.changeNickname(nickname);
        switch (message) {
            case SUCCESS -> System.out.println("Your nickname has been successfully changed");
            case NULL_FIELD -> System.out.println("Please enter your new nickname");
        }
    }

    private void changePassword(Matcher matcher) {
        String oldPassword = MainController.removeDoubleQuotation(matcher.group("oldPassword"));
        String newPassword = MainController.removeDoubleQuotation(matcher.group("newPassword"));
        ProfileMenuMessages message = profileMenuController.checkChangePasswordErrors(oldPassword, newPassword);
        switch (message) {
            case SUCCESS -> checkPasswordConfirmAndChangeThat(newPassword);
            case NULL_FIELD -> System.out.println("Please fill all fields");
            case INCORRECT_CAPTCHA -> System.out.println("You entered the captcha code incorrectly!");
            case INCORRECT_PASSWORD -> System.out.println("Current password is incorrect!");
            case SHORT_PASSWORD -> System.out.println("The new password is too short!");
            case NON_CAPITAL_PASSWORD -> System.out.println("The new password must contain uppercase characters!");
            case NON_SMALL_PASSWORD -> System.out.println("The new password must contain lowercase characters!");
            case NON_SPECIFIC_PASSWORD -> System.out.println("The new password must contain specific characters!");
            case NON_NUMBER_PASSWORD -> System.out.println("The new password must contain numbers!");
            case DUPLICATE_PASSWORD -> System.out.println("Please enter a new password!");
        }
    }

    private void checkPasswordConfirmAndChangeThat(String newPassword) {
        System.out.println("Please enter your new password again");
        String newPasswordConfirm = GetInputFromUser.getUserInput();
        ProfileMenuMessages message = profileMenuController.changePassword(newPassword, newPasswordConfirm);
        switch (message) {
            case SUCCESS -> System.out.println("Your password has been successfully changed!");
            case NULL_FIELD -> System.out.println("You didn't enter anything!");
            case INVALID_PASSWORD_CONFIRM -> System.out.println("Your new password confirmation is not correct!");
        }
    }

    private void changeEmail(Matcher matcher) {
        String email = MainController.removeDoubleQuotation(matcher.group("email"));
        ProfileMenuMessages message = profileMenuController.changeEmail(email);
        switch (message) {
            case SUCCESS -> System.out.println("Your email has been successfully changed!");
            case NULL_FIELD -> System.out.println("Please enter your new email!");
            case INVALID_EMAIL_FORMAT -> System.out.println("Your email format is invalid!");
        }
    }

    private void changeSlogan(Matcher matcher) {
        String slogan = MainController.removeDoubleQuotation(matcher.group("slogan"));
        ProfileMenuMessages message = profileMenuController.changeSlogan(slogan);
        switch (message) {
            case SUCCESS -> System.out.println("Your slogan has been successfully changed!");
            case NULL_FIELD -> System.out.println("Please enter your new slogan!");
        }
    }

    private void removeSlogan() {
        ProfileMenuMessages message = profileMenuController.removeSlogan();
        switch (message) {
            case SUCCESS -> System.out.println("Your slogan has been successfully removed!");
            case EMPTY_SLOGAN -> System.out.println("You have no slogan to delete!");
        }
    }

    private void showHighScore() {
        String result = profileMenuController.showHighScore();
        System.out.println(result);
    }

    private void showRank() {
        String result = profileMenuController.showRank();
        System.out.println(result);
    }

    private void showSlogan() {
        String result = profileMenuController.showSlogan();
        System.out.println(result);
    }

    private void showAllOfProfile() {
        String result = profileMenuController.showProfile();
        System.out.println(result);
    }
}
