package controller.nongame;

import controller.Controller;
import controller.InputOutputHandler;
import controller.captchacontrollers.CaptchaGenerator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Packet;
import view.controls.Control;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class LoginController implements Controller {
    private final InputOutputHandler inputOutputHandler;
    private int numberOfIncorrectPassword;
    private String captchaText;

    public LoginController(InputOutputHandler inputOutputHandler, Control control) {
        this.inputOutputHandler = inputOutputHandler;
        this.numberOfIncorrectPassword = 0;
        inputOutputHandler.addListener(evt -> {
            if (evt.getPropertyName().equals("login"))
                control.listener().propertyChange(evt);
            if (((Packet) evt.getNewValue()).getValue().equals("INCORRECT_PASSWORD")) numberOfIncorrectPassword++;
        });
    }

    public void requestLoginUser(String username, String password, boolean stayLoggedIn) {
        Packet packet = new Packet("login", "login user", new String[]{
                username,
                password,
                String.valueOf(stayLoggedIn)
        }, "");
        inputOutputHandler.sendPacket(packet);
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

    public void requestIsRecoveryAnswerCorrect(String username, String answer) {
        Packet packet = new Packet("login", "is recovery answer correct", new String[]{
                username,
                answer,
        }, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void requestUsernameExists(String username) {
        Packet packet = new Packet("login", "username exists", null, username);
        inputOutputHandler.sendPacket(packet);
    }

    public void requestRecoveryQuestion(String username) {
        Packet packet = new Packet("login", "username exists", null, username);
        inputOutputHandler.sendPacket(packet);
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
