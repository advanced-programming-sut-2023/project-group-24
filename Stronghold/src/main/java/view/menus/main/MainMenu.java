package view.menus.main;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.controls.Control;

import java.net.URL;

public class MainMenu extends Application {
    private final AppController app;

    public MainMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/FXML/main/mainMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(stage, app);

        stage.setWidth(pane.getMinWidth());
        stage.setHeight(pane.getMinHeight());
        stage.getScene().setRoot(pane);
        stage.show();
    }
}
