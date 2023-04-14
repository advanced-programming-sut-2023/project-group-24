package controller.createmapcontrollers;

import model.GameDatabase;
import utils.enums.messages.CreateMapMessages;

public class LandscapeController {
    private GameDatabase gameDatabase;

    public LandscapeController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
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
        //TODO
        return null;
    }

    public CreateMapMessages dropRock(int x, int y, String direction) {
        //TODO
        return null;
    }

    public CreateMapMessages dropTree(int x, int y, String type) {
        //TODO
        return null;
    }
}
