package view.menus;

import controller.createmapcontrollers.CastleController;
import controller.createmapcontrollers.LandscapeController;

import java.util.regex.Matcher;

public class CreateMapMenu {
    private LandscapeController landscapeController;
    private CastleController castleController;

    public CreateMapMenu(LandscapeController landscapeController, CastleController castleController) {
        this.landscapeController = landscapeController;
        this.castleController = castleController;
    }

    private void setTexture(Matcher matcher) {
        //TODO connect to landscape controller
    }

    private void clear(Matcher matcher) {
        //TODO
    }

    private void dropRock(Matcher matcher) {
        //TODO
    }

    private void dropTree(Matcher matcher) {
        //TODO
    }

    private void setCurrentKingdom(Matcher matcher) {
        //TODO
    }

    private void dropBuilding(Matcher matcher) {
        //TODO
    }
    private void dropUnit(Matcher matcher) {
        //TODO
    }
}