package model.buildings;

import model.Kingdom;
import model.People;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WorkersNeededBuildingTest extends BuildingTest {
    private final Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private final Map map = new Map(10, "testMap");
    private final WorkersNeededBuilding building = new WorkersNeededBuilding(kingdom, map.getMap()[2][2], BuildingType.MILL);

    @Test
    void isSleeping() {
        Assertions.assertFalse(building.isSleeping());
    }

    @Test
    void hasEnoughWorkers() {
        Assertions.assertFalse(building.hasEnoughWorkers());
    }

    @Test
    void assignWorker() {
        building.assignWorker(new People());
        Assertions.assertFalse(building.hasEnoughWorkers());
        building.assignWorker(new People());
        building.assignWorker(new People());
        Assertions.assertTrue(building.hasEnoughWorkers());
    }

    @Test
    void unAssignWorker() {
        building.assignWorker(new People());
        Assertions.assertFalse(building.hasEnoughWorkers());
        building.assignWorker(new People());
        People people = new People();
        building.assignWorker(people);
        Assertions.assertTrue(building.hasEnoughWorkers());
        building.unAssignWorker(people);
        Assertions.assertFalse(building.hasEnoughWorkers());
        building.assignWorker(people);
        building.unAssignWorker(new People());
        Assertions.assertTrue(building.hasEnoughWorkers());
    }

    @Test
    void changeSleepingMode() {
        building.changeSleepingMode();
        building.changeSleepingMode();
        Assertions.assertFalse(building.isSleeping());
        building.changeSleepingMode();
        Assertions.assertTrue(building.isSleeping());
    }

    @Test
    void testShowDetails() {
        Assertions.assertEquals(building.showDetails().get(2), "Number of workers: 0/3 !");
        building.assignWorker(new People());
        building.assignWorker(new People());
        Assertions.assertEquals(building.showDetails().get(2), "Number of workers: 2/3 !");
        building.assignWorker(new People());
        Assertions.assertEquals(building.showDetails().get(2), "Number of workers: 3/3 !");
    }
}