package view.controls.login;

import controller.ControllersName;
import controller.nongame.LoginController;
import controller.MenusName;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.controls.Control;

public class SecurityQuestionConfirmMenuController extends Control {
    public TextField usernameField;
    public VBox questionPane;
    public Label question;
    public TextField answerField;
    public Button confirm;
    public Button cancel;
    public Label error;

    private LoginController loginController;

    public void confirm() throws Exception {
        if (loginController.isRecoveryAnswerCorrect(usernameField.getText(), answerField.getText())) {
            getApp().setCurrentUser(usernameField.getText());
            getStage().close();
            getApp().run(MenusName.FORGOT_PASSWORD_MENU);
            return;
        }
        error.setText("Incorrect answer");
    }

    public void cancel() {
        getStage().close();
    }

    @Override
    public void run() {
        loginController = (LoginController) getApp().getControllerForMenu(ControllersName.LOGIN);
        setUpText();
        usernameField.textProperty().addListener((observableValue, s, t1) ->
                setQuestionVisibility(loginController.usernameExists(usernameField.getText())));
        answerField.textProperty().addListener((observableValue, s, t1) -> error.setText(""));
        questionPane.setVisible(false);
    }

    private void setQuestionVisibility(boolean usernameExists) {
        if (usernameExists) {
            questionPane.setVisible(true);
            question.setText(loginController.getRecoveryQuestion(usernameField.getText()));
            answerField.setText("");
            error.setText("");
        }
        else questionPane.setVisible(false);
    }

    private void setUpText() {
        Font tiny = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 14);
        Font medium = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 20);
        Font mini = Font.loadFont(getClass().getResourceAsStream("/fonts/Seagram.ttf"), 11);
        question.setFont(tiny);
        answerField.setFont(medium);
        usernameField.setFont(medium);
        cancel.setFont(medium);
        confirm.setFont(medium);
        error.setFont(mini);
        question.setTextFill(Color.rgb(101, 166, 27));
    }
}
