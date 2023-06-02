package view.controls.login;

import controller.MenusName;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import view.controls.Control;
import view.menus.main.MainMenu;

import java.io.InputStream;

public class LoginMenuController extends Control {
    public TextField usernameField;
    public BorderPane mainPane;
    public PasswordField passwordFieldHidden;
    public TextField passwordFieldShown;

    public void forgotPassword(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.SECURITY_QUESTION_CONFIRM_MENU);
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.REGISTER_MENU);
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.MAIN_MENU);
    }

    @Override
    public void run() {
        setUpFont();
    }

    private void setUpFont() {
        InputStream fontStream = getClass().getResourceAsStream("/fonts/seagram tfb.ttf");
        if (fontStream != null) {
            Font font = Font.loadFont(fontStream, 20);
            usernameField.setFont(font);
        } else System.out.println("font not found");
    }
}
