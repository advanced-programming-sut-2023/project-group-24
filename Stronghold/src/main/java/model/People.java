package model;

import model.map.Cell;

public class People {
    private Cell location;
    private boolean isWorking;

    public People(Cell location) {
        this.location = location;
        isWorking = false;
    }

    public Cell getLocation() {
        return location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
