package view.menus.profile;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.net.URL;

public class ProfileMenu extends Application {
    private final AppController app;

    public ProfileMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = ProfileMenu.class.getResource("/FXML/profile/profileMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(stage, app);
        stage.setWidth(pane.getPrefWidth());
        stage.setHeight(pane.getPrefHeight());
        stage.setX(250);
        stage.setY(25);

        stage.getScene().setRoot(pane);
        stage.show();
    }
}
