package view.controls.profile;

import controller.AppController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import view.controls.Control;
import view.modelview.LeaderBoardTable;

public class LeaderBoardMenuController extends Control {
    public ImageView back;
    public ScrollPane scrollPane;
    public LeaderBoardTable leaderBoardContent;

    public void back(MouseEvent mouseEvent) {
        getStage().close();
    }

    @Override
    public void run() {
        final double SPEED = 0.05 / leaderBoardContent.getChildren().size();
        scrollPane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
        });
    }
}
