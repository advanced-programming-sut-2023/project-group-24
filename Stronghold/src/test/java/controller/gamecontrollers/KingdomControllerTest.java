package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.StorageBuilding;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.KingdomColor;
import model.enums.PopularityFactor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class KingdomControllerTest {
    private final Map map = new Map(200, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private final KingdomController kingdomController = new KingdomController(gameDatabase);

    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }

    @Test
    void nextTurn() {
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][2], BuildingType.STOCKPILE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][3], BuildingType.STOCKPILE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][4], BuildingType.ARMOURY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][5], BuildingType.ARMOURY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][6], BuildingType.GRANARY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][7], BuildingType.GRANARY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][8], BuildingType.GRANARY);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 3));
        kingdomController.changeStockedNumber(new Pair<>(Item.SWORD, 70));
        kingdomController.changeStockedNumber(new Pair<>(Item.APPLE, 600));
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[5][5], BuildingType.HOVEL);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[5][6], BuildingType.HOVEL);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[5][7], BuildingType.HOVEL);
        for (int i = 0; i < BuildingType.values().length; i++) {
            Building.getBuildingFromBuildingType(kingdom1, map.getMap()[0][i], BuildingType.values()[i]);
        }
        kingdomController.nextTurn();
        kingdomController.nextTurn();
        kingdomController.nextTurn();
        kingdomController.nextTurn();
        kingdomController.nextTurn();
        kingdomController.nextTurn();
    }

    @Test
    void changeStockedNumber() {
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][2], BuildingType.STOCKPILE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][3], BuildingType.STOCKPILE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][4], BuildingType.ARMOURY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][5], BuildingType.ARMOURY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][6], BuildingType.GRANARY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][7], BuildingType.GRANARY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][8], BuildingType.GRANARY);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 3));
        kingdomController.changeStockedNumber(new Pair<>(Item.SWORD, 70));
        kingdomController.changeStockedNumber(new Pair<>(Item.APPLE, 600));
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(0)).getNumberOfItemsInStorage(), 3);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, -1));
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(0)).getNumberOfItemsInStorage(), 2);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 210));
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(0)).getNumberOfItemsInStorage(), 200);
        Assertions.assertEquals(((StorageBuilding) map.getMap()[2][3].getExistingBuilding()).getNumberOfItemsInStorage(), 12);
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(2)).getNumberOfItemsInStorage(), 50);
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(3)).getNumberOfItemsInStorage(), 20);
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(4)).getNumberOfItemsInStorage(), 250);
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(5)).getNumberOfItemsInStorage(), 250);
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(6)).getNumberOfItemsInStorage(), 100);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, -10));
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(0)).getNumberOfItemsInStorage(), 190);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, -200));
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(0)).getNumberOfItemsInStorage(), 0);
        Assertions.assertEquals(((StorageBuilding) kingdom1.getBuildings().get(1)).getNumberOfItemsInStorage(), 2);
    }

    @Test
    void showPopularityFactors() {
        String expected = "fear: 0\nfood: 0\nInn: 0\ntax: 1\nhomeless: 0\nreligion: 0";
        Assertions.assertEquals(expected, kingdomController.showPopularityFactors());
        kingdom1.setPopularityFactor(PopularityFactor.FEAR, -2);
        kingdom1.setPopularityFactor(PopularityFactor.FOOD, -1);
        kingdom1.setPopularityFactor(PopularityFactor.INN, 1);
        kingdom1.setPopularityFactor(PopularityFactor.TAX, 2);
        kingdom1.setPopularityFactor(PopularityFactor.HOMELESS, 3);
        kingdom1.setPopularityFactor(PopularityFactor.RELIGION, 4);
        expected = "fear: -2\nfood: -1\nInn: 1\ntax: 2\nhomeless: 3\nreligion: 4";
        Assertions.assertEquals(expected, kingdomController.showPopularityFactors());
    }

    @Test
    void showPopularity() {
        Assertions.assertEquals(kingdomController.showPopularity(), 75);
        kingdom1.setPopularity(15);
        Assertions.assertEquals(kingdomController.showPopularity(), 15);
    }

    @Test
    void setFoodRate() {
        Assertions.assertEquals(kingdom1.getFoodRate(), 0);
        kingdomController.setFoodRate(2);
        Assertions.assertEquals(kingdom1.getFoodRate(), 2);
    }

    @Test
    void showFoodList() {
        String allFood = kingdomController.showFoodList();
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][2], BuildingType.GRANARY);
        for (Item value : Item.values()) {
            if (value.getCategory() == Item.Category.FOOD)
                Assertions.assertFalse(allFood.contains(value.getName()));
        }
        kingdomController.changeStockedNumber(new Pair<>(Item.APPLE, 21));
        allFood = kingdomController.showFoodList();
        for (Item value : Item.values()) {
            if (value.getCategory() == Item.Category.FOOD && !value.getName().equals("apple"))
                Assertions.assertFalse(allFood.contains(value.getName()));
        }
    }

    @Test
    void showFoodRate() {
        Assertions.assertEquals(kingdomController.showFoodRate(), 0);
        kingdomController.setFoodRate(2);
        Assertions.assertEquals(kingdomController.showFoodRate(), 2);
    }

    @Test
    void handleTaxFactor() {
        kingdomController.handleTaxFactor(0);
        Assertions.assertEquals(kingdom1.getPopularityFactor(PopularityFactor.TAX), 1);
    }

    @Test
    void showTaxRate() {
        Assertions.assertEquals(kingdomController.showTaxRate(), kingdom1.getTaxRate());
        kingdom1.setTaxRate(2);
        Assertions.assertEquals(kingdomController.showTaxRate(), kingdom1.getTaxRate());
    }

    @Test
    void freeSpace() {
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][2], BuildingType.STOCKPILE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][3], BuildingType.STOCKPILE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][4], BuildingType.ARMOURY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][5], BuildingType.ARMOURY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][6], BuildingType.GRANARY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][7], BuildingType.GRANARY);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][8], BuildingType.GRANARY);
        Assertions.assertEquals(kingdomController.freeSpace(Item.WOOD), 400);
        Assertions.assertEquals(kingdomController.freeSpace(Item.SWORD), 100);
        Assertions.assertEquals(kingdomController.freeSpace(Item.APPLE), 750);

        kingdomController.changeStockedNumber(new Pair<>(Item.IRON, 100));
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 115));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 175));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, -140));
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, -25));

        kingdomController.changeStockedNumber(new Pair<>(Item.MEAT, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.APPLE, 225));
        kingdomController.changeStockedNumber(new Pair<>(Item.BREAD, 275));
        kingdomController.changeStockedNumber(new Pair<>(Item.BREAD, -240));
        kingdomController.changeStockedNumber(new Pair<>(Item.APPLE, -75));

        kingdomController.changeStockedNumber(new Pair<>(Item.MACE, 40));
        kingdomController.changeStockedNumber(new Pair<>(Item.SWORD, 20));
        kingdomController.changeStockedNumber(new Pair<>(Item.LEATHER_ARMOR, 30));
        kingdomController.changeStockedNumber(new Pair<>(Item.LEATHER_ARMOR, -20));
        kingdomController.changeStockedNumber(new Pair<>(Item.SWORD, -11));

        Assertions.assertEquals(kingdomController.freeSpace(Item.WOOD), 175);
        Assertions.assertEquals(kingdomController.freeSpace(Item.SWORD), 41);
        Assertions.assertEquals(kingdomController.freeSpace(Item.APPLE), 365);
    }
}