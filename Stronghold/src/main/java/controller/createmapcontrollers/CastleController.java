package controller.createmapcontrollers;

import model.GameDatabase;
import utils.enums.messages.CreateMapMessages;

public class CastleController {
    private GameDatabase gameDatabase;

    public CastleController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
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
