package view.menus.login;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.controls.Control;

import java.net.URL;

public class SecurityQuestionChooseMenu extends Application {
    private AppController app;

    public SecurityQuestionChooseMenu(controller.AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.initStyle(StageStyle.UNDECORATED);

        URL url = LoginMenu.class.getResource("/FXML/login/securityQuestionChooseMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(dialog, app);

        Scene scene = new Scene(pane);
        dialog.setScene(scene);
        dialog.show();
    }
}
