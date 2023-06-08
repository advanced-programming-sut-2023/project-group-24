package view.oldmenus;

import controller.AppController;
import controller.MainController;
import controller.MenusName;
import controller.ProfileController;
import view.enums.commands.Commands;
import view.enums.messages.ProfileMenuMessages;

import java.util.regex.Matcher;

public class ProfileMenu {

    ProfileController profileController;

    public ProfileMenu(ProfileController profileController) {
        this.profileController = profileController;
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
            else if (Commands.getMatcher(command, Commands.SHOW_CURRENT_MENU) != null)
                System.out.println("Profile menu");
            else
                System.out.println("Invalid command!");
        }
    }

    private void enterMainMenu() {
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
        System.out.println("You are in main menu now!");
    }

    private void changeUsername(Matcher matcher) {
        String username = MainController.removeDoubleQuotation(matcher.group("username"));
        ProfileMenuMessages message = profileController.changeUsername(username);
        switch (message) {
            case SUCCESS:
                System.out.println("Your username has been successfully changed");
                break;
            case INVALID_USERNAME:
                System.out.println("Your username format is invalid!");
                break;
            case DUPLICATE_USERNAME:
                System.out.println("This username is already used");
                break;
            case NULL_FIELD:
                System.out.println("Please enter new username");
                break;
            default:
                break;
        }
    }

    private void changeNickname(Matcher matcher) {
        String nickname = MainController.removeDoubleQuotation(matcher.group("nickname"));
        ProfileMenuMessages message = profileController.changeNickname(nickname);
        switch (message) {
            case SUCCESS:
                System.out.println("Your nickname has been successfully changed");
                break;
            case NULL_FIELD:
                System.out.println("Please enter your new nickname");
                break;
        }
    }

    private void changePassword(Matcher matcher) {
        String oldPassword = MainController.removeDoubleQuotation(matcher.group("oldPassword"));
        String newPassword = MainController.removeDoubleQuotation(matcher.group("newPassword"));
        ProfileMenuMessages message = profileController.checkChangePasswordErrors(oldPassword, newPassword);
        switch (message) {
            case SUCCESS:
                checkPasswordConfirmAndChangeThat(newPassword);
                break;
            case NULL_FIELD:
                System.out.println("Please fill all fields");
                break;
            case INCORRECT_CAPTCHA:
                System.out.println("You entered the captcha code incorrectly!");
                break;
            case INCORRECT_PASSWORD:
                System.out.println("Current password is incorrect!");
                break;
            case SHORT_PASSWORD:
                System.out.println("The new password is too short!");
                break;
            case NON_CAPITAL_PASSWORD:
                System.out.println("The new password must contain uppercase characters!");
                break;
            case NON_SMALL_PASSWORD:
                System.out.println("The new password must contain lowercase characters!");
                break;
            case NON_SPECIFIC_PASSWORD:
                System.out.println("The new password must contain specific characters!");
                break;
            case NON_NUMBER_PASSWORD:
                System.out.println("The new password must contain numbers!");
                break;
            case DUPLICATE_PASSWORD:
                System.out.println("Please enter a new password!");
                break;
        }
    }

    private void checkPasswordConfirmAndChangeThat(String newPassword) {
        System.out.println("Please enter your new password again");
        String newPasswordConfirm = GetInputFromUser.getUserInput();
        ProfileMenuMessages message = profileController.changePassword(newPassword, newPasswordConfirm);
        switch (message) {
            case SUCCESS:
                System.out.println("Your password has been successfully changed!");
                break;
            case NULL_FIELD:
                System.out.println("You didn't enter anything!");
                break;
            case INVALID_PASSWORD_CONFIRM:
                System.out.println("Your new password confirmation is not correct!");
                break;
        }
    }

    private void changeEmail(Matcher matcher) {
        String email = MainController.removeDoubleQuotation(matcher.group("email"));
        ProfileMenuMessages message = profileController.changeEmail(email);
        switch (message) {
            case SUCCESS:
                System.out.println("Your email has been successfully changed!");
                break;
            case NULL_FIELD:
                System.out.println("Please enter your new email!");
                break;
            case INVALID_EMAIL_FORMAT:
                System.out.println("Your email format is invalid!");
                break;
            case DUPLICATE_EMAIL:
                System.out.println("This has been used by another user!");
                break;
        }
    }

    private void changeSlogan(Matcher matcher) {
        String slogan = MainController.removeDoubleQuotation(matcher.group("slogan"));
        ProfileMenuMessages message = profileController.changeSlogan(slogan);
        switch (message) {
            case SUCCESS:
                System.out.println("Your slogan has been successfully changed!");
                break;
            case NULL_FIELD:
                System.out.println("Please enter your new slogan!");
                break;
        }
    }

    private void removeSlogan() {
        ProfileMenuMessages message = profileController.removeSlogan();
        switch (message) {
            case SUCCESS:
                System.out.println("Your slogan has been successfully removed!");
                break;
            case EMPTY_SLOGAN:
                System.out.println("You have no slogan to delete!");
                break;
        }
    }

    private void showHighScore() {
        int result = profileController.showHighScore();
        System.out.println("Your high score is: " + result);
    }

    private void showRank() {
        int result = profileController.showRank();
        System.out.println("Your rank is: " + result);
    }

    private void showSlogan() {
        String result = profileController.showSlogan();
        System.out.println(result);
    }

    private void showAllOfProfile() {
        String result = profileController.showProfile();
        System.out.println(result);
    }
}
