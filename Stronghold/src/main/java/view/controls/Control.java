package view.controls;

import javafx.stage.Stage;

public class Control {
    private Stage stage;

    protected Stage getStage() {
        return stage;
    }

    public void setUp(Stage stage) {
        this.stage = stage;
    }
}
