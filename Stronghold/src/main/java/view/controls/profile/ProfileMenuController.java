package view.controls.profile;

import controller.AppController;
import controller.MenusName;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.awt.*;

public class ProfileMenuController extends Control {
    public Button back;
    public Button avatar;
    public VBox right;
    public BorderPane mainPane;
    public TextField usernameField;
    public TextField nicknameField;
    public TextField emailField;
    public TextField sloganField;
    public Text haveCustomSloganText;
    public Button changePasswordButton;
    public Button leaderBoardButton;
    public Label sloganError;
    public Label emailError;
    public Label nicknameError;
    public Label usernameError;
    public Button saveButton;

    private Image avatarImage;

    @FXML
    public void initialize() {
//        back.setImage(new Image(LoginMenu.class.getResource("/images/back-icon.png").toExternalForm()));
//        avatar.setImage(new Image(LoginMenu.class.getResource("/images/back-icon.png").toExternalForm()));
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.CHANGE_PASSWORD_MENU);
    }

    public void leaderBoard(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.LEADER_BOARD_MENU);
    }

    public void save(MouseEvent mouseEvent) {
    }

    public void chooseAvatar(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.CHOOSE_AVATAR_MENU);
    }

    @Override
    public void run() {
        mainPane.setRight(right);
        avatarImage = new Image(getClass().getResource("/images/icons/game-icon.png").toExternalForm());
        avatar.setStyle("-fx-background-image: url(\""  + avatarImage.getUrl() + "\");");
        
        setUpText();
    }

    private void setUpText() {
        Font big = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 24);
        Font normal = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        Font small = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 17);
        Font tiny = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 14);
        Font mini = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 11);
        usernameField.setFont(normal);
        nicknameField.setFont(normal);
        emailField.setFont(small);
        sloganField.setFont(tiny);
        haveCustomSloganText.setFont(tiny);
        haveCustomSloganText.setFill(Color.valueOf("#2414b3"));
        emailError.setFont(mini);
        usernameError.setFont(mini);
        nicknameError.setFont(mini);
        sloganError.setFont(mini);
        leaderBoardButton.setFont(big);
        changePasswordButton.setFont(big);
        saveButton.setFont(big);
    }
}
