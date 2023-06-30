package view.controls.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.controls.Control;
import view.modelview.ChatBox;

import java.beans.PropertyChangeListener;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChatMenuController extends Control {
    public VBox chatsContainer;
    public ScrollPane chatsScrollPane;
    public ScrollPane messageScrollPane;
    public HBox sendContainer;
    public TextArea messageToSend;
    public Button sendButton;

    @Override
    public void run() {

    }

    @Override
    public PropertyChangeListener listener() {
        return null;
    }
}
