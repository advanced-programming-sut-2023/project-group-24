package view.oldmenus;

import controller.AppController;
import controller.MainController;
import controller.MenusName;
import controller.nongame.ResetPasswordMenuController;
import view.enums.commands.Commands;
import view.enums.messages.ResetPasswordMessages;

import java.util.regex.Matcher;

public class ResetPasswordMenu {

    ResetPasswordMenuController resetPasswordMenuController;

    public ResetPasswordMenu(ResetPasswordMenuController resetPasswordMenuController) {
        this.resetPasswordMenuController = resetPasswordMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu() == MenusName.RESET_PASSWORD_MENU) {
            command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.RESET_PASSWORD)) != null)
                checkUserUsername(matcher);
            else if (Commands.getMatcher(command, Commands.SHOW_CURRENT_MENU) != null)
                System.out.println("Reset password menu");
            else if (Commands.getMatcher(command, Commands.EXIT) != null)
                loginMenu();
            else System.out.println("Invalid command!");
        }
    }

    private void loginMenu() {
        AppController.setCurrentMenu(MenusName.LOGIN_MENU);
    }

    private void checkUserUsername(Matcher matcher) {
        String username = MainController.removeDoubleQuotation(matcher.group("username"));
        ResetPasswordMessages message = resetPasswordMenuController.checkUserUsername(username);
        switch (message) {
            case USER_NOT_FOUND:
                System.out.println("User not found!");
                break;
            case USER_FOUND:
                getAndCheckUserRecoveryAnswer();
                break;
        }
    }

    private void getAndCheckUserRecoveryAnswer() {
        String recoveryQuestion = resetPasswordMenuController.getUserRecoveryQuestion();
        System.out.println(recoveryQuestion);
        String recoveryAnswer = GetInputFromUser.getUserInput();
        ResetPasswordMessages message = resetPasswordMenuController.checkUserRecoveryAnswer(recoveryAnswer);
        switch (message) {
            case INCORRECT_ANSWER:
                System.out.println("Your answer is not correct!");
                break;
            case CORRECT_ANSWER:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        System.out.println("Enter your new password: ");
        String newPassword = GetInputFromUser.getUserInput();
        System.out.println("Re-enter your new password: ");
        String newPasswordConfirm = GetInputFromUser.getUserInput();
        ResetPasswordMessages message = resetPasswordMenuController.checkAndChangeNewPassword(newPassword, newPasswordConfirm);
        switch (message) {
            case SUCCESS:
                System.out.println("Your password has been successfully changed");
                loginMenu();
                break;
            case PASSWORD_REPETITION_DO_NOT_MATCH:
                System.out.println("The password and its repetition do not match");
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
        }
    }
}
