package model.buildings;

import model.enums.Direction;
import model.Kingdom;
import model.map.Cell;

public class DefenceBuilding extends Building {
    private Direction ladderState;
    private Direction staircase;

    public DefenceBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        ladderState = Direction.NONE;
        staircase = Direction.NONE;
    }

    public Direction getLadderState() {
        return ladderState;
    }

    public void setLadderState(Direction ladderState) {
        this.ladderState = ladderState;
    }

    public Direction getStaircase() {
        return staircase;
    }

    public void setStaircase(Direction staircase) {
        this.staircase = staircase;
    }

    public void addLadder(Direction ladderState) {
        this.ladderState = ladderState;
    }

    public void addStaircase(Direction staircase) {
        this.staircase = staircase;
    }
}
