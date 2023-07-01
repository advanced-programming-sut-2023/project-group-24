package view.controls.main;

import controller.ControllersName;
import controller.nongame.EnterMapController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.controls.Control;

import java.util.Collections;

public class EnterMapMenuController extends Control {
    public VBox userContainer;
    public TextField username;
    public Label idError;
    public TextField mapId;

    private EnterMapController enterMapController;

    public void confirm() {
        if (enterMapController.startGame()) getStage().close();
    }

    public void cancel() {
        getStage().close();
    }

    @Override
    public void run() {
        this.enterMapController = (EnterMapController) getApp().getControllerForMenu(ControllersName.ENTER_MAP);
        mapId.setOnKeyPressed(this::keyPressedMap);
        username.setOnKeyPressed(this::keyPressedUser);
        username.setVisible(false);
        userContainer.visibleProperty().bind(username.visibleProperty());
        addUser(getApp().getCurrentUser().getUsername());
    }

    private void keyPressedUser(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) chooseUser();
    }

    private void keyPressedMap(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) chooseMap();
    }

    public void chooseMap() {
        if (!enterMapController.mapIdExists(mapId.getText())) {
            idError.setText("this map does not exist");
            return;
        }
        mapId.setDisable(true);
        username.setVisible(true);
        enterMapController.selectMap(mapId.getText());
    }

    private void chooseUser() {
        if (enterMapController.isInvalidUser(username.getText())) return;
        enterMapController.addUser(username.getText());
        addUser(username.getText());
        username.setText("");
    }

    private void addUser(String username) {
        ImageView imageView = new ImageView(new Image(enterMapController.getAvatarPath(username)));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Text text = new Text(username);
        text.setFill(Color.WHITE);
        HBox hBox = new HBox(imageView, text);
        hBox.setSpacing(10);
        userContainer.getChildren().add(hBox);
    }
}
