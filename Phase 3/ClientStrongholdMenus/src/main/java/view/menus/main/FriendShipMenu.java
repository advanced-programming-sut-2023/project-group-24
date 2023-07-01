package view.menus.main;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.controls.Control;
import view.menus.login.LoginMenu;

import java.net.URL;

public class FriendShipMenu extends Application {
    private final AppController app;

    public FriendShipMenu(AppController app) {
        this.app = app;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
//        dialog.initStyle(StageStyle.TRANSPARENT);

        URL url = LoginMenu.class.getResource("/FXML/main/friendshipMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane = fxmlLoader.load();
        ((Control) fxmlLoader.getController()).setUp(dialog, app);
        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);
        System.out.println("hi");
        dialog.setTitle("frind");
        dialog.show();
    }
}
