package model;

import model.buildings.Building;

public class People {
    private Building workStation;

    public People() {
        workStation = null;
    }

    public Building getWorkStation() {
        return workStation;
    }

    public void setWorkStation(Building workStation) {
        this.workStation = workStation;
    }
}