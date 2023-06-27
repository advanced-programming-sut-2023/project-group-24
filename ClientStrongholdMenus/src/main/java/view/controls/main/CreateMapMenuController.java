package view.controls.main;

import controller.ControllersName;
import controller.CreateMapController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.controls.Control;

public class CreateMapMenuController extends Control {
    public Button confirm;
    public Button cancel;
    public TextField mapId;
    public TextField mapSize;
    public Label idError;
    public Label sizeError;

    private CreateMapController createMapController;

    public void confirm() {
        if (mapSize.getText().equals("")) sizeError.setText("This field is empty");
        if (mapId.getText().equals("")) idError.setText("This field is empty");
        if (!sizeError.getText().equals("") || !idError.getText().equals("")) return;
        switch (createMapController.getCreateMapMessage(Integer.parseInt(mapSize.getText()), mapId.getText())) {
            case ID_EXIST:
                idError.setText("The id already exists");
                break;
            case INVALID_SIZE:
                sizeError.setText("The map size too small or too big");
                break;
            case SUCCESS:
                createMapController.createMap(Integer.parseInt(mapSize.getText()), mapId.getText());
                success();
                break;
        }
    }

    public void cancel() {
        getStage().close();
    }

    private void success() {
        //TODO a loading page and connection to create map section
        createMapController.saveMap();
        getStage().close();
    }

    @Override
    public void run() {
        createMapController = (CreateMapController) getApp().getControllerForMenu(ControllersName.CREATE_MAP);
        mapId.textProperty().addListener((observableValue, s, t1) -> idError.setText(""));
        mapSize.textProperty().addListener((observableValue, s, t1) -> sizeFieldCheck(s, t1));
    }

    private void sizeFieldCheck(String from, String to) {
        if (!to.matches("\\d*")) mapSize.setText(from);
        sizeError.setText("");
    }
}
