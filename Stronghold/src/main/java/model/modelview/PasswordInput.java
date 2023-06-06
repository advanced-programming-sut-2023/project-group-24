package model.modelview;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import view.menus.login.LoginMenu;

public class PasswordInput extends HBox {
    private TextField passwordFieldShown;
    private PasswordField passwordFieldHidden;
    private ImageView show;
    private StackPane showContainer;
    private Font font;

    public PasswordInput() {
        super();
        this.getStyleClass().add("password-input");
        passwordFieldShown = new TextField();
        passwordFieldHidden = new PasswordField();
        show = new ImageView();
        showContainer = new StackPane(show);
        this.getChildren().add(passwordFieldHidden);
        this.getChildren().add(passwordFieldShown);
        this.getChildren().add(showContainer);
        passwordFieldHidden.textProperty().addListener((observableValue, s, t1) -> hintFont());
        passwordFieldHidden.setPromptText("password");
        passwordFieldShown.setPromptText("password");
        this.setAlignment(Pos.BOTTOM_LEFT);
        show.setImage(new Image(LoginMenu.class.getResource("/images/login/see-password.png").toExternalForm()));
        setUpFields();
        setUpSize();
    }

    private void hintFont() {
        if (font == null) return;
        if (passwordFieldHidden.getText().equals("")) passwordFieldHidden.setFont(font);
        else passwordFieldHidden.setFont(Font.font(
                "Arial", FontWeight.MEDIUM, FontPosture.REGULAR, font.getSize()));
    }

    public void setFont(Font font) {
        this.font = font;
        this.passwordFieldShown.setFont(font);
        this.passwordFieldHidden.setFont(font);
    }

    private void setUpFields() {
        passwordFieldShown.managedProperty().bind(showContainer.hoverProperty());
        passwordFieldShown.visibleProperty().bind(showContainer.hoverProperty());
        passwordFieldHidden.managedProperty().bind(showContainer.hoverProperty().not());
        passwordFieldHidden.visibleProperty().bind(showContainer.hoverProperty().not());
        passwordFieldHidden.textProperty().bindBidirectional(passwordFieldShown.textProperty());
        showContainer.hoverProperty().addListener(observable -> handleFocused());
    }

    private void setUpSize() {
        passwordFieldHidden.minHeightProperty().bind(this.prefHeightProperty());
        passwordFieldHidden.maxHeightProperty().bind(this.prefHeightProperty());
        passwordFieldShown.minHeightProperty().bind(this.prefHeightProperty());
        passwordFieldShown.maxHeightProperty().bind(this.prefHeightProperty());
        passwordFieldHidden.maxWidthProperty().bind(this.prefHeightProperty().multiply(-0.6).add(250));
        passwordFieldHidden.minWidthProperty().bind(this.prefHeightProperty().multiply(-0.6).add(250));
        passwordFieldShown.maxWidthProperty().bind(this.prefHeightProperty().multiply(-0.6).add(250));
        passwordFieldShown.minWidthProperty().bind(this.prefHeightProperty().multiply(-0.6).add(250));
        passwordFieldHidden.alignmentProperty().bind(this.alignmentProperty());
        passwordFieldShown.alignmentProperty().bind(this.alignmentProperty());
        show.fitHeightProperty().bind(this.prefHeightProperty().multiply(0.6));
        show.fitWidthProperty().bind(this.prefHeightProperty().multiply(0.6));
        showContainer.prefHeightProperty().bind(this.prefHeightProperty().multiply(0.6));
        showContainer.maxHeightProperty().bind(this.prefHeightProperty().multiply(0.6));
    }

    private void handleFocused() {
        if (passwordFieldShown.isVisible() && passwordFieldHidden.isFocused()) {
            int currentPosition = passwordFieldHidden.getCaretPosition();
            passwordFieldShown.requestFocus();
            passwordFieldShown.selectRange(currentPosition, currentPosition);
            passwordFieldShown.selectPositionCaret(currentPosition);
        }
        if (!showContainer.isHover() && passwordFieldShown.isFocused()) {
            int currentPosition = passwordFieldShown.getCaretPosition();
            passwordFieldHidden.requestFocus();
            passwordFieldHidden.selectEnd();
            passwordFieldHidden.selectRange(currentPosition, currentPosition);
            passwordFieldHidden.selectPositionCaret(currentPosition);
        }
    }

    public String getText() {
        return passwordFieldHidden.getText();
    }

    public StringProperty textProperty() {
        return passwordFieldHidden.textProperty();
    }

    public void disable(boolean disable) {
        passwordFieldShown.setDisable(disable);
        passwordFieldHidden.setDisable(disable);
    }
}
