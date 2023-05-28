package view.controls.profile;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class ChooseAvatarMenuController extends Control {
    public void confirm(MouseEvent mouseEvent) {
        getStage().close();
    }

    public void copyMenu(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.COPY_AVATAR_MENU);
    }
}
