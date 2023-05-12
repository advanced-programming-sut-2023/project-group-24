package model.army;

import model.enums.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoldierTypeTest {
    private SoldierType soldierType = SoldierType.CROSSBOWMAN;

    @Test
    void stringToEnum() {
        for (SoldierType value : SoldierType.values()) {
            Assertions.assertEquals(value, SoldierType.stringToEnum(value.toString()));
        }
    }

    @Test
    void getArmor() {
        Assertions.assertEquals(soldierType.getArmor(), Item.LEATHER_ARMOR);
    }

    @Test
    void getWeapon() {
        Assertions.assertEquals(soldierType.getWeapon(), Item.CROSSBOW);
    }

    @Test
    void isCanClimbLadder() {
        Assertions.assertFalse(soldierType.isCanClimbLadder());
    }

    @Test
    void getNation() {
        Assertions.assertEquals(soldierType.getNation(), Type.EUROPEAN);
    }

    @Test
    void isCanDig() {
        Assertions.assertFalse(soldierType.isCanDig());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("engineer with oil", SoldierType.ENGINEER_WITH_OIL.toString());
    }
}