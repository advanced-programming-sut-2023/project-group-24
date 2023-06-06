package view.controls.login;

import controller.MenusName;
import controller.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    private RegisterController registerController;

    public void register() throws Exception {
        getApp().run(MenusName.SECURITY_QUESTION_CHOOSE_MENU);
    }

    public void back() throws Exception {
        getApp().run(MenusName.LOGIN_MENU);
    }

    @Override
    public void run() {
        registerController = (RegisterController) getApp().getControllerForMenu(MenusName.REGISTER_MENU);
        setUpFont();
    }

    private void setUpFont() {
        InputStream fontStream = getClass().getResourceAsStream("/fonts/Seagram.ttf");
        InputStream fontStream2 = getClass().getResourceAsStream("/fonts/Seagram.ttf");
        InputStream fontStream3 = getClass().getResourceAsStream("/fonts/Seagram.ttf");
        if (fontStream != null) {
            Font font = Font.loadFont(fontStream, 24);
            confirmPasswordField.setFont(font);
            passwordField.setFont(font);
            usernameField.setFont(font);
            nicknameField.setFont(font);
            Font small = Font.loadFont(fontStream2, 18);
            emailField.setFont(small);
            Font tiny = Font.loadFont(fontStream3, 14);
            sloganField.setFont(tiny);
            haveCustomSloganText.setFont(tiny);
            haveCustomSloganText.setFill(Color.valueOf("#a08005"));
        } else System.out.println("font not loaded");
    }
}
