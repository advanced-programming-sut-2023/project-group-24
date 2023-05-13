package model.buildings;

import model.Kingdom;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuildingTest {
    private final Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private final Map map = new Map(10, "testMap");

    @Test
    void getBuildingFromBuildingType() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        Assertions.assertEquals(map.getMap()[2][2].getExistingBuilding(), building);
        Assertions.assertEquals(kingdom.getBuildings().get(0), building);
    }

    @Test
    void getBuildingType() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        Assertions.assertEquals(building.getBuildingType(), BuildingType.TOWN_HALL);
    }

    @Test
    void getKingdom() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        Assertions.assertEquals(building.getKingdom(), kingdom);
    }

    @Test
    void getLocation() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        Assertions.assertEquals(building.getLocation(), map.getMap()[2][2]);
    }

    @Test
    void getHp() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        Assertions.assertEquals(building.getHp(), 2000);
    }

    @Test
    void takeDamage() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        building.takeDamage(100);
        Assertions.assertEquals(building.getHp(), 1900);
    }

    @Test
    void repair() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        building.takeDamage(100);
        building.repair();
        Assertions.assertEquals(building.getHp(), 2000);
    }

    @Test
    void showDetails() {
        Building building = Building.getBuildingFromBuildingType(kingdom, map.getMap()[2][2], BuildingType.TOWN_HALL);
        Assertions.assertNotNull(building);
        Assertions.assertEquals(building.showDetails().size(), 2);
    }
}