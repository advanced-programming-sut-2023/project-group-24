package view.controls.profile;

import controller.ControllersName;
import controller.nongame.LeaderBoardController;
import controller.nongame.ProfileController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import model.Packet;
import view.controls.Control;
import view.modelview.LeaderBoardTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LeaderBoardMenuController extends Control {
    public Button back;
    public LeaderBoardTable leaderBoardContent;
    public Label rank;

    private LeaderBoardController leaderBoardController;

    public void back() {
        getStage().close();
    }

    @Override
    public void run() {
        ProfileController profileController = (ProfileController) getApp().getControllerForMenu(ControllersName.PROFILE, this);
        leaderBoardController = (LeaderBoardController) getApp().getControllerForMenu(ControllersName.LEADER_BOARD, this);
        leaderBoardContent.show(leaderBoardController.showTenUsers());
        leaderBoardContent.setOnScroll(this::handleScroll);
        rank.setText("Your rank is " + profileController.showRank());
    }

    @Override
    public PropertyChangeListener listener() {
        return evt -> Platform.runLater(this::update);
    }

    private void update() {
        leaderBoardController.update();
        leaderBoardContent.show(leaderBoardController.showTenUsers());
    }

    private void handleScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) leaderBoardController.goDown();
        else leaderBoardController.goUp();

        leaderBoardContent.show(leaderBoardController.showTenUsers());
    }
}
