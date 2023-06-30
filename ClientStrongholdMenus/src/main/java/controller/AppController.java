package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.functionalcontrollers.Pair;
import controller.nongame.*;
import javafx.stage.Stage;
import model.Packet;
import model.User;
import model.UserInfo;
import model.databases.ChatDatabase;
import model.databases.Database;
import view.controls.Control;
import view.menus.login.*;
import view.menus.main.ChatMenu;
import view.menus.main.CreateMapMenu;
import view.menus.main.EnterMapMenu;
import view.menus.main.MainMenu;
import view.menus.profile.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class AppController {
    private static final String HOST = "localhost";
    private static final int PORT = 1717;

    private Socket socket;
    private Database database;
    private ChatDatabase chatDatabase;
    private InputOutputHandler inputOutputHandler;
    private Stage stage;
    private UserInfo userInfo;
    private ArrayList<PropertyChangeListener> updateListeners;
    private User currentUser;

    public AppController(Stage stage) {
        try {
            System.out.println("Connecting to the sever...");
            this.database = new Database();
            this.socket = new Socket(HOST, PORT);
            this.stage = stage;
            this.updateListeners = new ArrayList<>();
            this.inputOutputHandler = new InputOutputHandler(
                    new DataOutputStream(socket.getOutputStream()),
                    new DataInputStream(socket.getInputStream())
            );
            inputOutputHandler.start();
            this.inputOutputHandler.addListener(evt -> handleInputs(((Packet) evt.getNewValue())));
        } catch (IOException e) {
            System.out.println("couldn't connect");
            e.printStackTrace();
        }
    }

    private void handleInputs(Packet packet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        if (packet.getTopic().equals("app") && packet.getSubject().equals("database")) receiveDatabase(packet);
        if (packet.getTopic().equals("app") && packet.getSubject().equals("chat database")) receiveChatDatabase(packet);
        if (packet.getTopic().equals("database")) switch (packet.getSubject()) {
            case "register":
                database.addUser(gson.fromJson(packet.getValue(), User.class));
                break;
            case "change password":
                database.getUserByUsername(packet.getArgs()[0]).changePasswords(MainController.getSHA256(packet.getValue()));
                break;
            case "login":
                database.getUserByUsername(packet.getValue()).setOnline(true);
                break;
            case "change username":
                database.getUserByUsername(packet.getArgs()[0]).setUsername(packet.getValue());
                break;
            case "change nickname":
                database.getUserByUsername(packet.getArgs()[0]).setNickName(packet.getValue());
                break;
            case "change email":
                database.getUserByUsername(packet.getArgs()[0]).setEmail(packet.getValue());
                break;
            case "change slogan":
                database.getUserByUsername(packet.getArgs()[0]).setSlogan(packet.getValue());
                break;
            case "remove slogan":
                database.getUserByUsername(packet.getArgs()[0]).setSlogan(null);
                break;
            case "set avatar":
                database.getUserByUsername(packet.getArgs()[0]).setCurrentAvatar(Integer.parseInt(packet.getValue()));
                break;
            case "logout":
                database.getUserByUsername(packet.getValue()).setOnline(false);
                database.getUserByUsername(packet.getValue()).setLastSessionToNow();
                break;
        }
    }

    private void receiveChatDatabase(Packet packet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        chatDatabase = gson.fromJson(packet.getValue(), ChatDatabase.class);
        System.out.println("chat database received");
    }

    private void receiveDatabase(Packet packet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        database = gson.fromJson(packet.getValue(), Database.class);
        System.out.println("database received");
        for (PropertyChangeListener l : updateListeners)
            l.propertyChange(new PropertyChangeEvent("", "", 0, 1));
    }

    public void saveUserInfo(String username, String password, String nickname, String slogan, String email) {
        userInfo = new UserInfo(username, password, nickname, slogan, email);
    }

    public void saveUserRecovery(int questionNumber, String answer) {
        userInfo.setRecovery(new Pair<>(questionNumber, MainController.getSHA256(answer)));
    }

    public void saveUser() {
        database.addUser(userInfo.toUser());
        database.saveDataIntoFile();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Packet packet = new Packet("register", "register", null, gson.toJson(userInfo.toUser()));
        inputOutputHandler.sendPacket(packet);
    }

    public void setCurrentUser(String username) {
        currentUser = database.getUserByUsername(username);
        if (currentUser == null) System.out.println("not found");
    }

    public void setCurrentUserPassword(String password) {
        Packet packet = new Packet("profile", "forgot password", new String[]{currentUser.getUsername()}, password);
        inputOutputHandler.sendPacket(packet);
        database.getUserByUsername(currentUser.getUsername()).changePasswords(MainController.getSHA256(password));
        database.saveDataIntoFile();
    }

    public void addListener(PropertyChangeListener listener) {
        this.updateListeners.add(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        this.updateListeners.remove(listener);
    }

    public void updateListeners(String name) {
        for (PropertyChangeListener updateListener : this.updateListeners)
            updateListener.propertyChange(new PropertyChangeEvent(this, name, 1, 1));
    }


    public void run(MenusName currentMenu) throws Exception {
        switch (currentMenu) {
            case LOGIN_MENU:
                LoginMenu loginMenu = new LoginMenu(this);
                loginMenu.start(stage);
                break;
            case FORGOT_PASSWORD_MENU:
                ForgotPasswordMenu forgotPasswordMenu = new ForgotPasswordMenu(this);
                forgotPasswordMenu.start(stage);
                break;
            case SECURITY_QUESTION_CONFIRM_MENU:
                SecurityQuestionConfirmMenu securityQuestionConfirmMenu = new SecurityQuestionConfirmMenu(this);
                securityQuestionConfirmMenu.start(stage);
                break;
            case REGISTER_MENU:
                RegisterMenu registerMenu = new RegisterMenu(this);
                registerMenu.start(stage);
                break;
            case SECURITY_QUESTION_CHOOSE_MENU:
                SecurityQuestionChooseMenu securityQuestionChooseMenu = new SecurityQuestionChooseMenu(this);
                securityQuestionChooseMenu.start(stage);
                break;
            case CAPTCHA_MENU:
                CaptchaMenu captchaMenu = new CaptchaMenu(this);
                captchaMenu.start(stage);
                break;
            case MAIN_MENU:
                MainMenu mainMenu = new MainMenu(this);
                mainMenu.start(stage);
                break;
            case PROFILE_MENU:
                ProfileMenu profileMenu = new ProfileMenu(this);
                profileMenu.start(stage);
                break;
            case CHANGE_PASSWORD_MENU:
                ChangePasswordMenu changePasswordMenu = new ChangePasswordMenu(this);
                changePasswordMenu.start(stage);
                break;
            case LEADER_BOARD_MENU:
                LeaderBoardMenu leaderBoardMenu = new LeaderBoardMenu(this);
                leaderBoardMenu.start(stage);
                break;
            case CHOOSE_AVATAR_MENU:
                ChooseAvatarMenu chooseAvatarMenu = new ChooseAvatarMenu(this);
                chooseAvatarMenu.start(stage);
                break;
            case COPY_AVATAR_MENU:
                CopyAvatarMenu copyAvatarMenu = new CopyAvatarMenu(this);
                copyAvatarMenu.start(stage);
                break;
            case ENTER_CREATE_MAP_MENU:
                CreateMapMenu createMapMenu = new CreateMapMenu(this);
                createMapMenu.start(stage);
                break;
            case ENTER_MAP_MENU:
                EnterMapMenu enterMapMenu = new EnterMapMenu(this);
                enterMapMenu.start(stage);
                break;
            case CHAT_MENU:
                ChatMenu chatMenu = new ChatMenu(this);
                chatMenu.start(stage);
                break;
        }
    }

    public Controller getControllerForMenu(ControllersName name, Control control) {
        if (control.listener() != null) inputOutputHandler.addListener(control.listener());
        switch (name) {
            case LOGIN:
                return new LoginController(inputOutputHandler, database);
            case REGISTER:
                return new RegisterController(inputOutputHandler, database);
            case PROFILE:
                return new ProfileController(inputOutputHandler, database, currentUser);
            case LEADER_BOARD:
                return new LeaderBoardController(inputOutputHandler, database);
            case CREATE_MAP:
                return new CreateMapController(inputOutputHandler, database);
            case ENTER_MAP:
                return new EnterMapController(inputOutputHandler, database, currentUser);
        }
        return null;
    }

    public void saveData() {
        Packet packet = new Packet("database", "save data", null, "");
        inputOutputHandler.sendPacket(packet);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser.setOnline(false);
        currentUser.setLastSessionToNow();
        Packet packet = new Packet("profile", "logout", null, "");
        inputOutputHandler.sendPacket(packet);
    }
}
