package view.controls;

import controller.AppController;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;

public abstract class Control {
    private Stage stage;
    private AppController app;

    protected Stage getStage() {
        return stage;
    }

    protected AppController getApp() {
        return app;
    }

    public void setUp(Stage stage, AppController app) {
        this.stage = stage;
        this.app = app;
        run();
    }

    public abstract void run();
    public abstract PropertyChangeListener listener();
}
