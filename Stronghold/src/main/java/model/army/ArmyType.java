package model.army;

import model.StringFunctions;

public enum ArmyType {
    ARCHER(12, 96, 5, 16, 4),
    CROSSBOWMEN(20, 128, 3, 24, 2),
    SPEARMAN(8, 64, 0, 24, 3),
    PIKE_MAN(20, 160, 0, 28, 2),
    MACE_MAN(20, 128, 0, 32, 3),
    SWORD_MAN(40, 160, 0, 48, 1),
    KNIGHT(40, 170, 0, 48, 5),
    TUNNELLER(30, 64, 0, 24, 4),
    LADDER_MAN(4, 64, 0, 0, 4),
    ENGINEER(30, 64, 0, 0, 3),
    BLACK_MONK(10, 128, 0, 24, 1),
    ARCHER_BOW(75, 96, 5, 16, 4),
    SLAVE(5, 48, 0, 8, 4),
    SLINGER(12, 64, 2, 16, 4),
    ASSASSIN(60, 128, 0, 28, 3),
    HORSE_ARCHER(80, 128, 5, 24, 5),
    ARABIAN_SWORD_MAN(80, 150, 0, 32, 5),
    FIRE_THROWER(100, 96, 3, 32, 5),
    //WAR_MACHINES:
    SIEGE_TOWER(150, 600, 0, 0, 1),
    PORTABLE_SHIELDS(5, 300, 0, 0, 1),
    BATTERING_RAMS(150, 1000, 1, 334, 1),
    CATAPULT(150, 150, 5, 200, 2),
    TREBUCHETS(150, 400, 7, 334, 0),
    FIRE_BALLISTA(150, 150, 6, 70, 2),
    //LORD
    LORD(0, 0, 0, 0, 0);
    private final int price;
    private final int maxHp;
    private final int range;
    private final int damage;
    private final int speed;//cell per turn

    ArmyType(int price, int maxHp, int range, int damage, int speed) {
        this.price = price;
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

    public int getPrice() {
        return price;
    }
}
