package view.controls.login;

import controller.MenusName;
import controller.RegisterController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.modelview.PasswordInput;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.io.InputStream;

public class RegisterMenuController extends Control {
    public Button back;
    public TextField usernameField;
    public PasswordInput passwordField;
    public PasswordInput confirmPasswordField;
    public TextField nicknameField;
    public TextField emailField;
    public TextField sloganField;
    public Text haveCustomSloganText;
    public Label userNameError;
    public Label passwordError;
    public Label confirmPasswordError;
    public Label emailError;
    public CheckBox customSlogan;
    public Label nicknameError;
    public Label sloganError;

    private RegisterController registerController;

    public void register() throws Exception {
        if (usernameField.getText().equals("")) userNameError.setText("This field is empty");
        if (passwordField.getText().equals("")) passwordError.setText("This field is empty");
        if (nicknameField.getText().equals("")) nicknameError.setText("This field is empty");
        if (confirmPasswordField.getText().equals("")) confirmPasswordError.setText("This field is empty");
        if (emailField.getText().equals("")) emailError.setText("This field is empty");
        if (sloganField.getText().equals("")) sloganError.setText("This field is empty");
        if (userNameError.getText().equals("")
                && passwordError.getText().equals("")
                && nicknameError.getText().equals("")
                && confirmPasswordError.getText().equals("")
                && emailError.getText().equals("")
                && sloganError.getText().equals(""))
            getApp().run(MenusName.SECURITY_QUESTION_CHOOSE_MENU);
    }

    public void back() throws Exception {
        getApp().run(MenusName.LOGIN_MENU);
    }

    @Override
    public void run() {
        registerController = (RegisterController) getApp().getControllerForMenu(MenusName.REGISTER_MENU);
        setUpFont();
        setUpErrors();
        sloganField.disableProperty().bind(customSlogan.selectedProperty().not());
        customSlogan.setOnAction(actionEvent -> customSloganSelect());
        randomSlogan();
    }

    private void customSloganSelect() {
        if (!customSlogan.isSelected()) randomSlogan();
    }

    @FXML
    private void randomSlogan() {
        sloganField.setText(registerController.makeRandomSlogan());
    }

    private void setUpErrors() {
        usernameField.textProperty().addListener((observableValue, s, t1) -> usernameErrors());
        passwordField.textProperty().addListener((observableValue, s, t1) -> passwordErrors());
        confirmPasswordField.textProperty().addListener((observableValue, s, t1) -> passwordErrors());
        emailField.textProperty().addListener((observableValue, s, t1) -> emailErrors());
        nicknameField.textProperty().addListener((observableValue, s, t1) -> nicknameError.setText(""));
        sloganField.textProperty().addListener((observableValue, s, t1) -> sloganError.setText(""));
    }

    private void emailErrors() {
        switch (registerController.checkEmailErrors(emailField.getText())) {
            case DUPLICATE_EMAIL:
                emailError.setText("This email is signed in with another account");
                break;
            case INVALID_EMAIL:
                emailError.setText("This email is invalid");
                break;
            default:
                emailError.setText("");
                break;
        }
    }

    private void passwordErrors() {
        switch (registerController.checkPasswordErrors(passwordField.getText(), confirmPasswordField.getText())) {
            case SHORT_PASSWORD:
                passwordError.setText("The password is too short");
                confirmPasswordError.setText("");
                break;
            case NON_CAPITAL_PASSWORD:
                passwordError.setText("There are no capital letters in you password");
                confirmPasswordError.setText("");
                break;
            case NON_SMALL_PASSWORD:
                passwordError.setText("There are no lowercase letters in you password");
                confirmPasswordError.setText("");
                break;
            case NON_NUMBER_PASSWORD:
                passwordError.setText("There are no digits in you password");
                confirmPasswordError.setText("");
                break;
            case NON_SPECIFIC_PASSWORD:
                passwordError.setText("There are no special characters in you password");
                confirmPasswordError.setText("");
                break;
            case INCORRECT_PASSWORD_CONFIRM:
                passwordError.setText("");
                confirmPasswordError.setText("The confirm password doesn't match your password");
                break;
            default:
                passwordError.setText("");
                confirmPasswordError.setText("");
                break;
        }
    }

    private void usernameErrors() {
        switch (registerController.checkUsernameErrors(usernameField.getText())) {
            case INVALID_USERNAME:
                userNameError.setText("There are invalid characters in your username");
                break;
            case DUPLICATE_USERNAME:
                userNameError.setText("This username is already taken");
                break;
            default:
                userNameError.setText("");
                break;
        }
    }

    private void setUpFont() {
        if (getClass().getResourceAsStream("/fonts/Seagram.ttf") != null) {
            Font normal = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
            Font small = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 17);
            Font tiny = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 14);
            Font mini = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 11);
            confirmPasswordField.setFont(normal);
            passwordField.setFont(normal);
            usernameField.setFont(normal);
            nicknameField.setFont(normal);
            emailField.setFont(small);
            sloganField.setFont(tiny);
            haveCustomSloganText.setFont(tiny);
            haveCustomSloganText.setFill(Color.valueOf("#a08005"));
            emailError.setFont(mini);
            confirmPasswordError.setFont(mini);
            passwordError.setFont(mini);
            userNameError.setFont(mini);
            nicknameError.setFont(mini);
            sloganError.setFont(mini);
        } else System.out.println("font not loaded");
    }
}
