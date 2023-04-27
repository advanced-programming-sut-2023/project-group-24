package controller;

import model.databases.Database;
import model.enums.RecoveryQuestion;
import model.User;
import view.enums.messages.ResetPasswordMessages;

public class ResetPasswordMenuController {

    private final Database database;
    private User currentUser;

    public ResetPasswordMenuController(Database database) {
        this.database = database;
    }

    public ResetPasswordMessages checkUserUsername(String username) {
        currentUser = database.getUserByUsername(username);
        if (currentUser == null) return ResetPasswordMessages.USER_NOT_FOUND;
        else return ResetPasswordMessages.USER_FOUND;
    }

    public ResetPasswordMessages checkUserRecoveryAnswer(String recoveryAnswer) {
        String recoveryAnswerAsSHA = MainController.getSHA256(recoveryAnswer);
        if (currentUser.isRecoveryAnswerCorrect(recoveryAnswerAsSHA)) return ResetPasswordMessages.CORRECT_ANSWER;
        else return ResetPasswordMessages.INCORRECT_ANSWER;
    }

    public ResetPasswordMessages checkAndChangeNewPassword(String newPassword, String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)) return ResetPasswordMessages.PASSWORD_REPETITION_DO_NOT_MATCH;
        String newPasswordAsSHA = MainController.getSHA256(newPassword);
        changePassword(newPasswordAsSHA);
        return ResetPasswordMessages.SUCCESS;
    }

    public String getUserRecoveryQuestion() {
        int numberOfQuestion = currentUser.getRecoveryQuestionNumber();
        return RecoveryQuestion.getRecoveryQuestionByNumber(numberOfQuestion);
    }

    public void changePassword(String newPassword) {
        currentUser.changePasswords(newPassword);
    }
}
