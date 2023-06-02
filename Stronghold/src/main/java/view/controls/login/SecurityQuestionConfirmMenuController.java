package view.controls.login;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class SecurityQuestionConfirmMenuController extends Control {
    public void confirm(MouseEvent mouseEvent) throws Exception {
        getStage().close();
        getApp().run(MenusName.FORGOT_PASSWORD_MENU);
    }

    public void cancel(MouseEvent mouseEvent) {
        getStage().close();
    }

    @Override
    public void run() {

    }
}
