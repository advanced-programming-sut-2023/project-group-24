package controller.nongame;

import controller.Controller;
import controller.InputOutputHandler;
import model.Packet;
import model.User;
import view.controls.Control;
import view.modelview.UserInfo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;

public class LeaderBoardController implements Controller {
    private final InputOutputHandler inputOutputHandler;
    private int row;
    private Vector<User> users;

    public LeaderBoardController(InputOutputHandler inputOutputHandler, Control control) {
        this.inputOutputHandler = inputOutputHandler;
        inputOutputHandler.addListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Packet packet = ((Packet) evt.getNewValue());
                if (packet.getTopic().equals("leader board") && packet.getSubject().equals("all users"))
                    update(packet);
            }
        });
        this.row = 0;
    }

    public ArrayList<UserInfo> showTenUsers() {
        ArrayList<UserInfo> output = new ArrayList<>();
        for (int i = row * 10; i < (row + 1) * 10 && i < users.size(); i++)
            output.add(new UserInfo(
                    users.get(i).getUsername(),
                    users.get(i).getHighScore(),
                    i + 1)
            );
        return output;
    }

    public void goDown() {
        row++;
        if (row >= Math.ceil((double) users.size() / 10)) row = (int) Math.ceil((double) users.size() / 10) - 1;
    }

    public void goUp() {
        row--;
        if (row < 0) row = 0;
    }

    public void update(Packet packet) {
        //TODO update leader board
    }
}
