package view.controls.login;

import controller.ControllersName;
import controller.MenusName;
import controller.RegisterController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.controls.Control;
import view.modelview.PasswordInput;

public class ForgotPasswordMenuController extends Control {
    public Label title;
    public PasswordInput passwordField;
    public Label passwordError;
    public PasswordInput confirmPasswordField;
    public Label confirmPasswordError;
    public Button confirm;
    public Button cancel;

    private RegisterController registerController;

    public void cancel() {
        getStage().close();
    }

    public void confirm() {
        if (passwordField.getText().equals("")) passwordError.setText("This field is empty");
        if (confirmPasswordField.getText().equals("")) confirmPasswordError.setText("This field is empty");
        if (!passwordError.getText().equals("") || !confirmPasswordError.getText().equals("")) return;
        getApp().setCurrentUserPassword(passwordField.getText());
        getStage().close();
    }

    @Override
    public void run() {
        registerController = (RegisterController) getApp().getControllerForMenu(ControllersName.REGISTER);
        setUpText();
        passwordField.textProperty().addListener((observableValue, s, t1) -> passwordErrors());
        confirmPasswordField.textProperty().addListener((observableValue, s, t1) -> passwordErrors());
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

    private void setUpText() {
        Font small = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 17);
        Font medium = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        Font mini = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 11);
        title.setFont(small);
        passwordField.setFont(medium);
        confirmPasswordField.setFont(medium);
        cancel.setFont(medium);
        confirm.setFont(medium);
        confirmPasswordError.setFont(mini);
        passwordError.setFont(mini);
        title.setTextFill(Color.rgb(101, 166, 27));
        confirmPasswordField.setPromptText("confirm password");
    }
}
