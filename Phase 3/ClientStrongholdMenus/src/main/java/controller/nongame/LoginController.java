package controller.nongame;

import controller.*;
import controller.captchacontrollers.CaptchaGenerator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Packet;
import model.User;
import model.databases.Database;
import model.enums.RecoveryQuestion;
import view.controls.Control;
import view.enums.messages.LoginMenuMessages;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class LoginController implements Controller {
    private final Database database;
    private int numberOfIncorrectPassword;
    private String captchaText;
    private InputOutputHandler ioHandler;

    public LoginController(InputOutputHandler ioHandler, Database database) {
        this.ioHandler = ioHandler;
        this.database = database;
        this.numberOfIncorrectPassword = 0;
    }

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        User user = database.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USER_NOT_FOUND;
        else if (user.isOnline()) return LoginMenuMessages.AUTHENTICATION_ERROR;
        else if (!user.isPasswordCorrect(MainController.getSHA256(password))) {
            numberOfIncorrectPassword++;
            return LoginMenuMessages.INCORRECT_PASSWORD;
        }
        numberOfIncorrectPassword = 0;
        if (stayLoggedIn) database.setStayedLoggedInUser(user);
        ioHandler.sendPacket(new Packet("login", "login", null, username));
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
}
