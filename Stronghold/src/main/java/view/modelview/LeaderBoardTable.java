package view.modelview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class LeaderBoardTable extends VBox {
    public LeaderBoardTable() {
        super();
        for (int i = 0; i < 10; i++) {
            Label rank = new Label();
            rank.setMinWidth(30);
            ImageView imageView = new ImageView();
            imageView.setFitHeight(0);
            imageView.setFitWidth(0);
            Label username = new Label();
            username.setMinWidth(200);
            Label score = new Label();
            score.setMinWidth(50);
            HBox hBox = new HBox(rank, imageView, username, score);
            hBox.setSpacing(10);
            hBox.setPadding(new Insets(5));
            this.getChildren().add(hBox);
        }
    }

    public void show(ArrayList<UserInfo> info) {
        for (int i = 0; i < 10; i++) {
            HBox hBox = (HBox) this.getChildren().get(i);
            if (i < info.size()) {
                ((ImageView) hBox.getChildren().get(1)).setImage(info.get(i).getAvatar());
                ((Label) hBox.getChildren().get(0)).setText(String.valueOf(info.get(i).getRank()));
                ((Label) hBox.getChildren().get(2)).setText(String.valueOf(info.get(i).getUsername()));
                ((Label) hBox.getChildren().get(3)).setText(String.valueOf(info.get(i).getHighscore()));
            } else {
                ((ImageView) hBox.getChildren().get(1)).setImage(null);
                ((Label) hBox.getChildren().get(0)).setText("");
                ((Label) hBox.getChildren().get(2)).setText("");
                ((Label) hBox.getChildren().get(3)).setText("");
            }
        }
    }
}
