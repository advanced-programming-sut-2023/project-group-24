package controller.gamecontrollers;

import model.Kingdom;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import controller.functionalcontrollers.Pair;
import view.enums.messages.ShopMenuMessages;

import java.util.ArrayList;
import java.util.List;

class ShopControllerTest {
    private final Map map = new Map(10, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private final ShopController shopController = new ShopController(gameDatabase);
    private final KingdomController kingdomController = new KingdomController(gameDatabase);

    @Test
    void showPriceList() {
        Object[][] list = shopController.showPriceList();
        Assertions.assertEquals(list.length, Item.values().length);
        Assertions.assertEquals(list[0].length, 4);
    }

    @Test
    void buyItem() {
        Assertions.assertEquals(shopController.buyItem("chert", 1, kingdomController), ShopMenuMessages.INVALID_NAME);
        Assertions.assertEquals(shopController.buyItem("stone", 0, kingdomController), ShopMenuMessages.INVALID_AMOUNT);
        Assertions.assertEquals(shopController.buyItem("stone", 1, kingdomController), ShopMenuMessages.FULL_STORAGE);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][2], BuildingType.STOCKPILE);
        Assertions.assertEquals(shopController.buyItem("iron", 200, kingdomController), ShopMenuMessages.NOT_ENOUGH_GOLD);
        Assertions.assertEquals(shopController.buyItem("iron", 1, kingdomController), ShopMenuMessages.SUCCESS);
        Assertions.assertEquals(shopController.buyItem("wood", 200, kingdomController), ShopMenuMessages.FULL_STORAGE);
        Assertions.assertEquals(kingdom1.getGold(), 955);
    }

    @Test
    void sellItem() {
        Assertions.assertEquals(shopController.sellItem("chert", 1, kingdomController), ShopMenuMessages.INVALID_NAME);
        Assertions.assertEquals(shopController.sellItem("wood", 0, kingdomController), ShopMenuMessages.INVALID_AMOUNT);
        Assertions.assertEquals(shopController.sellItem("wood", 1, kingdomController), ShopMenuMessages.NOT_ENOUGH_AMOUNT);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        Assertions.assertEquals(shopController.sellItem("wood", 1, kingdomController), ShopMenuMessages.SUCCESS);
        Assertions.assertEquals(kingdom1.getGold(), 1002);
        Assertions.assertEquals(shopController.sellItem("wood", 200, kingdomController), ShopMenuMessages.NOT_ENOUGH_AMOUNT);
        Assertions.assertEquals(shopController.sellItem("wood", 199, kingdomController), ShopMenuMessages.SUCCESS);
        Assertions.assertEquals(kingdom1.getGold(), 1400);
    }
}