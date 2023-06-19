package view.controls.profile;

import controller.ControllersName;
import controller.nongame.LoginController;
import controller.nongame.ProfileController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.controls.Control;

public class CopyAvatarMenuController extends Control {
    public Button confirm;
    public Button cancel;
    public TextField usernameField;
    public HBox infoContainer;
    public ImageView avatar;
    public Text username;

    private ProfileController profileController;
    private LoginController loginController;

    public void cancel() {
        getStage().close();
    }

    public void confirm() {
        profileController.copyAvatar(usernameField.getText());
        getApp().updateListeners("copied avatar");
        getStage().close();
    }

    @Override
    public void run() {
        profileController = (ProfileController) getApp().getControllerForMenu(ControllersName.PROFILE);
        loginController = (LoginController) getApp().getControllerForMenu(ControllersName.LOGIN);
        usernameField.textProperty().addListener((observableValue, s, t1) -> updateAvatar());
        confirm.setVisible(false);
        infoContainer.setVisible(false);
        setUpFont();
    }

    private void setUpFont() {
        Font normal = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        usernameField.setFont(normal);
        confirm.setFont(normal);
        cancel.setFont(normal);
        username.setFont(normal);
    }

    private void updateAvatar() {
        if (!loginController.usernameExists(usernameField.getText())) {
            infoContainer.setVisible(false);
            confirm.setVisible(false);
            return;
        }
        avatar.setImage(new Image(profileController.getAvatarPath(usernameField.getText())));
        username.setText(usernameField.getText());
        infoContainer.setVisible(true);
        confirm.setVisible(true);
    }
}
