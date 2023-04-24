package model.buildings;

import model.Kingdom;
import model.map.Cell;

public class Stable extends Building {
    private int numberOfHorses;
    private int numberOfAvailableHorses;

    public Stable(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
        numberOfHorses = 0;
        numberOfAvailableHorses = 0;
    }

    public int getNumberOfHorses() {
        return numberOfHorses;
    }

    public int getNumberOfAvailableHorses() {
        return numberOfAvailableHorses;
    }

    public void produceHorse() {
        numberOfHorses++;
    }

    public void useHorse() {
        numberOfAvailableHorses++;
    }

    public void killHorse() {
        numberOfHorses--;
    }
}
