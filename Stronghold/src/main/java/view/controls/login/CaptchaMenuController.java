package view.controls.login;

import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class CaptchaMenuController extends Control {
    public void cancel(MouseEvent mouseEvent) {
        getStage().close();
    }

    @Override
    public void run() {

    }
}
