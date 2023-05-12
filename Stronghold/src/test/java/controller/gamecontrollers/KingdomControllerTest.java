package controller.gamecontrollers;

import model.Kingdom;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.StorageBuilding;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingdomControllerTest {
    private Map map = new Map(10, "test");
    private Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }
    private GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private KingdomController kingdomController = new KingdomController(gameDatabase);

    @Test
    void nextTurn() {
        //TODO this cannot be avoided for much longer
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
        String expected = "fear: 0\nfood: 0\nInn: 0\ntax: 0\nhomeless: 0\nreligion: 0";
        Assertions.assertEquals(expected, kingdomController.showPopularityFactors());
    }

    @Test
    void showPopularity() {

    }

    @Test
    void setFoodRate() {
    }

    @Test
    void showFoodList() {
    }

    @Test
    void showFoodRate() {
    }

    @Test
    void handleTaxFactor() {
    }

    @Test
    void showTaxRate() {
    }

    @Test
    void freeSpace() {
    }
}