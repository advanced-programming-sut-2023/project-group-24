package model.buildings;

import model.Kingdom;
import model.enums.Direction;
import model.map.Cell;

import java.util.ArrayList;

public class DefenceBuilding extends Building {
    private final ArrayList<Direction> ladderState;
    private final ArrayList<Direction> staircase;

    public DefenceBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        ladderState = new ArrayList<>();
        staircase = new ArrayList<>();
    }

    public boolean hasLadderState(Direction direction) {
        return ladderState.contains(direction);
    }

    public void addLadder(Direction ladderState) {
        this.ladderState.add(ladderState);
    }

    public boolean hasStaircase(Direction direction) {
        return staircase.contains(direction);
    }

    public void addStaircase(Direction staircase) {
        this.staircase.add(staircase);
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        if (ladderState.size() == 0) {
            output.add("There are no ladders here!");
            return output;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("There are ladders on this building from: %s",
                ladderState.get(0).toString().toLowerCase()));
        for (int i = 1; i < ladderState.size(); i++)
            stringBuilder.append(", ").append(ladderState.get(i).toString().toLowerCase());
        output.add(stringBuilder.append("!").toString());
        return output;
    }
}
