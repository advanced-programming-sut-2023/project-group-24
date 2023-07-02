package view.controls.lobby;

import controller.Controller;
import controller.ControllersName;
import controller.MenusName;
import controller.nongame.LobbyController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Lobby;
import model.Packet;
import view.controls.Control;

import java.beans.PropertyChangeListener;
import java.net.URL;

public class LobbyMenuController extends Control {
    public ImageView adminLogo;
    public Text admin;
    public VBox lobbyContainer;
    private LobbyController lobbyController;

    private void handleText() {
        lobbyContainer.getChildren().clear();
        admin.setText(lobbyController.getCurrentLobby().getAdmin().getNickname());
        Lobby currentLobby = lobbyController.getCurrentLobby();
        for (int i = 1; i < currentLobby.getSize(); i++) {
            Text text = new Text();
            text.setText(currentLobby.getUsers().get(i).getNickname());
            lobbyContainer.getChildren().add(text);
        }
    }

    @Override
    public void run() {
        adminLogo.setImage(new Image(getClass().getResource("/images/icons/admin.png").toExternalForm()));
        lobbyController = (LobbyController) getApp().getControllerForMenu(ControllersName.LOBBY_CONTROL, this);
        refresh();
    }

    @Override
    public PropertyChangeListener listener() {
        return evt -> {
            Packet packet = (Packet) evt.getNewValue();
            if (packet.getTopic().equals("lobbyDatabase")) refresh();
            if (lobbyController.getCurrentLobby() != null) System.out.println(lobbyController.getCurrentLobby().getSize() + "*");
        };
    }

    public void refresh() {
            Platform.runLater(this::handleText);
    }

    public void leaveLobby() {
        lobbyController.removeUser(lobbyController.getCurrentLobby());
        backMain();
        refresh();
    }

    private void backMain() {
        try {
            getApp().run(MenusName.LOBBIES_MENU);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeState() {
        lobbyController.changeState(lobbyController.getCurrentLobby());
    }

    public void chat() throws Exception {
        getApp().createRoom();
        getApp().run(MenusName.CHAT_MENU);
    }
}
