package view.controls.login;

import controller.MenusName;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class SecurityQuestionChooseMenuController extends Control {
    public void cancel(MouseEvent mouseEvent) {
        getStage().close();
    }

    public void confirm(MouseEvent mouseEvent) throws Exception {
        getStage().close();
        getApp().run(MenusName.CAPTCHA_MENU);
    }

    @Override
    public void run() {

    }
}
