package model.army;

import model.Kingdom;
import model.map.Cell;

public class Soldier extends Army {
    private final SoldierType soldierType;
    private boolean canBurnOnce;

    public Soldier(Cell location, ArmyType armyType, Kingdom owner, SoldierType soldierType) {
        super(location, armyType, owner);
        this.soldierType = soldierType;
    }

    public SoldierType getSoldierType() {
        return soldierType;
    }

    public void setCanBurnOnce(boolean canBurnOnce) {
        this.canBurnOnce = canBurnOnce;
    }

    public boolean visibility() {
        for (Army army : getLocation().getArmies()) {
            if (!army.getOwner().equals(getOwner()))
                return true;
        }
        return false;
    }

    @Override
    public boolean canBurn() {
        return getArmyType().canBurn() || canBurnOnce;
    }
}
