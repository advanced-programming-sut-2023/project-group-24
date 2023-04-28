package model.army;

import model.Kingdom;
import model.enums.KingdomColor;
import model.map.Cell;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {
    Cell cell = new Cell(0, 0);
    Kingdom kingdom = new Kingdom(KingdomColor.RED);
    Army army = new Army(cell, ArmyType.ARCHER, kingdom);

    @Test
    void isDead() {
        assertFalse(army.isDead());
        army.takeDamage(10000);
        assertTrue(army.isDead());
    }

    @Test
    void getLocation() {
        assertEquals(army.getLocation(), cell);
    }

    @Test
    void getUnitState() {
        assertEquals(army.getUnitState(), UnitState.STANDING);
    }

    @Test
    void changeState() {
        army.changeState(UnitState.DEFENSIVE);
        assertEquals(army.getUnitState(), UnitState.DEFENSIVE);
    }

    @Test
    void getHp() {
        assertEquals(96, army.getHp());
        army.takeDamage(11);
        assertEquals(85, army.getHp());
        army.takeDamage(89);
        assertEquals(0, army.getHp());
    }

    @Test
    void takeDamage() {
        army.takeDamage(10000);
        assertTrue(army.isDead());
    }

}