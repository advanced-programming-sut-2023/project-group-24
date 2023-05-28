package view.controls.profile;

import controller.AppController;
import controller.MenusName;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.awt.*;

public class ProfileMenuController extends Control {
    public ImageView back;
    public ImageView avatar;

    @FXML
    public void initialize() {
        back.setImage(new Image(LoginMenu.class.getResource("/images/back-icon.png").toExternalForm()));
        avatar.setImage(new Image(LoginMenu.class.getResource("/images/back-icon.png").toExternalForm()));
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.CHANGE_PASSWORD_MENU);
    }

    public void leaderBoard(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.LEADER_BOARD_MENU);
    }
}
