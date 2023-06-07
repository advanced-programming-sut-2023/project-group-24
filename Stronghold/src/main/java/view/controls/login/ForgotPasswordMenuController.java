package view.controls.login;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class ForgotPasswordMenuController extends Control {
    public Label title;

    public void cancel(MouseEvent mouseEvent) {
        getStage().close();
    }

    public void confirm(MouseEvent mouseEvent) {
    }

    @Override
    public void run() {

    }
}
