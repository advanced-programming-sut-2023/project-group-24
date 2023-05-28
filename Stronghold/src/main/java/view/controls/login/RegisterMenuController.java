package view.controls.login;

import controller.MenusName;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.controls.Control;
import view.menus.login.LoginMenu;

public class RegisterMenuController extends Control {
    public ImageView back;

    @FXML
    public void initialize() {
        back.setImage(new Image(LoginMenu.class.getResource("/images/back-icon.png").toExternalForm()));
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.SECURITY_QUESTION_CHOOSE_MENU);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.LOGIN_MENU);
    }
}
