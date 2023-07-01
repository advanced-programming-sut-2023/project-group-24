package view.controls.main;

import controller.ControllersName;
import controller.nongame.FriendController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Friendship;
import model.User;
import view.controls.Control;

import java.beans.PropertyChangeListener;
import java.util.Vector;

public class FriendshipMenuController extends Control {
    public VBox following;
    public VBox myFriend;
    public VBox request;
    public TextField searchTab;
    public Button search;
    public ImageView image;
    public Label name;
    public Label score;
    private User selectedUser;
    FriendController friendController;
    User currentUser;
    Vector<User> allUsers;


    @Override
    public void run() {
        friendController = (FriendController) getApp().getControllerForMenu(ControllersName.FRIEND, this);
        currentUser = friendController.getCurrentUser();
        allUsers = friendController.getUsers();
        Vector<Friendship> myFriends = new Vector<>();
        Vector<Friendship> IFollow = new Vector<>();
        Vector<Friendship> requests = new Vector<>();
        System.out.println(currentUser.getFriends().size());
        for (Friendship friend : currentUser.getFriends()) {
            if (friend.isAccept())
                myFriends.add(friend);
            else if (friend.getRequesterName().equals(currentUser.getUsername()))
                IFollow.add(friend);
            else
                requests.add(friend);
        }
        for (Friendship friendship : myFriends) {
            String name;
            if (friendship.getAccepterName().equals(currentUser.getUsername()))
                name = friendship.getRequesterName();
            else name = friendship.getAccepterName();
            Label label = new Label(name);
            myFriend.getChildren().add(label);
        }
        for (Friendship friendship : IFollow) {
            Label label = new Label(friendship.getAccepterName());
            following.getChildren().add(label);
        }
        for (Friendship friendship : requests) {
            Label label = new Label(friendship.getRequesterName());
            label.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.isAltDown())
                    friendController.rejectAccept(friendship);
                else friendController.acceptRequest(friendship);
            });
            request.getChildren().add(label);
        }
    }

    private User getUser(String name) {
        for (User user : allUsers)
            if (user.getUsername().equals(name))
                return user;
        return null;
    }


    @Override
    public PropertyChangeListener listener() {
        return null;
    }

    public void search() {
        if ((selectedUser = getUser(searchTab.getText())) != null) {
            score.setText("Score: " + selectedUser.getHighScore());
            name.setText(selectedUser.getUsername());
            image.setImage(friendController.getAvatar(selectedUser));
        }
    }

    public void send(MouseEvent mouseEvent) {
        if (selectedUser != null) {
            System.out.println("farshi");
            friendController.sendRequest(new Friendship(currentUser.getUsername(), selectedUser.getUsername()));
        }
    }
}
