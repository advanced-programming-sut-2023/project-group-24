package view.controls.profile;

import controller.ControllersName;
import controller.MenusName;
import controller.ProfileController;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.controls.Control;

import java.awt.*;
import java.awt.image.ImageConsumer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChooseAvatarMenuController extends Control {
    public ScrollPane scrollPane;
    public Button confirm;
    public Button cancel;
    public VBox scrollContent;
    public HBox dragAndDrop;

    private ProfileController profileController;

    public void confirm(MouseEvent mouseEvent) {
        getApp().updateListeners("update avatar");
        getStage().close();
    }

    public void copyMenu(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.COPY_AVATAR_MENU);
    }

    @Override
    public void run() {
        this.profileController = (ProfileController) getApp().getControllerForMenu(ControllersName.PROFILE);
        updateScrollContent();
        setUpDragAndDrop();

        scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setUpDragAndDrop() {
        dragAndDrop.setOnDragOver(this::handleDragOver);
        dragAndDrop.setOnDragDropped(this::handleDragDropped);
        ImageView imageView =
                new ImageView(new Image(getClass().getResource("/images/menus/drop.png").toExternalForm()));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setTranslateX(20);
        dragAndDrop.getChildren().add(imageView);
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), imageView);
        transition.setCycleCount(-1);
        transition.setByY(20);
        transition.setAutoReverse(true);
        transition.play();
    }

    private void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = db.hasFiles();
        /* let the source know whether the string was successfully
         * transferred and used */
        if (db.getFiles().get(0).getAbsolutePath().endsWith(".png")) {
            profileController.addAvatar(db.getFiles().get(0).getAbsolutePath());
            updateScrollContent();
        }
        event.setDropCompleted(success);

        event.consume();
    }

    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != dragAndDrop && event.getDragboard().hasFiles())
            if (event.getDragboard().getFiles().get(0).getAbsolutePath().endsWith(".png"))
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }

    private void updateScrollContent() {
        scrollContent.getChildren().clear();
        for (String path : profileController.getAllAvatarsPath()) {
            Button button = new Button();
            try {
                URI uri = new URI(path);
                button.setOnMouseClicked(mouseEvent -> selectAvatar(uri));
            } catch (URISyntaxException ignored) {
            }
            button.setMinHeight(100);
            button.setMinWidth(100);
            button.setMaxHeight(100);
            button.setMaxWidth(100);
            button.setStyle("-fx-background-image: url(\""  + path + "\");-fx-background-size: stretch;");
            scrollContent.getChildren().add(button);
        }
        Button selected = (Button) scrollContent.getChildren().get(profileController.getCurrentAvatarNumber());
        selected.getStyleClass().add("selected");
    }

    private void selectAvatar(URI path) {
        profileController.setCurrentAvatar(path);
        int selected = profileController.getCurrentAvatarNumber();
        for (int i = 0; i < scrollContent.getChildren().size(); i++) {
            if (i != selected) scrollContent.getChildren().get(i).getStyleClass().removeAll("selected");
            else scrollContent.getChildren().get(i).getStyleClass().add("selected");
        }
    }
}
