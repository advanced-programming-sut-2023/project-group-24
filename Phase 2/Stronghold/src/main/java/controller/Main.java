package controller;

import controller.AppController;
import controller.MenusName;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AppController start = new AppController(stage);
        stage.setResizable(false);
        stage.setScene(new Scene(new Pane()));
        stage.getIcons().add(new Image(getClass().getResource("/images/icons/game-icon.png").toExternalForm()));
        stage.setTitle("Stronghold");

        start.run(MenusName.LOGIN_MENU);
    }
}
