package view.controls.profile;

import controller.ControllersName;
import controller.LeaderBoardController;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import view.controls.Control;
import view.modelview.LeaderBoardTable;

public class LeaderBoardMenuController extends Control {
    public ImageView back;
    public LeaderBoardTable leaderBoardContent;

    private LeaderBoardController leaderBoardController;

    public void back(MouseEvent mouseEvent) {
        getStage().close();
    }

    @Override
    public void run() {
        leaderBoardController = (LeaderBoardController) getApp().getControllerForMenu(ControllersName.LEADER_BOARD);
        leaderBoardContent.show(leaderBoardController.showTenUsers());
        leaderBoardContent.setOnScroll(this::handleScroll);
    }

    private void handleScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) leaderBoardController.goDown();
        else leaderBoardController.goUp();

        leaderBoardContent.show(leaderBoardController.showTenUsers());
    }
}
