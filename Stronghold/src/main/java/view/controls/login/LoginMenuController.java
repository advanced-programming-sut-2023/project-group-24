package view.controls.login;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class LoginMenuController extends Control {
    public void forgotPassword(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.FORGOT_PASSWORD_MENU);
    }
}
