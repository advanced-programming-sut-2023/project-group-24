package view.controls.main;

import controller.ControllersName;
import controller.nongame.ChatController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Packet;
import model.chat.Chat;
import model.chat.Message;
import view.controls.Control;
import view.modelview.ChatBox;
import view.modelview.MessageBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class ChatMenuController extends Control {
    public VBox chatsContainer;
    public ScrollPane chatsScrollPane;
    public ScrollPane messageScrollPane;
    public HBox sendContainer;
    public TextArea messageToSend;
    public Button sendButton;
    public VBox messageContainer;
    public Label chatName;

    private ChatController chatController;

    @Override
    public void run() {
        chatController = (ChatController) getApp().getControllerForMenu(ControllersName.CHAT, this);
        update();
    }

    @Override
    public PropertyChangeListener listener() {
        return evt -> {
            Packet packet = ((Packet) evt.getNewValue());
            if (packet.getTopic().equals("chat") && !packet.getSubject().equals("read"))
                Platform.runLater(this::update);
        };
    }

    private void update() {
        chatName.setText(chatController.getChatName());
        Vector<Message> messages = chatController.readAllMessages();
        if (messages == null) return;
        messageContainer.getChildren().clear();
        for (Message message : messages) messageContainer.getChildren().add(chatController.getMessageBox(message));
        messageScrollPane.setVvalue(1);
    }

    public void send() {
        chatController.sendMessage(messageToSend.getText());
        update();
    }
}
