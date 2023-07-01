package controller.nongame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import controller.InputOutputHandler;
import controller.MainController;
import controller.functionalcontrollers.Pair;
import model.Packet;
import model.User;
import model.databases.Database;
import model.enums.Slogan;
import view.enums.messages.CommonMessages;
import view.enums.messages.RegisterMenuMessages;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterController implements Controller {
    private Database database;
    private InputOutputHandler ioHandler;

    public RegisterController(InputOutputHandler ioHandler, Database database) {
        this.ioHandler = ioHandler;
        this.database = database;
        ioHandler.addListener(evt -> handlePacket((Packet) evt.getNewValue()));
    }

    private void handlePacket(Packet packet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        if (packet.getTopic().equals("register")) switch (packet.getSubject()) {
            case "register":
                registerUser(gson.fromJson(packet.getValue(), User.class));
                break;
        }
    }

    public RegisterMenuMessages checkEmailErrors(String email) {
        if (email.equals("")) return RegisterMenuMessages.NULL_EMAIL;
        for (User e : database.getAllUsers()) {
            if (e.getEmail().equalsIgnoreCase(email)) return RegisterMenuMessages.DUPLICATE_EMAIL;
        }
        if (MainController.isEmailValid(email)) return RegisterMenuMessages.INVALID_EMAIL;
        return RegisterMenuMessages.SUCCESS;
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

    public RegisterMenuMessages checkUsernameErrors(String username) {
        if (username.equals("")) return RegisterMenuMessages.NULL_USERNAME;
        else if (MainController.isUsernameValid(username)) return RegisterMenuMessages.INVALID_USERNAME;
        else if (database.getUserByUsername(username) != null) return RegisterMenuMessages.DUPLICATE_USERNAME;
        return RegisterMenuMessages.SUCCESS;
    }

    public void registerUser(User user) {
        database.addUser(user);
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
