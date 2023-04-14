package model.army;

import model.map.Cell;

public class Soldier extends Army {
    private final SoldierType soldierType;

    public Soldier(Cell location, ArmyType armyType, SoldierType soldierType) {
        super(location, armyType);
        this.soldierType = soldierType;
    }

    public SoldierType getSoldierType() {
        return soldierType;
    }
}
