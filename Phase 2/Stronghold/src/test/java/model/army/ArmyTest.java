package model.army;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ArmyTest {
    private final Map map = new Map(10, "test");
    private final Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Army army = new Soldier(map.getMap()[2][2], ArmyType.ARABIAN_SWORD_MAN, kingdom, SoldierType.ARABIAN_SWORD_MAN);
    private final Army army2 = new Soldier(map.getMap()[4][2], ArmyType.ENGINEER, kingdom2, SoldierType.ENGINEER);

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
        Assertions.assertEquals(army.getUnitState(), UnitState.DEFENSIVE);
        army.changeState(UnitState.stringToEnum("offensive"));
        Assertions.assertEquals(army.getUnitState(), UnitState.OFFENSIVE);
    }

    @Test
    void getHp() {
        Assertions.assertEquals(army.getHp(), 150);
    }

    @Test
    void takeDamage() {
        army.takeDamage(10);
        Assertions.assertEquals(army.getHp(), 140);
    }

    @Test
    void moveArmy() {
        army.setPath(new ArrayList<>(List.of(map.getMap()[2][3], map.getMap()[3][3])));
        army.moveArmy();
        Assertions.assertEquals(army.getLocation(), map.getMap()[2][3]);
        army.moveArmy();
        Assertions.assertEquals(army.getLocation(), map.getMap()[3][3]);
    }

    @Test
    void getPath() {
        Assertions.assertEquals(army.getPath().size(), 0);
    }

    @Test
    void setPath() {
        army.setPath(new ArrayList<>(List.of(map.getMap()[2][3], map.getMap()[3][3])));
        Assertions.assertEquals(army.getPath().size(), 2);
        army.moveArmy();
        Assertions.assertEquals(army.getPath().size(), 1);
    }

    @Test
    void getOwner() {
        Assertions.assertEquals(army.getOwner(), kingdom);
    }

    @Test
    void getArmyType() {
        Assertions.assertEquals(army.getArmyType(), ArmyType.ARABIAN_SWORD_MAN);
    }

    @Test
    void getTarget() {
        Assertions.assertNull(army.getTarget());
    }

    @Test
    void setTarget() {
        army.setTarget(army2);
        Assertions.assertEquals(army.getTarget(), army2);
    }

    @Test
    void getPatrol() {
        Assertions.assertNull(army.getPatrol());
    }

    @Test
    void setPatrol() {
        army.setPatrol(new Pair<>(map.getMap()[2][2], map.getMap()[7][7]));
        Assertions.assertNotNull(army.getPatrol());
    }

    @Test
    void getTargetCell() {
        Assertions.assertNull(army.getTargetCell());
    }

    @Test
    void setTargetCell() {
        army.setTargetCell(map.getMap()[4][4]);
        Assertions.assertEquals(army.getTargetCell(), map.getMap()[4][4]);
    }

    @Test
    void changeEngineerState() {
        army.changeEngineerState();
        Assertions.assertEquals(army.getArmyType(), ArmyType.ARABIAN_SWORD_MAN);
        Assertions.assertEquals(army2.getArmyType(), ArmyType.ENGINEER);
        army2.changeEngineerState();
        Assertions.assertEquals(army2.getArmyType(), ArmyType.ENGINEER_WITH_OIL);
        army2.changeEngineerState();
        Assertions.assertEquals(army2.getArmyType(), ArmyType.ENGINEER);
    }
}