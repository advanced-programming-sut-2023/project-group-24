package model;

import model.army.Army;
import model.army.ArmyType;
import model.enums.KingdomColor;
import model.map.Cell;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

class KingdomTest {

    Cell cell = new Cell(0, 0);
    Kingdom kingdom = new Kingdom(KingdomColor.RED);
    Army army = new Army(cell, ArmyType.ARCHER, kingdom);
    User user = new User("olala", "asdf", "dasf", "dsfsa", "asfd", new Pair<>(1, "ads"));

    @Test
    void getOwner() {
        kingdom.setOwner(user);
        assertEquals(user, kingdom.getOwner());
    }

    @Test
    void setOwner() {
        kingdom.setOwner(user);
        assertEquals(user, kingdom.getOwner());
    }

    @Test
    void getPopularity() {
        assertEquals(75, kingdom.getPopularity());
    }

    @Test
    void changePopularity() {
        kingdom.changePopularity(10);
        assertEquals(85, kingdom.getPopularity());
        kingdom.changePopularity(25);
        assertEquals(100, kingdom.getPopularity());
        kingdom.changePopularity(-120);
        assertEquals(0, kingdom.getPopularity());
    }

    @Test
    void getPopulation() {
        System.out.println(kingdom.getPopulation());
    }

    @Test
    void addPeople() {
    }

    @Test
    void getUnemployment() {
    }

    @Test
    void getGold() {
    }

    @Test
    void getPopularityFactors() {
    }

    @Test
    void changeGold() {
    }

    @Test
    void addArmy() {
    }

    @Test
    void removeArmy() {
    }

    @Test
    void removeArmies() {
    }

    @Test
    void getColor() {
    }

    @Test
    void getTrades() {
    }

    @Test
    void getStockedNumber() {
    }

    @Test
    void changeStockNumber() {
    }

    @Test
    void setStorage() {
    }

    @Test
    void removeBuilding() {
    }

    @Test
    void addBuilding() {
    }
}