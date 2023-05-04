package model.army;

import controller.MainController;
import model.enums.Item;

public enum SoldierType {
    LORD(null, null, false, Type.EUROPEAN, false),
    ARCHER(null, Item.BOW, true, Type.EUROPEAN, true),
    CROSSBOWMAN(Item.LEATHER_ARMOR, Item.CROSSBOW, false, Type.EUROPEAN, false),
    SPEARMAN(null, Item.CROSSBOW, true, Type.EUROPEAN, true),
    PIKE_MAN(Item.METAL_ARMOR, Item.PIKE, false, Type.EUROPEAN, true),
    MACE_MAN(Item.LEATHER_ARMOR, Item.MACE, true, Type.EUROPEAN, true),
    SWORD_MAN(Item.METAL_ARMOR, Item.SWORD, false, Type.EUROPEAN, false),
    KNIGHT(Item.METAL_ARMOR, Item.SWORD, false, Type.EUROPEAN, false),
    TUNNELLER(null, null, false, Type.EUROPEAN, false),
    LADDER_MAN(null, null, false, Type.ENGINEER, false),
    ENGINEER(null, null, false, Type.ENGINEER, true),
    ENGINEER_WITH_OIL(null, null, false, Type.ENGINEER, true),
    BLACK_MONK(null, null, false, Type.CHURCH, false),
    ARCHER_BOW(null, null, false, Type.ARABIAN, true),
    SLAVE(null, null, false, Type.ARABIAN, true),
    SLINGER(null, null, false, Type.ARABIAN, false),
    ASSASSIN(null, null, false, Type.ARABIAN, false),
    HORSE_ARCHER(null, null, false, Type.ARABIAN, false),
    ARABIAN_SWORD_MAN(null, null, false, Type.ARABIAN, false),
    FIRE_THROWER(null, null, false, Type.ARABIAN, false);
    private final Item armor;
    private final Item weapon;
    private final boolean canClimbLadder;
    private final Type nation;
    private final boolean canDig;

    SoldierType(Item armor, Item weapon, boolean canClimbLadder,
                Type nation, boolean canDig) {
        this.armor = armor;
        this.weapon = weapon;
        this.canClimbLadder = canClimbLadder;
        this.nation = nation;
        this.canDig = canDig;
    }

    public static SoldierType stringToEnum(String name) {
        String string = MainController.turnSpaceToUnderline(name);
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

    public Type getNation() {
        return nation;
    }

    public boolean isCanDig() {
        return canDig;
    }

    public String toString() {
        return super.toString().toLowerCase().replaceAll("-", " ");
    }
}
