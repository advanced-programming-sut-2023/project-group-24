package controller.gamecontrollers;

import model.databases.GameDatabase;
import view.enums.messages.BuildingControllerMessages;

public class BuildingController {
    private final GameDatabase gameDatabase;

    public BuildingController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public BuildingControllerMessages dropBuilding(int x, int y, String type) {
        //TODO generate the cell, then generate the building type, to check if it can be built there then find the current Kingdom in the game database
        return null;
    }

    public BuildingControllerMessages selectBuilding(int x, int y) {
        //TODO check if there is a building in that cell and the owner is the current player. then set the current building.
        return null;
    }

    public BuildingControllerMessages createUnit(String name, int count) {
        //TODO check if the building can create a troop, check if it can create this troop, and check for resources
        return null;
    }

    public BuildingControllerMessages repair() {
        //TODO check if the current building can be repaired
        return null;
    }

    public BuildingControllerMessages setTaxRate(int taxRate) {
        //TODO setTaxRate
        return null;
    }

    public BuildingControllerMessages createResources(String resourceName) {
        //TODO check if it can make that resource
        return null;
    }
}
