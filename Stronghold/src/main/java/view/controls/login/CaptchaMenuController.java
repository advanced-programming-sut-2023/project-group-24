package view.controls.login;

import controller.ControllersName;
import controller.LoginController;
import controller.MenusName;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.controls.Control;

public class CaptchaMenuController extends Control {
    public Label error;
    public TextField captchaField;
    public ImageView captchaImage;
    public Label title;
    public Button confirm;
    public Button cancel;

    private LoginController loginController;

    public void cancel() {
        getStage().close();
    }

    public void confirm() {
        if (loginController.isCaptchaIncorrect(captchaField.getText())) {
            error.setText("Incorrect Captcha");
            anotherCaptcha();
            return;
        }
        getApp().saveUser();
        getStage().close();
    }

    public void anotherCaptcha() {
        loginController.generateCaptcha(captchaImage);
        captchaField.setText("");
    }

    @Override
    public void run() {
        this.loginController = (LoginController) getApp().getControllerForMenu(ControllersName.LOGIN);
        setUpText();
        anotherCaptcha();
        captchaField.textProperty().addListener((observableValue, s, t1) -> preventLettersInCaptcha(s, t1));
    }

    private void preventLettersInCaptcha(String s, String t1) {
        if (!t1.matches("\\d*")) captchaField.setText(s);
    }

    private void setUpText() {
        Font small = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 17);
        Font medium = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        Font mini = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 11);
        title.setFont(small);
        cancel.setFont(medium);
        confirm.setFont(medium);
        error.setFont(mini);
        captchaField.setFont(medium);
        title.setTextFill(Color.rgb(101, 166, 27));
    }
}
