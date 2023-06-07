package view.controls.login;

import controller.MenusName;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.enums.RecoveryQuestion;
import view.controls.Control;

public class SecurityQuestionChooseMenuController extends Control {
    public ComboBox<String> comboBox;
    public Label title;
    public TextField answer;
    public Button confirm;
    public Button cancel;

    public void cancel() {
        getStage().close();
    }

    public void confirm() throws Exception {
        getStage().close();
        getApp().run(MenusName.CAPTCHA_MENU);
    }

    @Override
    public void run() {
        comboBox.setItems(FXCollections.observableArrayList(RecoveryQuestion.getAsArray()));
        comboBox.setValue(RecoveryQuestion.getRecoveryQuestionByNumber(1));
        setUpText();
    }

    private void setUpText() {
        Font small = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 17);
        Font medium = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        title.setFont(small);
        answer.setFont(medium);
        cancel.setFont(medium);
        confirm.setFont(medium);
        title.setTextFill(Color.rgb(101, 166, 27));
    }
}
