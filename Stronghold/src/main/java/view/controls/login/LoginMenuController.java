package view.controls.login;

import controller.AppController;
import controller.LoginController;
import controller.MenusName;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import model.modelview.PasswordInput;
import view.controls.Control;
import view.enums.messages.LoginMenuMessages;

import java.io.InputStream;

public class LoginMenuController extends Control {
    public TextField usernameField;
    public BorderPane mainPane;
    public PasswordInput passwordInput;
    public Hyperlink register;
    public Hyperlink forgotPassword;
    public ImageView captchaImage;
    public TextField captchaField;

    private LoginController loginController;

    public void forgotPassword(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.SECURITY_QUESTION_CONFIRM_MENU);
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.REGISTER_MENU);
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        if (usernameField.getText().equals("")) showError("Empty Field", "Username field is empty");
        else if (passwordInput.getText().equals("")) showError("Empty Field", "Password field is empty");
        else if (captchaField.getText().equals("")) showError("Empty Field", "Captcha field is empty");
        else if (loginController.isCaptchaIncorrect(captchaField.getText()))
            showError("Incorrect Captcha", "The captcha is incorrect");
        else switch (loginController.loginUser(usernameField.getText(), passwordInput.getText(),false)) {
            case USER_NOT_FOUND:
                showError("Login Unsuccessful", "No users with this username were found.");
                break;
            case INCORRECT_PASSWORD:
                showError("Login Unsuccessful", "The password is incorrect");
                loginController.disableInputIncorrectPassword(mainPane);
                break;
            case SUCCESS:
                getApp().run(MenusName.MAIN_MENU);
                break;
        }
    }

    @Override
    public void run() {
        loginController = (LoginController) getApp().getControllerForMenu(MenusName.LOGIN_MENU);
        loginController.generateCaptcha(captchaImage);
        setUpFont();
    }

    private void setUpFont() {
        InputStream fontStream = getClass().getResourceAsStream("/fonts/Seagram.ttf");
        InputStream fontStream2 = getClass().getResourceAsStream("/fonts/Seagram.ttf");
        if (fontStream != null) {
            Font font = Font.loadFont(fontStream, 24);
            usernameField.setFont(font);
            passwordInput.setFont(font);
            Font small = Font.loadFont(fontStream2, 18);
            register.setFont(small);
            forgotPassword.setFont(small);
        } else System.out.println("font not found");
    }

    private void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initOwner(getStage());
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/CSS/alert.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        alert.show();
    }

    public void anotherCaptcha(MouseEvent mouseEvent) {
        loginController.generateCaptcha(captchaImage);
    }
}
