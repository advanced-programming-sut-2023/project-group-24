package model.army;

public enum ArmyType {
    ARCHER(12, 5, 23, 4);
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
        //TODO ...
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
