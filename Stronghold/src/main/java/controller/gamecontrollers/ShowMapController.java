package controller.gamecontrollers;

import model.databases.GameDatabase;

public class ShowMapController {
    private final GameDatabase gameDatabase;
    private int currentMapX;
    private int currentMapY;

    public ShowMapController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public String showMap(int x, int y) {
        //TODO get maps and return it and give current x,y value for moveMap
        return null;
    }

    public String moveMap(String direction) {
        //TODO convert direction into x,y and call showMap function
        return null;
    }

    public String showDetails(int x, int y) {
        //TODO get map data and return them
        return null;
    }
}
