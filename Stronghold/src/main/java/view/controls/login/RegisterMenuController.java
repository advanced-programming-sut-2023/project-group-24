package view.controls.login;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class RegisterMenuController extends Control {
    public void register(MouseEvent mouseEvent) throws Exception {
        getApp().run(MenusName.SECURITY_QUESTION_CHOOSE_MENU);
    }
}
