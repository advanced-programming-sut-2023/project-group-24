package model.army;

import model.storage.Armor;
import model.storage.Weapon;

public enum SoldierType {
    ;
    private final Armor armor;
    private final Weapon weapon;
    private final boolean canClimbLadder;
    private final Nation nation;
    private final boolean canDig;

    SoldierType(Armor armor, Weapon weapon, boolean canClimbLadder,
                Nation nation, boolean canDig) {
        this.armor = armor;
        this.weapon = weapon;
        this.canClimbLadder = canClimbLadder;
        this.nation = nation;
        this.canDig = canDig;
    }

    public static SoldierType stringToEnum(String name) {
        //TODO ...
        return null;
    }

    public Armor getArmor() {
        return armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean isCanClimbLadder() {
        return canClimbLadder;
    }

    public Nation getNation() {
        return nation;
    }
}
