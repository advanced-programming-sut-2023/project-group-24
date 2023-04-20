package model.army;

import model.StringFunctions;

public enum ArmyType {
    LORD(12, 12, 12, 4),
    ARCHER(12, 5, 23, 4),
    CROSSBOWMEN(12, 2, 3, 4);
    private final int maxHp;
    private final int range;
    private final int damage;
    private final int speed;//cell per turn

    ArmyType(int maxHp, int range, int damage, int speed) {
        this.maxHp = maxHp;
        this.range = range;
        this.damage = damage;
        this.speed = speed;
    }

    public static ArmyType stringToEnum(String name) {
        String string = StringFunctions.turnSpaceToUnderline(name);
        for (ArmyType value : values())
            if (string.equalsIgnoreCase(value.toString()))
                return value;
        return null;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

}
