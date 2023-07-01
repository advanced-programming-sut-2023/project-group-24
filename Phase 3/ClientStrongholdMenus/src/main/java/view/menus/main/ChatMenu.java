package view.menus.main;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.net.URL;

public class ChatMenu extends Application {
    private AppController app;

    public ChatMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Stage dialog = new Stage();
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.initOwner(stage);
////        dialog.initStyle(StageStyle.TRANSPARENT);
//
//        URL url = LoginMenu.class.getResource("/FXML/main/chatMenu.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(url);
//        Pane pane = fxmlLoader.load();
//        ((Control) fxmlLoader.getController()).setUp(dialog, app);
//
//        Scene scene = new Scene(pane);
//        scene.setFill(Color.TRANSPARENT);
//        dialog.setScene(scene);
//        dialog.setTitle("Literally Telegram");
//        dialog.getIcons().add(new Image(getClass().getResource("/images/icons/telegram.png").toExternalForm()));
//        dialog.show();
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        if (stage.getScene() != null) {
            dialog.initOwner(stage);
        }

        URL url = LoginMenu.class.getResource("/FXML/main/chatMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(dialog, app);

        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.setTitle("Literally Telegram");
        dialog.getIcons().add(new Image(getClass().getResource("/images/icons/telegram.png").toExternalForm()));
        dialog.setWidth(800);
        dialog.setHeight(600);
        dialog.show();
    }
}
