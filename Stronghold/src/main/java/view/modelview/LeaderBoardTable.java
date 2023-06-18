package view.modelview;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class LeaderBoardTable extends VBox {
    public LeaderBoardTable() {
        super();
        for (int i = 0; i < 50; i++) {
            Button button = new Button();
            button.setMinWidth(100);
            button.setMinHeight(100);
            button.setText(String.valueOf(i));
            this.getChildren().add(button);
        }
    }
}
