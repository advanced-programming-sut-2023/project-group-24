package model.buildings;

import model.Kingdom;
import model.enums.Direction;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefenceBuildingTest extends BuildingTest {
    private Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private Map map = new Map(10, "testMap");
    private DefenceBuilding defenceBuilding = new DefenceBuilding(kingdom, map.getMap()[2][2], BuildingType.LOW_WALL);

    @Test
    void hasLadderState() {
        Assertions.assertFalse(defenceBuilding.hasLadderState(Direction.UP));
    }

    @Test
    void addLadder() {
        defenceBuilding.addLadder(Direction.UP);
        Assertions.assertTrue(defenceBuilding.hasLadderState(Direction.UP));
    }

    @Test
    void hasStaircase() {
        Assertions.assertFalse(defenceBuilding.hasStaircase(Direction.UP));
    }

    @Test
    void addStaircase() {
        defenceBuilding.addStaircase(Direction.UP);
        Assertions.assertTrue(defenceBuilding.hasStaircase(Direction.UP));
    }

    @Test
    void showDetails() {
        Assertions.assertEquals(defenceBuilding.showDetails().get(2), "There are no ladders here!");
        defenceBuilding.addLadder(Direction.UP);
        Assertions.assertEquals(defenceBuilding.showDetails().get(2), "There are ladders on this building from: up!");
        defenceBuilding.addLadder(Direction.RIGHT);
        defenceBuilding.addLadder(Direction.DOWN);
        Assertions.assertEquals(defenceBuilding.showDetails().get(2), "There are ladders on this building from: up, right, down!");
    }
}