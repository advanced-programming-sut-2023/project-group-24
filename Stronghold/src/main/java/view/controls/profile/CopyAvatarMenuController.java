package view.controls.profile;

import javafx.scene.input.MouseEvent;
import view.controls.Control;

public class CopyAvatarMenuController extends Control {
    public void cancel(MouseEvent mouseEvent) {
        getStage().close();
    }

    @Override
    public void run() {

    }
}
