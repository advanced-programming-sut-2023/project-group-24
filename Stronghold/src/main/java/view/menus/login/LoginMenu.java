package view.menus.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.controls.login.LoginMenuController;

import java.net.URL;

public class LoginMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/login/loginMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((LoginMenuController) fxmlLoader.getController()).setUp(stage);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
