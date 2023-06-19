package view.controls.profile;

import controller.ControllersName;
import controller.nongame.LoginController;
import controller.nongame.ProfileController;
import controller.nongame.RegisterController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import view.controls.Control;
import view.modelview.PasswordInput;

public class ChangePasswordMenuController extends Control {
    public Button confirm;
    public Button cancel;
    public PasswordInput confirmPassword;
    public PasswordInput newPassword;
    public PasswordInput oldPassword;
    public TextField captchaField;
    public ImageView captchaImage;
    public Label captchaError;
    public Label confirmPasswordError;
    public Label passwordError;
    public Label oldPasswordError;

    private LoginController loginController;
    private ProfileController profileController;
    private RegisterController registerController;

    public void cancel() {
        getStage().close();
    }

    public void confirm() {
        if (oldPassword.getText().equals("")) oldPasswordError.setText("This field is empty");
        if (newPassword.getText().equals("")) passwordError.setText("This field is empty");
        if (confirmPassword.getText().equals("")) confirmPasswordError.setText("This field is empty");
        if (loginController.isCaptchaIncorrect(captchaField.getText())) captchaError.setText("Incorrect captcha");
        anotherCaptcha();
        switch (profileController.checkChangePasswordErrors(oldPassword.getText(), newPassword.getText())) {
            case INCORRECT_PASSWORD:
                oldPasswordError.setText("wrong password");
                break;
            case DUPLICATE_PASSWORD:
                if (errorFound()) return;
                passwordError.setText("The new password is the same as your old one");
                break;
        }
        if (errorFound()) return;
        profileController.changePassword(newPassword.getText(), confirmPassword.getText());
        getStage().close();
    }

    private boolean errorFound() {
        return !passwordError.getText().equals("")
                || !confirmPasswordError.getText().equals("")
                || !oldPasswordError.getText().equals("");
    }

    public void anotherCaptcha() {
        loginController.generateCaptcha(captchaImage);
        captchaField.setText("");
    }

    @Override
    public void run() {
        profileController = (ProfileController) getApp().getControllerForMenu(ControllersName.PROFILE);
        loginController = (LoginController) getApp().getControllerForMenu(ControllersName.LOGIN);
        registerController = (RegisterController) getApp().getControllerForMenu(ControllersName.REGISTER);
        setUpText();
        anotherCaptcha();
        newPassword.textProperty().addListener((observableValue, s, t1) -> passwordErrors());
        confirmPassword.textProperty().addListener((observableValue, s, t1) -> passwordErrors());
        oldPassword.textProperty().addListener((observableValue, s, t1) -> oldPasswordError.setText(""));
        captchaField.textProperty().addListener((observableValue, s, t1) -> checkCaptchaChars(s, t1));
    }

    private void checkCaptchaChars(String from, String to) {
        if (!to.matches("\\d*")) captchaField.setText(from);
        captchaError.setText("");
    }

    private void passwordErrors() {
        switch (registerController.checkPasswordErrors(newPassword.getText(), confirmPassword.getText())) {
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
        Font big = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 25);
        Font normal = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        Font mini = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 11);
        cancel.setFont(big);
        confirm.setFont(big);
        oldPassword.setFont(normal);
        newPassword.setFont(normal);
        confirmPassword.setFont(normal);
        captchaField.setFont(normal);
        oldPassword.setPromptText("old password");
        newPassword.setPromptText("new password");
        confirmPassword.setPromptText("confirm new password");
        captchaError.setFont(mini);
        oldPasswordError.setFont(mini);
        passwordError.setFont(mini);
        confirmPasswordError.setFont(mini);
    }
}
