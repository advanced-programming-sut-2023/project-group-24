package view.menus.login;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.controls.Control;
import view.controls.login.LoginMenuController;

import java.net.URL;

public class LoginMenu extends Application {
    private final AppController app;

    public LoginMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/login/loginMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(stage, app);

        stage.getScene().setRoot(pane);
        stage.show();
    }
}
