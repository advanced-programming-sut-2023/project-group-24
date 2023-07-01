package view.menus.profile;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.net.URL;

public class ChangePasswordMenu extends Application {
    private AppController app;

    public ChangePasswordMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.initStyle(StageStyle.TRANSPARENT);

        URL url = LoginMenu.class.getResource("/FXML/profile/changePasswordMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(dialog, app);

        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.show();

        Rectangle rect = new Rectangle(dialog.getWidth(), dialog.getHeight());
        rect.setArcHeight(83.0);
        rect.setArcWidth(83.0);
        pane.setClip(rect);
    }
}
