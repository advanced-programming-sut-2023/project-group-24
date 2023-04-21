package controller;

import model.Database;
import model.Direction;
import model.map.Cell;
import model.map.Map;
import view.menus.messages.CreateMapMessages;

public class CreateMapController {
    private Map map;
    private final Database database;

    public CreateMapController(Database database) {
        this.database = database;
    }

    public CreateMapMessages createMap(int size, String id) {
        if (database.getMapById(id) != null)
            return CreateMapMessages.ID_EXIST;
        if (!(size == 200 || size == 400))
            return CreateMapMessages.INVALID_SIZE;
        map = new Map(size , id);
        database.addMap(map);
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages setTexture(int x, int y, String texture) {
        //TODO
        return null;
    }

    public CreateMapMessages setTexture(int x1, int y1, int x2, int y2, String texture) {
        //TODO
        return null;
    }

    public CreateMapMessages clear(int x, int y) {
        if (x >= map.getSize() || y >= map.getSize() || x < 0 || y < 0)
            return CreateMapMessages.INVALID_LOCATION;
        map.getMap()[x][y].clear(map);
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages dropRock(int x, int y, String direction) {
        if (x >= map.getSize() || y >= map.getSize() || x < 0 || y < 0)
            return CreateMapMessages.INVALID_LOCATION;
        if (!direction.matches("[newsr]"))
            return CreateMapMessages.INVALID_DIRECTION;
        map.getMap()[x][y].setRock(true);
        if (direction.equals("r"))
            direction = randomDirection();
        map.getMap()[x][y].setDirection(getDirection(direction));
        return CreateMapMessages.SUCCESS;
    }

    private Direction getDirection(String direction) {
        switch (direction) {
            case "n":
                return Direction.UP;
            case "e":
                return Direction.RIGHT;
            case "w":
                return Direction.LEFT;
            default:
                return Direction.DOWN;
        }
    }

    private String randomDirection() {
        int direct = (int) (Math.random() * 4);
        switch (direct) {
            case 0:
                return "n";
            case 1:
                return "e";
            case 2:
                return "w";
            default:
                return "s";
        }
    }

    public CreateMapMessages dropTree(int x, int y, String type) {
        //TODO
        return null;
    }

    public void setCurrentKingdom(String color) {
        //TODO
    }

    public CreateMapMessages dropBuilding(int x, int y, String type) {
        //TODO
        return null;
    }

    public CreateMapMessages dropUnit(int x, int y, String type, int count) {
        //TODO
        return null;
    }
}
