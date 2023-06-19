package view.controls.profile;

import controller.ControllersName;
import controller.nongame.LeaderBoardController;
import controller.nongame.ProfileController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import view.controls.Control;
import view.modelview.LeaderBoardTable;

public class LeaderBoardMenuController extends Control {
    public Button back;
    public LeaderBoardTable leaderBoardContent;
    public Label rank;

    private LeaderBoardController leaderBoardController;

    public void back(MouseEvent mouseEvent) {
        getStage().close();
    }

    @Override
    public void run() {
        ProfileController profileController = (ProfileController) getApp().getControllerForMenu(ControllersName.PROFILE);
        leaderBoardController = (LeaderBoardController) getApp().getControllerForMenu(ControllersName.LEADER_BOARD);
        leaderBoardContent.show(leaderBoardController.showTenUsers());
        leaderBoardContent.setOnScroll(this::handleScroll);
        rank.setText("Your rank is " + profileController.showRank());
    }

    private void handleScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) leaderBoardController.goDown();
        else leaderBoardController.goUp();

        leaderBoardContent.show(leaderBoardController.showTenUsers());
    }
}
