package view.controls.main;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.controls.Control;
import view.modelview.ChatBox;

import java.beans.PropertyChangeListener;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChatMenuController extends Control {
    public VBox chatsContainer;
    public ScrollPane chatsScrollPane;

    @Override
    public void run() {
        byte[] bytes = new byte[] {(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
        String s = new String(bytes, StandardCharsets.UTF_8);
        for (int i = 0; i < 24; i++) chatsContainer.getChildren().add(new ChatBox("" + s));
        chatsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Override
    public PropertyChangeListener listener() {
        return null;
    }
}
