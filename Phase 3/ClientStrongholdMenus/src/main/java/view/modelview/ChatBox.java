package view.modelview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ChatBox extends Button {
    public ChatBox(String name) {
        super();
        this.setText(name);
        this.setMinWidth(180);
        this.setMaxWidth(180);
        this.setMinHeight(50);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("chat-box");
    }
}
