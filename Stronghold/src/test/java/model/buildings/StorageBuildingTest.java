package model.buildings;

import model.Kingdom;
import model.enums.Item;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

class StorageBuildingTest {
    private Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private Map map = new Map(10, "testMap");
    private StorageBuilding storageBuilding = new StorageBuilding(kingdom, map.getMap()[2][2], BuildingType.GRANARY);

    @Test
    void getNumberOfItemsInStorage() {
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 0);
    }

    @Test
    void changeItemCount() {
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 100));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 100);
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 110);
        storageBuilding.changeItemCount(new Pair<>(Item.BREAD, 10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 120);
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, -20));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 100);
        storageBuilding.changeItemCount(new Pair<>(Item.BREAD, -20));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 90);
        storageBuilding.changeItemCount(new Pair<>(Item.MEAT, 200));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 250);
        storageBuilding.changeItemCount(new Pair<>(Item.MEAT, -10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 240);
        storageBuilding.changeItemCount(new Pair<>(Item.MEAT, -2000));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 90);
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, -2000));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 0);
    }

    @Test
    void useFood() {
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 100));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 100);
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 110);
        storageBuilding.changeItemCount(new Pair<>(Item.BREAD, 10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 120);
        storageBuilding.useFood(100);
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 20);
        storageBuilding.useFood(100);
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 0);
    }

    @Test
    void getStockedNumber() {
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 100));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 100);
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 110);
        storageBuilding.changeItemCount(new Pair<>(Item.BREAD, 10));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 120);
        storageBuilding.changeItemCount(new Pair<>(Item.APPLE, 20));
        Assertions.assertEquals(storageBuilding.getNumberOfItemsInStorage(), 140);
        Assertions.assertEquals(storageBuilding.getStockedNumber(Item.APPLE), 130);
        Assertions.assertEquals(storageBuilding.getStockedNumber(Item.BREAD), 10);
    }

    @Test
    void showDetails() {
        Assertions.assertTrue(storageBuilding.showDetails().size() > 2);
    }
}