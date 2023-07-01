package view.controls.lobby;

import controller.ControllersName;
import controller.MenusName;
import controller.nongame.LobbyController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Lobby;
import model.Packet;
import view.controls.Control;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LobbyMainMenuController extends Control {

    public TextField lobbySize;
    public CheckBox privateLobby;
    public TextField lobbyId;
    public VBox lobbyContainer;
    public TextField searchBox;
    private LobbyController lobbyController;

    public void backMainMenu() throws Exception {
        getApp().run(MenusName.MAIN_MENU);
    }

    @Override
    public void run() {
        lobbyController = (LobbyController) getApp().getControllerForMenu(ControllersName.LOBBY_CONTROL, this);
        refresh();
    }

    private void handleText() {
        lobbyContainer.getChildren().clear();
        ArrayList<String> lobbies = lobbyController.getRandomLobbies();
        for (int i = 0; i < lobbies.size(); i++) {
            Text text = new Text();
            text.setText(String.valueOf(i + 1) + ") " + lobbies.get(i));
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        goToLobby(text.getText());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            lobbyContainer.getChildren().add(text);
        }
    }

    private void goToLobby(String text) throws Exception {
        String regex = "\\) Admin: ([^\\s]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        lobbyController.setCurrentLobby(matcher.group(1));
        lobbyController.addUser(lobbyController.getCurrentLobby());
        refresh();
        getApp().run(MenusName.LOBBY_MENU);
    }

    private void goToLobby(Lobby lobby) {
        lobbyController.setCurrentLobby(lobby.getAdmin().getNickname());
        refresh();
        try {
            getApp().run(MenusName.LOBBY_MENU);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startGame() {
        ProcessBuilder processBuilder = new ProcessBuilder("./run.bat");
        try {
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("could not start game");
        }
    }

    @Override
    public PropertyChangeListener listener() {
        return evt -> {
            Packet packet = (Packet) evt.getNewValue();
            if (packet.getTopic().equals("lobbyDatabase")) refresh();
        };
    }

    public void addLobby() {
        if (!(privateLobby.isSelected() && lobbyId.getText().isEmpty())) {
            lobbyController.addLobby(Integer.parseInt(lobbySize.getText()), privateLobby.isSelected(), lobbyId.getText());
            lobbyController.setCurrentLobby(lobbyController.getLobbies().get(lobbyController.getLobbies().size() - 1).getAdmin().getNickname());
            goToLobby(lobbyController.getCurrentLobby());
        }
        refresh();
        lobbySize.clear();
        lobbyId.clear();
        privateLobby.setSelected(false);
    }

    public void refresh() {
        Platform.runLater(this::handleText);
    }

    public void joinPrivate() {
        Lobby lobby = null;
        if (!searchBox.getText().isEmpty()) lobby = lobbyController.getPrivateLobby(searchBox.getText());
        if (lobby != null) {
            lobbyController.addUser(lobby);
            goToLobby(lobby);
        }
    }
}
