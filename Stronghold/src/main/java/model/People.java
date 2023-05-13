package model;

import model.buildings.Building;

public class People {
    private final Kingdom owner;
    private Building workStation;

    public People(Kingdom owner) {
        this.owner = owner;
        workStation = null;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public Building getWorkStation() {
        return workStation;
    }

    public void setWorkStation(Building workStation) {
        this.workStation = workStation;
    }
}