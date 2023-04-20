package controller;

import model.Database;
import model.User;
import utils.enums.Slogan;
import view.enums.messages.CommonMessages;
import view.enums.messages.RegisterMenuMessages;
import view.menus.CaptchaMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterMenuController {

    Database database;

    public RegisterMenuController(Database database) {
        this.database = database;
    }

    public RegisterMenuMessages checkErrorsForRegister(String username, String password,
                                                       String passwordConfirmation, String nickname,
                                                       String email, String sloganTag, String slogan) {
        if (username == null) return RegisterMenuMessages.NULL_USERNAME;
        else if (password == null) return RegisterMenuMessages.NULL_PASSWORD;
        else if (nickname == null) return RegisterMenuMessages.NULL_NICKNAME;
        else if (email == null) return RegisterMenuMessages.NULL_EMAIL;
        else if (sloganTag != null && slogan == null) return RegisterMenuMessages.NULL_SLOGAN;
        else if (MainController.isUsernameValid(username)) return RegisterMenuMessages.INVALID_USERNAME;
        else if (database.getUserByUsername(username) != null) return RegisterMenuMessages.DUPLICATE_USERNAME;
        else if (!password.equals("random")) {
            RegisterMenuMessages passwordCheckMessage = checkPasswordErrors(password, passwordConfirmation);
            if (passwordCheckMessage != RegisterMenuMessages.SUCCESS) return passwordCheckMessage;
        }
        for (User e : database.getAllUsers()) {
            if (e.getEmail().equals(email)) return RegisterMenuMessages.DUPLICATE_EMAIL;
        }
        if (MainController.isEmailValid(email)) return RegisterMenuMessages.INVALID_EMAIL;
        return RegisterMenuMessages.SUCCESS;
    }

    public RegisterMenuMessages checkPasswordErrors(String password, String passwordConfirmation) {
        CommonMessages passwordMessage = MainController.whatIsPasswordProblem(password);
        switch (passwordMessage) {
            case OK -> {
            }
            case SHORT_PASSWORD -> {
                return RegisterMenuMessages.SHORT_PASSWORD;
            }
            case NON_CAPITAL_PASSWORD -> {
                return RegisterMenuMessages.NON_CAPITAL_PASSWORD;
            }
            case NON_SMALL_PASSWORD -> {
                return RegisterMenuMessages.NON_SMALL_PASSWORD;
            }
            case NON_NUMBER_PASSWORD -> {
                return RegisterMenuMessages.NON_NUMBER_PASSWORD;
            }
        }
        if (!password.equals(passwordConfirmation)) return RegisterMenuMessages.INCORRECT_PASSWORD_CONFIRM;
        return RegisterMenuMessages.SUCCESS;
    }

    public RegisterMenuMessages checkErrorsForSecurityQuestion(String recoveryQuestion, String answer, String answerConfirm) {
        int recoveryQuestionNumber = Integer.parseInt(recoveryQuestion);
        if (recoveryQuestionNumber < 1 || recoveryQuestionNumber > 3) return RegisterMenuMessages.INVALID_NUMBER;
        else if (!answer.equals(answerConfirm)) return RegisterMenuMessages.INCORRECT_ANSWER_CONFIRMATION;
        return RegisterMenuMessages.SUCCESS;
    }

    public String makeRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        List<Integer> indexesOfPassword = new ArrayList<>();
        String[] passwordCharacters = new String[]{"0123456789", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "abcdefghijklmnopqrstuvwxyz", "`~!@#$%^&*()_+=-*/><?{}[]|\\/:\\;\",."};
        int sizeOfPassword = (int) (Math.random() * 8) + 8;
        password.setLength(sizeOfPassword);
        for (int i = 0; i < sizeOfPassword; i++) indexesOfPassword.add(i);
        for (int i = 0; i < 4; i++) {
            int indexCharInPassword = indexesOfPassword.get(random.nextInt(indexesOfPassword.size()));
            indexesOfPassword.remove((Integer) indexCharInPassword);
            int indexOfCharacter = (int) (Math.random() * passwordCharacters[i].length());
            password.setCharAt(indexCharInPassword, passwordCharacters[i].charAt(indexOfCharacter));
        }
        for (int i = 0; i < sizeOfPassword - 4; i++) {
            int indexInPassword = indexesOfPassword.get(random.nextInt(indexesOfPassword.size()));
            int passwordCharactersIndex = (int) (Math.random() * 4);
            indexesOfPassword.remove((Integer) indexInPassword);
            int indexOfCharacter = (int) (Math.random() * passwordCharacters[passwordCharactersIndex].length());
            password.setCharAt(indexInPassword, passwordCharacters[passwordCharactersIndex].charAt(indexOfCharacter));
        }
        return password.toString();
    }

    public String makeRandomSlogan() {
        return Slogan.getRandomSlogan();
    }

    public RegisterMenuMessages registerUser(String username, String password,
                                             String nickname, String email,
                                             String slogan, int recoveryQuestionNumber,
                                             String recoveryAnswer) {
        if (!CaptchaMenu.runCaptcha()) return RegisterMenuMessages.INCORRECT_CAPTCHA;
        String passwordAsSHA = MainController.getSHA256(password);
        String recoveryAnswerAsSHA = MainController.getSHA256(recoveryAnswer);
        database.addUser(username, passwordAsSHA, nickname, slogan, email, recoveryQuestionNumber, recoveryAnswerAsSHA);
        try {
            database.saveDataIntoFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return RegisterMenuMessages.SUCCESS;
    }
}
