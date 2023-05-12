package model.army;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmyTypeTest {
    private ArmyType armyType = ArmyType.ARCHER;

    @Test
    void stringToEnum() {
        for (ArmyType value : ArmyType.values()) {
            Assertions.assertEquals(value, ArmyType.stringToEnum(value.toString()));
        }
    }

    @Test
    void getMaxHp() {
        Assertions.assertEquals(armyType.getMaxHp(), 96);
    }

    @Test
    void getRange() {
        Assertions.assertEquals(armyType.getRange(), 5);
    }

    @Test
    void getDamage() {
        Assertions.assertEquals(armyType.getDamage(), 16);
    }

    @Test
    void getSpeed() {
        Assertions.assertEquals(armyType.getSpeed(), 4);
    }

    @Test
    void getPrice() {
        Assertions.assertEquals(armyType.getPrice(), 12);
    }

    @Test
    void testToString() {
        Assertions.assertEquals(ArmyType.ARABIAN_SWORD_MAN.toString(), "arabian sword man");
    }
}