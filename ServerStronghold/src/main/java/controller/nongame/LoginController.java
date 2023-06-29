package controller.nongame;

import controller.Controller;
import controller.MainController;
import controller.MenusName;
import controller.captchacontrollers.CaptchaGenerator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Packet;
import model.User;
import model.databases.Database;
import model.enums.RecoveryQuestion;
import view.enums.messages.LoginMenuMessages;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LoginController implements Controller {
    private final Database database;
    private int numberOfIncorrectPassword;
    private String captchaText;
    private Socket socket;
    private ArrayList<Socket> sockets;

    public LoginController(Database database) {
        this.database = database;
        this.numberOfIncorrectPassword = 0;
    }

    public LoginController(Database database, Socket socket, ArrayList<Socket> sockets) {
        this(database);
        this.socket = socket;
        this.sockets = sockets;
    }

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        User user = database.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USER_NOT_FOUND;
        else if (!user.isPasswordCorrect(MainController.getSHA256(password))) {
            numberOfIncorrectPassword++;
            return LoginMenuMessages.INCORRECT_PASSWORD;
        }
        numberOfIncorrectPassword = 0;
        if (stayLoggedIn) database.setStayedLoggedInUser(user);
        return LoginMenuMessages.SUCCESS;
    }

    public void disableInputIncorrectPassword(Pane mainPane) {
        mainPane.setDisable(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mainPane.setDisable(false);
            }
        }, (numberOfIncorrectPassword * 5) * 1000);
    }

    public void makeDelayForIncorrectPassword() {
        int delayTime = (numberOfIncorrectPassword * 5) * 1000;
        try {
            Robot robot = new Robot();
            changeConsole(robot);
            robot.delay(delayTime);
            changeConsole(robot);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeConsole(Robot robot) {
        robot.delay(300);
        robot.mouseMove(1700, 950);
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(1725, 975);
        robot.delay(30);
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
    }

    public boolean isRecoveryAnswerCorrect(String username, String answer) {
        return database.getUserByUsername(username).isRecoveryAnswerCorrect(MainController.getSHA256(answer));
    }

    public boolean usernameExists(String username) {
        return database.getUserByUsername(username) != null;
    }

    public String getRecoveryQuestion(String username) {
        return RecoveryQuestion.getRecoveryQuestionByNumber(
                database.getUserByUsername(username).getRecoveryQuestionNumber());
    }

    public void generateCaptcha(ImageView captchaImage) {
        File file = new File("info/captcha.png");
        captchaText = CaptchaGenerator.generateRandomCaptcha(200, 50, file.getAbsolutePath());
        captchaImage.setImage(new Image(file.getAbsolutePath()));
    }

    public boolean isCaptchaIncorrect(String captchaText) {
        return captchaText == null || !captchaText.equals(this.captchaText);
    }

    public void handlePacket(Packet packet) {
        switch (packet.getSubject()) {
            case "login":
                database.getUserByUsername(packet.getValue()).setOnline(true);
                sendDataToAllSockets(new Packet("database", "login", null, packet.getValue()));
                break;
        }
    }

    public void sendDataToAllSockets(Packet packet) {
        if (sockets == null) return;
        for (Socket socket : sockets) {
            if (socket.equals(this.socket)) continue;
            try {
                DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
                stream.writeUTF(packet.toJson());
                System.out.println("data sent to " + socket.getPort());
            } catch (IOException e) {
                System.out.println("couldn't send data to " + socket.getInetAddress() + ":" + socket.getPort());
            }
        }
    }
}
