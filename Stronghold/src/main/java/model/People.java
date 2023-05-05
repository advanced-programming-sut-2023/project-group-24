package model;

import model.buildings.Building;
import model.map.Cell;

public class People {
    private Kingdom owner;
    private Building workStation;

    public People(Kingdom owner) {
        this.owner = owner;
        workStation = null;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public void setOwner(Kingdom owner) {
        this.owner = owner;
    }

    public Building getWorkStation() {
        return workStation;
    }

    public void setWorkStation(Building workStation) {
        this.workStation = workStation;
    }
}
