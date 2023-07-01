package controller.nongame;

import controller.Controller;
import controller.InputOutputHandler;
import model.User;
import model.databases.Database;
import view.modelview.UserInfo;

import java.util.ArrayList;
import java.util.Vector;

public class LeaderBoardController implements Controller {
    private Database database;
    private int row;
    private Vector<User> users;
    private InputOutputHandler ioHandler;

    public LeaderBoardController(InputOutputHandler ioHandler, Database database) {
        this.ioHandler = ioHandler;
        this.database = database;
        this.users = database.getAllUsersByRank();
        this.row = 0;
    }

    public ArrayList<UserInfo> showTenUsers() {
        ArrayList<UserInfo> output = new ArrayList<>();
        for (int i = row * 10; i < (row + 1) * 10 && i < users.size(); i++)
            output.add(new UserInfo(users.get(i), i + 1));
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

    public void update() {
        users = database.getAllUsersByRank();
    }
}
