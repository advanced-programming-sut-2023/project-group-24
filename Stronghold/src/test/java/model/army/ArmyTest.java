package model.army;

import model.Kingdom;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {
    private Map map = new Map(10, "test");
    private Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private Army army = new Soldier(map.getMap()[2][2], ArmyType.ARABIAN_SWORD_MAN, kingdom, SoldierType.ARABIAN_SWORD_MAN);

    @Test
    void isDead() {
        Assertions.assertEquals(kingdom.getArmies().size(), 1);
        Assertions.assertEquals(map.getMap()[2][2].getArmies().size(), 1);
        army.isDead();
        Assertions.assertEquals(kingdom.getArmies().size(), 0);
        Assertions.assertEquals(map.getMap()[2][2].getArmies().size(), 0);
    }

    @Test
    void getLocation() {
        Assertions.assertEquals(army.getLocation(), map.getMap()[2][2]);
    }

    @Test
    void getUnitState() {
        Assertions.assertEquals(army.getUnitState(), UnitState.STANDING);
    }

    @Test
    void changeState() {
        army.changeState(UnitState.DEFENSIVE);
        Assertions.assertEquals(army.getUnitState(), UnitState.STANDING);
        army.changeState(UnitState.stringToEnum());
    }

    @Test
    void getHp() {
    }

    @Test
    void takeDamage() {
    }

    @Test
    void moveArmy() {
    }

    @Test
    void getPath() {
    }

    @Test
    void setPath() {
    }

    @Test
    void getOwner() {
    }

    @Test
    void getArmyType() {
    }

    @Test
    void getTarget() {
    }

    @Test
    void setTarget() {
    }

    @Test
    void getPatrol() {
    }

    @Test
    void setPatrol() {
    }

    @Test
    void getTargetCell() {
    }

    @Test
    void setTargetCell() {
    }

    @Test
    void changeEngineerState() {
    }

    @Test
    void setArmyType() {
    }
}