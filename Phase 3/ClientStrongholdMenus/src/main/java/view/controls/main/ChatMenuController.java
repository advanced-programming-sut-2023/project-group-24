package view.controls.main;

import controller.ControllersName;
import controller.nongame.ChatController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
import java.security.PrivilegedExceptionAction;
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
    public TextField privateChat;
    public TextField room;

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
            if (packet.getTopic().equals("chat") && packet.getSubject().equals("read"))
                Platform.runLater(this::updateWithoutSeeing);
        };
    }

    private void updateWithoutSeeing() {
        chatName.setText(chatController.getChatName());
        Vector<Message> messages = chatController.readWithoutSeeing();
        if (messages == null) return;
        messageContainer.getChildren().clear();
        chatsContainer.getChildren().clear();
        for (Message message : messages) {
            MessageBox messageBox = chatController.getMessageBox(message);
            messageContainer.getChildren().add(messageBox);
            messageBox.getEdit().setOnMouseClicked(mouseEvent -> edit(message));
            messageBox.getDeleteForMe().setOnMouseClicked(mouseEvent -> deleteForMe(message));
            messageBox.getDeleteForAll().setOnMouseClicked(mouseEvent -> deleteForAll(message));
            messageBox.getLaughing().setOnMouseClicked(mouseEvent -> react(message, 1));
            messageBox.getCrying().setOnMouseClicked(mouseEvent -> react(message, 2));
            messageBox.getHeart().setOnMouseClicked(mouseEvent -> react(message, 3));
        }
        for (Chat chat : chatController.getAllChats()) {
            ChatBox chatBox = new ChatBox(chatController.getChatName(chat));
            chatsContainer.getChildren().add(chatBox);
            chatBox.setOnMouseClicked(mouseEvent -> selectChat(chatBox.getText()));
        }
        messageScrollPane.setVvalue(1);
    }

    private void update() {
        chatName.setText(chatController.getChatName());
        Vector<Message> messages = chatController.readAllMessages();
        if (messages == null) return;
        messageContainer.getChildren().clear();
        chatsContainer.getChildren().clear();
        for (Message message : messages) {
            MessageBox messageBox = chatController.getMessageBox(message);
            messageContainer.getChildren().add(messageBox);
            messageBox.getEdit().setOnMouseClicked(mouseEvent -> edit(message));
            messageBox.getDeleteForMe().setOnMouseClicked(mouseEvent -> deleteForMe(message));
            messageBox.getDeleteForAll().setOnMouseClicked(mouseEvent -> deleteForAll(message));
            messageBox.getLaughing().setOnMouseClicked(mouseEvent -> react(message, 1));
            messageBox.getCrying().setOnMouseClicked(mouseEvent -> react(message, 2));
            messageBox.getHeart().setOnMouseClicked(mouseEvent -> react(message, 3));
        }
        for (Chat chat : chatController.getAllChats()) {
            ChatBox chatBox = new ChatBox(chatController.getChatName(chat));
            chatsContainer.getChildren().add(chatBox);
            chatBox.setOnMouseClicked(mouseEvent -> selectChat(chatBox.getText()));
        }
        messageScrollPane.setVvalue(1);
    }

    private void selectChat(String chat) {
        chatController.selectChat(chat);
        update();
    }

    private void react(Message message, int i) {
        chatController.react(message, i);
        update();
    }

    private void deleteForAll(Message message) {
        chatController.deleteForAll(message);
        update();
    }

    private void deleteForMe(Message message) {
        chatController.deleteForMe(message);
        update();
    }

    private void edit(Message message) {
        chatController.edit(message, messageToSend.getText());
        messageToSend.setText("");
        update();
    }

    public void send() {
        chatController.sendMessage(messageToSend.getText());
        messageToSend.setText("");
        update();
    }

    public void newPrivateChat() {
        if (chatController.newPrivateChat(privateChat.getText())) update();
    }

    public void newRoom() {
        if (chatController.newRoom(room.getText().split("\\-"))) update();
    }
}
