package view.controls.profile;

import controller.AppController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class LeaderBoardMenuController extends Control {
    public ImageView back;

    @FXML
    public void initialize() {
        back.setImage(new Image(AppController.class.getResource("/images/back-icon.png").toExternalForm()));
    }

    public void back(MouseEvent mouseEvent) {
        getStage().close();
    }
}
