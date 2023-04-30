package controller;

import model.User;
import model.databases.Database;
import utils.enums.MenusName;
import view.enums.messages.LoginMenuMessages;
import view.menus.CaptchaMenu;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LoginMenuController {
    private final Database database;
    private int numberOfIncorrectPassword;

    public LoginMenuController(Database database) {
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
        if (!CaptchaMenu.runCaptcha()) return LoginMenuMessages.INCORRECT_CAPTCHA;
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
}
