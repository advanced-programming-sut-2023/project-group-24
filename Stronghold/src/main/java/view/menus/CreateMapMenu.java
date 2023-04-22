package view.menus;

import controller.AppController;
import controller.CreateMapController;
import view.menus.commands.Commands;
import view.menus.messages.CreateMapMessages;

import java.util.regex.Matcher;

public class CreateMapMenu {
    private final CreateMapController createMapController;

    public CreateMapMenu(CreateMapController createMapController) {
        this.createMapController = createMapController;
    }

    public void run() {
        Matcher matcher;
        String input;
        while (true) {
            input = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(input, Commands.CREATE_MAP)) != null) {
                if (createMap(matcher)) break;
            } else System.out.println("Invalid commands!");
        }
        while (AppController.getCurrentMenu().equals(MenusName.CREATE_MAP_MENU)) {
            input = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(input, Commands.DROP_ROCK)) != null) dropRock(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.DROP_TREE)) != null) dropTree(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.CLEAR)) != null) clear(matcher);
            else if (Commands.getMatcher(input, Commands.EXIT) != null) exitCreateMap();
            else if ((matcher = Commands.getMatcher(input, Commands.SET_TEXTURE)) != null) setTexture(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.SET_TEXTURE_MULTIPLE)) != null)
                setTextureMultiple(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.DROP_UNIT)) != null) dropUnit(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.DROP_BUILDING)) != null) dropBuilding(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.NEW_KINGDOM)) != null) newKingdom(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_KINGDOM)) != null)
                setCurrentKingdom(matcher);
            else System.out.println("Invalid command!");
        }
    }

    private void exitCreateMap() {
        handleMessage(createMapController.exitCreateMapMenu());
    }

    private void newKingdom(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String texture = matcher.group("texture");
        CreateMapMessages messages = createMapController.newKingdom(x, y, texture);
        handleMessage(messages);
    }

    private boolean createMap(Matcher matcher) {
        String id = matcher.group("id");
        int size = Integer.parseInt(matcher.group("size"));
        CreateMapMessages messages = createMapController.createMap(size, id);
        handleMessage(messages);
        return messages.equals(CreateMapMessages.SUCCESS);
    }

    private void setTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String texture = matcher.group("texture");
        CreateMapMessages messages = createMapController.setTexture(x, y, texture);
        handleMessage(messages);
    }

    private void setTextureMultiple(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x1"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        String texture = matcher.group("texture");
        CreateMapMessages messages = createMapController.setTexture(x1, y1, x2, y2, texture);
        handleMessage(messages);
    }

    private void clear(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        CreateMapMessages messages = createMapController.clear(x, y);
        handleMessage(messages);
    }

    private void dropRock(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String direction = matcher.group("direction");
        CreateMapMessages messages = createMapController.dropRock(x, y, direction);
        handleMessage(messages);
    }

    private void dropTree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        CreateMapMessages messages = createMapController.dropTree(x, y, type);
        handleMessage(messages);
    }

    private void setCurrentKingdom(Matcher matcher) {
        String color = matcher.group("color");
        CreateMapMessages messages = createMapController.setCurrentKingdom(color);
        handleMessage(messages);
    }

    private void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        CreateMapMessages messages = createMapController.dropBuilding(x, y, type);
        handleMessage(messages);
    }

    private void dropUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        int count = 1;
        if (matcher.group("count") != null) count = Integer.parseInt(matcher.group("count"));
        CreateMapMessages messages = createMapController.dropUnit(x, y, type, count);
        handleMessage(messages);
    }

    private void handleMessage(CreateMapMessages messages) {
        switch (messages) {
            case INVALID_LOCATION:
                System.out.println("\033[0;31mLocation out of bounds!\033[0m");
                break;
            case INVALID_DIRECTION:
                System.out.println("\033[0;31mInvalid direction!\033[0m");
                break;
            case NOT_HERE:
                System.out.println("\033[0;31mNot here!\033[0m");
                break;
            case ID_EXIST:
                System.out.println("\033[0;31mId already exist!\033[0m");
                break;
            case INVALID_SIZE:
                System.out.println("\033[0;31msize out of bound!\033[0m");
                break;
            case FEW_KINGDOM:
                System.out.println("\033[0;31mNot enough kingdom!\033[0m");
                break;
            case INVALID_COLOR:
                System.out.println("\033[0;31mInvalid color!\033[0m");
                break;
            case KINGDOM_NOT_EXIST:
                System.out.println("\033[0;31mNo kingdom with this color exist!\033[0m");
                break;
            case INVALID_TEXTURE:
                System.out.println("\033[0;31mInvalid texture!\033[0m");
                break;
            case INVALID_TYPE:
                System.out.println("\033[0;31mInvalid Type!\033[0m");
                break;
            case CURRENT_KINGDOM_NULL:
                System.out.println("\033[0;31mSet a kingdom first!\033[0m");
                break;
            case INVALID_COUNT:
                System.out.println("\033[0;31mInvalid count!\033[0m");
                break;
            case KINGDOM_EXIST:
                System.out.println("\033[0;31mKingdom with this color allready exist!\033[0m");
                break;
            case SUCCESS:
                System.out.println("\033[0;32mSuccess\033[0m");
                break;
        }
    }
}
