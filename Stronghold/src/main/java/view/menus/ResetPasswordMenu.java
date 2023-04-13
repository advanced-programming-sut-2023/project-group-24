package view.menus;

import controller.ResetPasswordMenuController;

import java.util.regex.Matcher;

public class ResetPasswordMenu {

    ResetPasswordMenuController resetPasswordMenuController;

    public ResetPasswordMenu(ResetPasswordMenuController resetPasswordMenuController) {
        this.resetPasswordMenuController = resetPasswordMenuController;
    }

    public void run() {
        //TODO get commands from GetInputFromUser func anc check that
    }

    private void checkUserUsername(Matcher matcher) {
        //TODO check user username and set currentUser for database
    }

    private void getAndCheckUserRecoveryAnswer() {
        //TODO get user Recovery Answer
    }

    private void changePassword() {
        //TODO get new password from user and change pass
    }
}
