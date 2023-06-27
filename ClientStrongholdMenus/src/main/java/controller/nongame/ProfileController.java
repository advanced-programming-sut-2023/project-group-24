package controller.nongame;

import controller.Controller;
import controller.InputOutputHandler;
import model.AvatarHandler;
import model.Packet;
import view.controls.Control;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Objects;

public class ProfileController implements Controller {
    private final InputOutputHandler inputOutputHandler;

    public ProfileController(InputOutputHandler inputOutputHandler, Control control) {
        this.inputOutputHandler = inputOutputHandler;
        inputOutputHandler.addListener(evt -> control.listener().propertyChange(evt));
    }

    public void requestChangeUsername(String newUsername) {
        Packet packet = new Packet("profile", "change username", null, newUsername);
        inputOutputHandler.sendPacket(packet);
    }

    public void requestCheckChangeUsernameErrors(String newUsername) {
        Packet packet = new Packet("profile", "check username errors", null, newUsername);
        inputOutputHandler.sendPacket(packet);
    }

    public void requestChangeNickname(String newNickname) {
        Packet packet = new Packet("profile", "change nickname", null, newNickname);
        inputOutputHandler.sendPacket(packet);
    }

    public void checkChangePasswordErrors(String oldPassword, String newPassword) {
        Packet packet = new Packet("profile", "check change password", new String[]{oldPassword, newPassword}, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void changePassword(String newPassword, String newPasswordConfirm) {
        Packet packet = new Packet("profile", "change password", new String[]{newPassword, newPasswordConfirm}, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void changeEmail(String newEmail) {
        Packet packet = new Packet("profile", "change email", null, newEmail);
        inputOutputHandler.sendPacket(packet);
    }

    public void checkChangeEmailErrors(String newEmail) {
        Packet packet = new Packet("profile", "check change email", null, newEmail);
        inputOutputHandler.sendPacket(packet);
    }

    public void changeSlogan(String newSlogan) {
        Packet packet = new Packet("profile", "change slogan", null, newSlogan);
        inputOutputHandler.sendPacket(packet);
    }

    public void removeSlogan() {
        Packet packet = new Packet("profile", "remove slogan", null, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void showRank() {
        Packet packet = new Packet("profile", "show rank", null, "");
        inputOutputHandler.sendPacket(packet);
    }

    public String getAvatarPath() { //TODO VERY IMPORTANT!!! ask for avatars from the server
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        return path + "info/avatars/mine/0.png";
    }

    public String getAvatarPath(String username) {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarPath = new File(path + "info/avatars/" + username + ".png");
        if (avatarPath.exists()) return path + "info/avatars/" + username + ".png";
        return path + "info/avatars/a@a.png";
    }

    public String[] getAllAvatarsPath() {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarsPath = new File(path + "info/avatars/mine");
        return avatarsPath.list();
    }

    public void addAvatar(String absolutePath) {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarsPath = new File(path + "info/avatars/mine");
        int index = Objects.requireNonNull(avatarsPath.listFiles()).length;
        try {
            Files.copy(new File(absolutePath).toPath(),
                    new File(path + "info/avatars/mine/" + index + ".png").toPath());
        } catch (IOException e) {
            System.out.println("couldn't save avatar");
            e.printStackTrace();
        }
    }

    public int getCurrentAvatarNumber() {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarsPath = new File(path + "info/avatars/mine");
        File target = new File(getAvatarPath());
        return new AvatarHandler().getAvatarNumber(avatarsPath, target);
    }

    public void setCurrentAvatar(URI pathUri) {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarsPath = new File(path + "info/avatars/mine/0.png");
        if (avatarsPath.exists()) if (avatarsPath.delete()) return;
        try {
            Files.copy(new File(pathUri).toPath(), avatarsPath.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO inform server of the change
    }

    public void setCurrentAvatar(String newPath) {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        File avatarsPath = new File(path + "info/avatars/mine/0.png");
        if (avatarsPath.exists()) if (avatarsPath.delete()) return;
        try {
            Files.copy(new File(newPath).toPath(), avatarsPath.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO inform server of the change
    }

    public void copyAvatar(String username) {
        File file = new File("");
        String path;
        if (file.getAbsolutePath().contains("Stronghold")) path = "../";
        else path = "./";
        String newAvatarPath = getAvatarPath(username);
        if (new AvatarHandler().getAvatarNumber(new File(path + "info/avatars/mine"), new File(newAvatarPath)) == -1)
            addAvatar(newAvatarPath);
        else setCurrentAvatar(newAvatarPath);
    }

    public String getCurrentUser(String info) {
        switch (info) {
            case "username":
                Packet packet1 = new Packet("profile", "show username", null, "");
                inputOutputHandler.sendPacket(packet1);
                break;
            case "nickname":
                Packet packet2 = new Packet("profile", "show rank", null, "");
                inputOutputHandler.sendPacket(packet2);
                break;
            case "email":
                Packet packet3 = new Packet("profile", "show email", null, "");
                inputOutputHandler.sendPacket(packet3);
                break;
            case "slogan":
                Packet packet4 = new Packet("profile", "show slogan", null, "");
                inputOutputHandler.sendPacket(packet4);
                break;
        }
        return "";
    }
}
