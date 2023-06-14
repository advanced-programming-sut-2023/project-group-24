package view.controls.profile;

import controller.ControllersName;
import controller.MenusName;
import controller.ProfileController;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.enums.Slogan;
import view.controls.Control;
import view.enums.messages.ProfileMenuMessages;

import java.beans.PropertyChangeListener;

public class ProfileMenuController extends Control {
    public Button back;
    public Rectangle avatar;
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
    public CheckBox customSlogan;
    public HBox sloganContainer;
    public Button sloganEdit;
    public Button emailEdit;
    public Button nicknameEdit;
    public Button usernameEdit;

    private ProfileController profileController;
    private PropertyChangeListener listener;

    public void changePassword() throws Exception {
        getApp().run(MenusName.CHANGE_PASSWORD_MENU);
    }

    public void leaderBoard() throws Exception {
        getApp().run(MenusName.LEADER_BOARD_MENU);
    }

    public void save() {
        if (usernameField.getText().equals("")) usernameError.setText("Empty Field");
        if (nicknameField.getText().equals("")) nicknameError.setText("Empty Field");
        if (emailField.getText().equals("")) emailError.setText("Empty Field");
        if (sloganField.getText().equals("") && customSlogan.isSelected()) sloganError.setText("Empty Field");
        if (!sloganError.getText().equals("") || !emailError.getText().equals("") || !nicknameError.getText().equals("")
                || !usernameError.getText().equals("")) return;
        profileController.changeUsername(usernameField.getText());
        profileController.changeNickname(nicknameField.getText());
        profileController.changeEmail(emailField.getText());
        if (profileController.changeSlogan(sloganField.getText()) == ProfileMenuMessages.NULL_FIELD)
            profileController.removeSlogan();
        disableFields();
        resetFields();
    }

    public void chooseAvatar() throws Exception {
        getApp().run(MenusName.CHOOSE_AVATAR_MENU);
    }

    public void back() throws Exception {
        getApp().removeListener(listener);
        getApp().run(MenusName.MAIN_MENU);
    }

    public void randomSlogan() {
        String currentSlogan = sloganField.getText();
        String nextSlogan = Slogan.getRandomSlogan();
        if (currentSlogan.equals(nextSlogan)) {
            randomSlogan();
            return;
        }
        sloganField.setText(nextSlogan);
    }

    @Override
    public void run() {
        this.profileController = (ProfileController) getApp().getControllerForMenu(ControllersName.PROFILE);
        disableFields();
        resetFields();
        updateAvatar();
        setUpText();
        mainPane.setRight(right);
        this.listener = evt -> updateAvatar();
        getApp().addListener(listener);
        usernameField.textProperty().addListener((observableValue, s, t1) -> usernameErrors(t1));
        emailField.textProperty().addListener((observableValue, s, t1) -> emailErrors(t1));
        nicknameField.textProperty().addListener((observableValue, s, t1) -> nicknameError.setText(""));
        sloganField.textProperty().addListener((observableValue, s, t1) -> sloganError.setText(""));
        customSlogan.selectedProperty().addListener((observableValue, s, t1) -> sloganError.setText(""));

        if (!profileController.getCurrentUser("slogan").equals("")) customSlogan.setSelected(true);
        sloganContainer.visibleProperty().bind(customSlogan.selectedProperty());
        customSlogan.setOnAction((e) -> sloganField.setText(""));
    }

    private void usernameErrors(String to) {
        switch (profileController.checkChangeUsernameErrors(to)) {
            case INVALID_USERNAME:
                usernameError.setText("There are invalid characters in your username");
                break;
            case DUPLICATE_USERNAME:
                usernameError.setText("This username already exists");
                break;
            default:
                usernameError.setText("");
                break;
        }
    }

    private void emailErrors(String newEmail) {
        switch (profileController.checkChangeEmailErrors(newEmail)) {
            case INVALID_EMAIL_FORMAT:
                emailError.setText("Email format is incorrect");
                break;
            case DUPLICATE_EMAIL:
                emailError.setText("This email already exists");
                break;
            default:
                emailError.setText("");
                break;
        }
    }

    private void updateAvatar() {
        Image avatarImage = new Image(profileController.getAvatarPath());
        avatar.setFill(new ImagePattern(avatarImage));
    }

    private void resetFields() {
        usernameField.setText(profileController.getCurrentUser("username"));
        nicknameField.setText(profileController.getCurrentUser("nickname"));
        emailField.setText(profileController.getCurrentUser("email"));
        sloganField.setText(profileController.getCurrentUser("slogan"));
    }

    private void disableFields() {
        sloganField.setDisable(true);
        emailField.setDisable(true);
        nicknameField.setDisable(true);
        usernameField.setDisable(true);
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

    public void enableEdit(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget().equals(sloganEdit)) sloganField.setDisable(false);
        if (mouseEvent.getTarget().equals(emailEdit)) emailField.setDisable(false);
        if (mouseEvent.getTarget().equals(nicknameEdit)) nicknameField.setDisable(false);
        if (mouseEvent.getTarget().equals(usernameEdit)) usernameField.setDisable(false);
    }
}
