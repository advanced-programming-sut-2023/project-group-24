package model.buildings;

import java.util.ArrayList;

public class EngineersNeededBuilding extends WorkersNeededBuilding{
    private ArrayList<Soldier> engineers;

    public EngineersNeededBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        //TODO
    }

    @Override
    public void assignWorker(People person) {
        //TODO assign the said worker
    }

    @Override
    public boolean hasEnoughWorkers() {
        //TODO i think its so obvious, it doesnt need proper punctuation, except commas
    }
}