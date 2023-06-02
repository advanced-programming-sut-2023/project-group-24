package controller;

import controller.captchacontrollers.CaptchaGenerator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.User;
import model.databases.Database;
import view.enums.messages.LoginMenuMessages;
import view.menus.login.LoginMenu;
import view.oldmenus.CaptchaMenu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class LoginController implements Controller {
    private final Database database;
    private int numberOfIncorrectPassword;
    private String captchaText;

    public LoginController(Database database) {
        this.database = database;
        this.numberOfIncorrectPassword = 0;
    }

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        User user = database.getUserByUsername(username);
        if (user == null) return LoginMenuMessages.USER_NOT_FOUND;
        else if (!user.isPasswordCorrect(MainController.getSHA256(password))) {
            numberOfIncorrectPassword++;
            return LoginMenuMessages.INCORRECT_PASSWORD;
        }
        numberOfIncorrectPassword = 0;
        if (stayLoggedIn) database.setStayedLoggedInUser(user);
        AppController.setLoggedInUser(user);
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
        return LoginMenuMessages.SUCCESS;
    }

    public void makeDelayForIncorrectPassword() {
        int delayTime = (numberOfIncorrectPassword * 5) * 1000;
        try {
            Robot robot = new Robot();
            changeConsole(robot);
            robot.delay(delayTime);
            changeConsole(robot);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeConsole(Robot robot) {
        robot.delay(300);
        robot.mouseMove(1700, 950);
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
        robot.mouseMove(1725, 975);
        robot.delay(30);
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
    }

    public void generateCaptcha(ImageView captchaImage) {
        File file = new File("info/captcha.png");
        captchaText = CaptchaGenerator.generateRandomCaptcha(200, 50, file.getAbsolutePath());
        captchaImage.setImage(new Image(file.getAbsolutePath()));
    }

    public boolean isCaptchaIncorrect(String captchaText) {
        return captchaText == null || !captchaText.equals(this.captchaText);
    }
}
