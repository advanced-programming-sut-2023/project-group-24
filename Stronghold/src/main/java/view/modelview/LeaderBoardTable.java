package view.modelview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class LeaderBoardTable extends VBox {
    public LeaderBoardTable() {
        super();
        this.getStyleClass().add("leader-board-table");
        this.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < 10; i++) {
            Label rank = createLabel(30, i);
            ImageView imageView = createImageView();
            Label username = createLabel(200, i);
            Label score = createLabel(50, i);
            HBox hBox = new HBox(rank, imageView, username, score);
            hBox.setSpacing(10);
            hBox.setPadding(new Insets(5));
            setHBoxColor(i, hBox);
            this.getChildren().add(hBox);
        }
    }

    private Label createLabel(int size, int row) {
        Label label = new Label();
        label.setMinWidth(size);
         label.setTextFill(Color.WHITE);
        return label;
    }

    private ImageView createImageView() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(0);
        imageView.setFitWidth(0);
        return imageView;
    }

    private void setHBoxColor(int i, HBox hBox) {
        if (i % 2 == 0) hBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(124, 159, 214,0.35), CornerRadii.EMPTY, Insets.EMPTY
        )));
        else  hBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(82, 122, 186,0.35), CornerRadii.EMPTY, Insets.EMPTY
        )));
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
