package view.controls.main;

import controller.MenusName;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.controls.Control;

import java.beans.PropertyChangeListener;

public class MainMenuController extends Control {
    public Text title;
    public Button enterGame;
    public Button createMap;
    public Button profileMenu;
    public Button exit;
    public Button chat;
    public Button friends;

    public void profileMenu() throws Exception {
        getApp().run(MenusName.PROFILE_MENU);
    }

    public void createMap() throws Exception {
        getApp().run(MenusName.ENTER_CREATE_MAP_MENU);

    }

    public void enterGame() throws Exception {
        getApp().run(MenusName.LOBBIES_MENU);
    }

    public void exit() {
        getApp().saveData();
        getApp().logout();
        getStage().close();
        System.exit(0);
    }

    @Override
    public void run() {
        profileMenu.getStyleClass().add("large-font");
        enterGame.getStyleClass().add("large-font");
        createMap.getStyleClass().add("large-font");
        exit.getStyleClass().add("large-font");
        friends.getStyleClass().add("large-font");
        chat.getStyleClass().add("large-font");
        title.getStyleClass().add("huge-font");
        title.setFill(Color.FIREBRICK);
        title.setStrokeWidth(0.2);
        title.setStroke(Color.BLACK);
        DropShadow effect = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0, 0, 0);
        effect.setInput(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 5, 0, 0, 0));
        title.setEffect(effect);
    }

    @Override
    public PropertyChangeListener listener() {
        return null;
    }

    public void friend() throws Exception {
        getApp().run(MenusName.FRIENDSHIP);
    }

    public void chat() throws Exception {
        getApp().run(MenusName.CHAT_MENU);
    }
}
