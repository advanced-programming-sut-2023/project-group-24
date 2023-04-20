package model.army;

import model.Item;
import model.StringFunctions;

public enum SoldierType {
    ;
    private final Item armor;
    private final Item weapon;
    private final boolean canClimbLadder;
    private final Nation nation;
    private final boolean canDig;

    SoldierType(Item armor, Item weapon, boolean canClimbLadder,
                Nation nation, boolean canDig) {
        this.armor = armor;
        this.weapon = weapon;
        this.canClimbLadder = canClimbLadder;
        this.nation = nation;
        this.canDig = canDig;
    }

    public static SoldierType stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (SoldierType value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public Item getArmor() {
        return armor;
    }

    public Item getWeapon() {
        return weapon;
    }

    public boolean isCanClimbLadder() {
        return canClimbLadder;
    }

    public Nation getNation() {
        return nation;
    }
}
