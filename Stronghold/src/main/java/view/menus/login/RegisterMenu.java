package view.menus.login;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.controls.Control;

import java.net.URL;

public class RegisterMenu extends Application {
    private final AppController app;

    public RegisterMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/login/registerMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(stage, app);

        stage.getScene().setRoot(pane);
        stage.show();
    }
}
