package view.controls.profile;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class ChangePasswordMenuController extends Control {
    public void cancel(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.PROFILE_MENU);
    }

    @Override
    public void run() {

    }
}
