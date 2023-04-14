package controller.gamecontrollers;

import model.GameDatabase;
import model.buildings.Building;
import utils.enums.messages.BuildingControllerMessages;

public class BuildingController {
    private GameDatabase gameDatabase;
    private Building currentBuilding;

    public BuildingController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    private BuildingControllerMessages dropBuilding(int x, int y, String type) {
        //TODO generate the cell, then generate the building type, to check if it can be built there then find the current Kingdom in the game database
    }

    private BuildingControllerMessages selectBuilding(int x, int y) {
        //TODO check if there is a building in that cell and the owner is the current player. then set the current building.
    }

    private BuildingControllerMessages createUnit(String name, int count) {
        //TODO check if the building can create a troop, check if it can create this troop, and check for resources
    }

    private BuildingControllerMessages repair() {
        //TODO check if the current building can be repaired
    }
}
