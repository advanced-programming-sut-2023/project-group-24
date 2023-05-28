package view.controls.login;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class LoginMenuController extends Control {
    public void forgotPassword(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.SECURITY_QUESTION_CONFIRM_MENU);
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.REGISTER_MENU);
    }
}
