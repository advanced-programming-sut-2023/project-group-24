package view.controls.main;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class MainMenuController extends Control {
    public void profileMenu(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.PROFILE_MENU);
    }

    @Override
    public void run() {

    }
}
