package view.modelview;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.chat.Message;
import model.chat.Reaction;

import java.util.Timer;
import java.util.TimerTask;

public class MessageBox extends HBox {
    private Message message;
    private int laughingReactions;
    private int cryingReactions;
    private int heartReactions;
    private ImageView imageView;
    private VBox left;
    private VBox right;

    public MessageBox(Message message) {
        super();
        right = new VBox();
        left = new VBox();
        right.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().add(left);
        this.getChildren().add(right);
        this.message = message;
        this.getStyleClass().add("message-box");
        this.setSpacing(30);

        manageTop(message);
        manageCenter(message);
        manageReactions(message);
        manageBottom();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(MessageBox.this::setBackground);
            }
        }, 30);
    }

    private void setBackground() {
        Insets insets = new Insets(0, this.getWidth() - left.getWidth() - right.getWidth() - 36, 0, 0);
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 62, 77), CornerRadii.EMPTY, insets)));
    }

    private void manageBottom() {
        int[] reactionCounts = new int[] {laughingReactions, cryingReactions, heartReactions};
        HBox reactions = new HBox();
        reactions.setSpacing(10);
        reactions.setMinWidth(80);
        reactions.setMaxWidth(80);
        for (int i = 0; i < 3; i++) {
            Text emoji = new Text(getEmojiFromNumber(1 + i));
            emoji.setFill(Color.WHITE);
            emoji.getStyleClass().add("emoji");
            Text count = new Text(String.valueOf(reactionCounts[i]));
            count.setFill(Color.WHITE);
            HBox bottom = new HBox(emoji, count);
            reactions.getChildren().add(bottom);
        }
        if (laughingReactions == 0) reactions.getChildren().get(0).setVisible(false);
        if (cryingReactions == 0) reactions.getChildren().get(1).setVisible(false);
        if (heartReactions == 0) reactions.getChildren().get(2).setVisible(false);


        HBox react = new HBox();
        for (int i = 0; i < 3; i++) {
            Text emoji = new Text(getEmojiFromNumber(1 + i));
            emoji.getStyleClass().add("emoji");
            react.getChildren().add(emoji);
        }
        react.visibleProperty().bind(this.hoverProperty());
        left.getChildren().add(reactions);
        right.getChildren().add(react);
    }

    private void manageReactions(Message message) {
        laughingReactions = 0;
        cryingReactions = 0;
        heartReactions = 0;
        for (Reaction reaction : message.getReactions()) {
            if (reaction.getReactionValue() == 1) laughingReactions++;
            if (reaction.getReactionValue() == 2) cryingReactions++;
            if (reaction.getReactionValue() == 3) heartReactions++;
        }
    }

    private void manageCenter(Message message) {
        Text messageContent = new Text(message.getMessage());
        messageContent.setFill(Color.WHITE);
        Button edit = getControlButton();
        Button deleteForMe = getControlButton();
        Button deleteForAll = getControlButton();
        VBox control = new VBox(edit, deleteForMe, deleteForAll);
        control.setAlignment(Pos.TOP_RIGHT);
        VBox messageContentContainer = new VBox(messageContent);
        messageContentContainer.setMinWidth(150);
        control.visibleProperty().bind(this.hoverProperty());
        HBox center = new HBox(messageContentContainer, control);
        center.setSpacing(30);
        messageContentContainer.setMinHeight(60);
        left.getChildren().add(messageContentContainer);
        right.getChildren().add(control);
    }

    private void manageTop(Message message) {
        imageView = new ImageView();
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Text sender = new Text(message.getSenderUsername());
        sender.setFill(Color.WHITE);
        HBox senderContainer = new HBox(imageView, sender);
        senderContainer.setSpacing(5);
        senderContainer.setMaxWidth(50);
        senderContainer.setMinWidth(50);
        Text date = new Text(String.format("%02d:%02d", message.getHour(), message.getMinute()));
        date.setFill(Color.WHITE);
        HBox dateContainer = new HBox(date);
        dateContainer.setAlignment(Pos.CENTER_RIGHT);
        HBox top = new HBox(senderContainer, dateContainer);
        left.getChildren().add(senderContainer);
        right.getChildren().add(dateContainer);
    }

    private Button getControlButton() {
        Button output = new Button();
        output.setMinHeight(20);
        output.setMinWidth(20);
        output.setMaxWidth(20);
        output.setMaxHeight(20);
        return output;
    }

    private String getEmojiFromNumber(int i) {
        byte[] bytes;
        switch (i) {
            case 1:
                bytes = new byte[] {(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
                return new String(bytes);
            case 2:
                bytes = new byte[] {(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xAD};
                return new String(bytes);
            case 3:
                bytes = new byte[] {(byte) 0xE2, (byte) 0x9D, (byte) 0xA4};
                return new String(bytes);
        }

        return "";
    }

    public void update() {
        manageTop(message);
        manageCenter(message);
        manageReactions(message);
        manageBottom();
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }
}
