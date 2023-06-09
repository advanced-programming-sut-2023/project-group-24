package view.oldmenus.gamemenus;

import controller.AppController;
import controller.MenusName;
import controller.gamecontrollers.ShowMapController;
import view.enums.commands.Commands;
import view.oldmenus.GetInputFromUser;

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
            else if (Commands.getMatcher(input, Commands.EXIT) != null) exitShowMapMenu();
            else if (Commands.getMatcher(input, Commands.SHOW_CURRENT_MENU) != null)
                System.out.println("Show map menu");
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
        String directions = matcher.group("direction").replaceFirst(" ", "");
        String[] direction = directions.split(" ");
        int changeY = 0;
        int changeX = 0;
        for (String e : direction) {
            String move = e.replaceAll("\\d+", "");
            String numberAsString = e.replaceAll("(up)|(down)|(left)|(right)", "");
            int number = numberAsString.equals("") ? 1 : Integer.parseInt(numberAsString);
            switch (move) {
                case "up":
                    changeX -= number;
                    break;
                case "down":
                    changeX += number;
                    break;
                case "left":
                    changeY -= number;
                    break;
                default:
                    changeY += number;
                    break;
            }
        }
        System.out.println(showMapController.moveMap(changeX, changeY));
    }

    private void showDetails(Matcher matcher) {
        System.out.println(showMapController.showDetails
                (Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
    }

    private void exitShowMapMenu() {
        showMapController.exit();
    }
}
