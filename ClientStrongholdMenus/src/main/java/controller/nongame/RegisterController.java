package controller.nongame;

import controller.Controller;
import controller.InputOutputHandler;
import controller.MainController;
import model.Packet;
import model.enums.Slogan;
import view.controls.Control;
import view.enums.messages.CommonMessages;
import view.enums.messages.RegisterMenuMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterController implements Controller {
    private InputOutputHandler inputOutputHandler;

    public RegisterController(InputOutputHandler inputOutputHandler, Control control) {
        this.inputOutputHandler = inputOutputHandler;
        inputOutputHandler.addListener(evt -> control.listener().propertyChange(evt));
    }

    public void requestCheckEmailErrors(String email) {
        Packet packet = new Packet("register", "check email errors", null, email);
        inputOutputHandler.sendPacket(packet);
    }

    public RegisterMenuMessages checkPasswordErrors(String password, String passwordConfirmation) {
        if (password.equals("")) return RegisterMenuMessages.NULL_PASSWORD;
        CommonMessages passwordMessage = MainController.whatIsPasswordProblem(password);
        switch (passwordMessage) {
            case SHORT_PASSWORD:
                return RegisterMenuMessages.SHORT_PASSWORD;
            case NON_CAPITAL_PASSWORD:
                return RegisterMenuMessages.NON_CAPITAL_PASSWORD;
            case NON_SMALL_PASSWORD:
                return RegisterMenuMessages.NON_SMALL_PASSWORD;
            case NON_NUMBER_PASSWORD:
                return RegisterMenuMessages.NON_NUMBER_PASSWORD;
            case NON_SPECIFIC_PASSWORD:
                return RegisterMenuMessages.NON_SPECIFIC_PASSWORD;
        }
        if (!password.equals(passwordConfirmation)) return RegisterMenuMessages.INCORRECT_PASSWORD_CONFIRM;
        return RegisterMenuMessages.SUCCESS;
    }

    public void requestCheckUsernameErrors(String username) {
        Packet packet = new Packet("register", "check username errors", null, username);
        inputOutputHandler.sendPacket(packet);
    }

    public String makeRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        List<Integer> indexesOfPassword = new ArrayList<>();
        String[] passwordCharacters = new String[]{"0123456789", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "abcdefghijklmnopqrstuvwxyz", "`~!@#$%^&*()+=-*/><?{}[]|\\/:\\;\",."};
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
}
