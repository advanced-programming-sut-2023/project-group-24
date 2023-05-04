package model;

import model.map.Cell;

public class People {
    private Kingdom owner;
    private boolean isWorking;

    public People(Kingdom owner) {
        this.owner = owner;
        isWorking = false;
    }

    public Kingdom getOwner() {
        return owner;
    }

    public void setOwner(Kingdom owner) {
        this.owner = owner;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
