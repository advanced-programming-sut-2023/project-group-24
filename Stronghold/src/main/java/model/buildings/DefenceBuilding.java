package model.buildings;

import model.LadderState;

public class DefenceBuilding extends Building {
    private LadderState ladderState;
    private LadderState staircase;

    public DefenceBuilding(Kingdom kingdom, BuildingType buildingType) {
        //TODO
    }

    public LadderState getLadderState() {
        return ladderState;
    }

    public LadderState getStaircase() {
        return staircase;
    }

    public void addLadder(LadderState ladderState) {
        //TODO set ladder state
    }

    public void addStaircase(LadderState staircase) {
        //TODO set staircase
    }
}
