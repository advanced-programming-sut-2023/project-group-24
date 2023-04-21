package view.menus;

import controller.AppController;
import controller.CreateMapController;
import view.menus.commands.Commands;
import view.menus.messages.CreateMapMessages;

import java.util.Objects;
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

    private void newKingdom(Matcher matcher) {
    }


    private boolean createMap(Matcher matcher) {
        String id = matcher.group("id");
        int size = Integer.parseInt(matcher.group("size"));
        CreateMapMessages messages = createMapController.createMap(size, id);
        switch (messages) {
            case ID_EXIST:
                System.out.println("Id already exist!");
                break;
            case INVALID_SIZE:
                System.out.println("size out of bound!");
                break;
            case SUCCESS:
                System.out.println("create map successfully");
                return true;
        }
        return false;
    }

    private void setTexture(Matcher matcher) {
        //TODO connect to landscape controller
    }

    private void setTextureMultiple(Matcher matcher) {
        //TODO connect to landscape controller
    }

    private void clear(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        CreateMapMessages messages = createMapController.clear(x, y);
        if (Objects.requireNonNull(messages) == CreateMapMessages.INVALID_LOCATION)
            System.out.println("Location out of bounds!");
        else
            System.out.println("Success");
    }

    private void dropRock(Matcher matcher) {
        //TODO
    }

    private void dropTree(Matcher matcher) {
        //TODO
    }

    private void setCurrentKingdom(Matcher matcher) {//set color
        //TODO
    }

    private void dropBuilding(Matcher matcher) {
        //TODO
    }

    private void dropUnit(Matcher matcher) {
        //TODO
    }
}
