package view.controls.main;

import controller.MenusName;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
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

    public void profileMenu() throws Exception {
        getApp().run(MenusName.PROFILE_MENU);
    }

    public void createMap() throws Exception {
        getApp().run(MenusName.ENTER_CREATE_MAP_MENU);
    }

    public void enterGame() throws Exception {
        getApp().run(MenusName.ENTER_MAP_MENU);
    }

    public void exit() {
        getApp().saveData();
        getStage().close();
        System.exit(0);
    }

    @Override
    public void run() {
        Font huge = Font.loadFont(getClass().getResource("/fonts/Seagram.ttf").toExternalForm(), 70);
        Font large = Font.loadFont(getClass().getResource("/fonts/Seagram.ttf").toExternalForm(), 30);
        profileMenu.setFont(large);
        enterGame.setFont(large);
        createMap.setFont(large);
        exit.setFont(large);
        title.setFont(huge);
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
}
