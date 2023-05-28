package view.controls.login;

import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class SecurityQuestionChooseMenuController extends Control {
    public void cancel(MouseEvent mouseEvent) {
        getStage().close();
    }

    public void confirm(MouseEvent mouseEvent) {
    }
}
