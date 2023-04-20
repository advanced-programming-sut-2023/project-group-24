package model.buildings;

import model.Kingdom;
import model.LadderState;
import model.map.Cell;

public class DefenceBuilding extends Building {
    private LadderState ladderState;
    private LadderState staircase;

    public DefenceBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        ladderState = LadderState.NONE;
        staircase = LadderState.NONE;
    }

    public LadderState getLadderState() {
        return ladderState;
    }

    public LadderState getStaircase() {
        return staircase;
    }

    public void setLadderState(LadderState ladderState) {
        this.ladderState = ladderState;
    }

    public void setStaircase(LadderState staircase) {
        this.staircase = staircase;
    }

    public void addLadder(LadderState ladderState) {
        this.ladderState = ladderState;
    }

    public void addStaircase(LadderState staircase) {
        this.staircase = staircase;
    }
}
