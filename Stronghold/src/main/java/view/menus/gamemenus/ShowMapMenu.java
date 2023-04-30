package view.menus.gamemenus;

import controller.AppController;
import controller.gamecontrollers.ShowMapController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.menus.GetInputFromUser;

import java.util.regex.Matcher;

public class ShowMapMenu {
    private final ShowMapController showMapController;

    public ShowMapMenu(ShowMapController showMapController) {
        this.showMapController = showMapController;
    }

    public void run() {
        String input;
        Matcher matcher;
        while (AppController.getCurrentMenu().equals(MenusName.SHOW_MAP_MENU)) {
            input = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(input, Commands.SHOW_MAP)) != null) showMap(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.MOVE_MAP)) != null) moveMap(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.SHOW_DETAILS)) != null) showDetails(matcher);
            else System.out.println("\033[0;31mInvalid command!\033[0m");
        }
    }

    private void showMap(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (showMapController.checkInvalidIndex(x, y)) System.out.println("\033[0;31mIndex out of bounds!\033[0m");
        else System.out.println(showMapController.showMap(x, y));
    }

    private void moveMap(Matcher matcher) {
        int changeY = 0;
        int changeX = 0;
        if (matcher.group("up") != null)
            changeY = Integer.parseInt(matcher.group("up"));
        if (matcher.group("down") != null)
            changeY = -Integer.parseInt(matcher.group("down"));
        if (matcher.group("right") != null)
            changeX = Integer.parseInt(matcher.group("right"));
        if (matcher.group("left") != null)
            changeX = Integer.parseInt(matcher.group("left"));
        System.out.println(showMapController.moveMap(changeY, changeX));
    }

    private void showDetails(Matcher matcher) {
        System.out.println(showMapController.showDetails
                (Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
    }
}
