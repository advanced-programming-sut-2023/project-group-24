package model.buildings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuildingTypeTest {
    private BuildingType buildingType = BuildingType.TOWN_HALL;

    @Test
    void getBuildingTypeFromName() {
        Assertions.assertEquals(BuildingType.getBuildingTypeFromName("town hall"), buildingType);
        Assertions.assertEquals(BuildingType.getBuildingTypeFromName("moat"), BuildingType.MOAT);
        Assertions.assertNull(BuildingType.getBuildingTypeFromName("chert"));
    }

    @Test
    void getName() {
        Assertions.assertEquals(buildingType.getName(), "town hall");
    }

    @Test
    void getCategory() {
        Assertions.assertEquals(buildingType.getCategory(), BuildingType.Category.CASTLE);
    }

    @Test
    void getMaterialToBuild() {
        Assertions.assertNull(buildingType.getMaterialToBuild());
    }

    @Test
    void getPrice() {
        Assertions.assertEquals(buildingType.getPrice(), 0);
    }

    @Test
    void getMaxHp() {
        Assertions.assertEquals(buildingType.getMaxHp(), 2000);
    }

    @Test
    void getWorkersNeeded() {
        Assertions.assertEquals(buildingType.getWorkersNeeded(), 0);
    }

    @Test
    void getHomeCapacity() {
        Assertions.assertEquals(buildingType.getHomeCapacity(), 8);
    }

    @Test
    void getHeight() {
        Assertions.assertEquals(buildingType.getHeight(), 0);
    }

    @Test
    void getAttackPoint() {
        Assertions.assertEquals(buildingType.getAttackPoint(), 0);
    }

    @Test
    void getPopularityRate() {
        Assertions.assertEquals(buildingType.getPopularityRate(), 0);
    }

    @Test
    void getStorageCapacity() {
        Assertions.assertEquals(buildingType.getStorageCapacity(), 0);
    }

    @Test
    void getProduceRate() {
        Assertions.assertEquals(buildingType.getProduceRate(), 0);
    }

    @Test
    void getUses() {
        Assertions.assertNull(buildingType.getUses());
    }

    @Test
    void getProduces() {
        Assertions.assertNull(buildingType.getProduces());
    }

    @Test
    void getProducePrice() {
        Assertions.assertEquals(buildingType.getProducePrice(), 0);
    }

    @Test
    void getTroopsItCanMake() {
        Assertions.assertNull(buildingType.getTroopsItCanMake());
    }

    @Test
    void getItemsItCanHold() {
        Assertions.assertNull(buildingType.getItemsItCanHold());
    }

    @Test
    void getItemsItCanMove() {
        Assertions.assertNull(buildingType.getItemsItCanMove());
    }

    @Test
    void canBeDestroyedByTunnels() {
        Assertions.assertFalse(buildingType.canBeDestroyedByTunnels());
    }

    @Test
    void canBeRepaired() {
        Assertions.assertFalse(buildingType.canBeRepaired());
    }

    @Test
    void getBuildingClass() {
        Assertions.assertEquals(buildingType.getBuildingClass(), Building.class);
    }

    @Test
    void getCanOnlyBuiltOn() {
        Assertions.assertNull(buildingType.getCanOnlyBuiltOn());
    }
}