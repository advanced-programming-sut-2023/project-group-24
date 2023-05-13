package controller.gamecontrollers;

import model.Kingdom;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import controller.functionalcontrollers.Pair;
import view.enums.messages.TradeControllerMessages;

import java.util.ArrayList;
import java.util.List;

class TradeControllerTest {
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
    private TradeController tradeController = new TradeController(gameDatabase);
    private KingdomController kingdomController = new KingdomController(gameDatabase);

    @Test
    void addTrade() {
        Assertions.assertEquals(tradeController.addTrade("stone", 2, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 0, 0, "i need stone"), TradeControllerMessages.INVALID_AMOUNT);
        Assertions.assertEquals(tradeController.addTrade("chert", 1, 0, "i need stone"), TradeControllerMessages.INVALID_RESOURCE_NAME);
        Assertions.assertEquals(tradeController.addTrade("leather armor", 1, 0, "i need stone"), TradeControllerMessages.SUCCESS);
    }

    @Test
    void tradeList() {
        Assertions.assertEquals(tradeController.addTrade("stone", 2, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 1, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.tradeList().size(), 2);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeList().size(), 2);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeList().size(), 2);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeList().size(), 2);
    }

    @Test
    void tradeAccept() {
        Assertions.assertEquals(tradeController.addTrade("stone", 1, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 2, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 3, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 4, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.tradeAccept(1, "never mind", kingdomController), TradeControllerMessages.TRADE_IS_YOURS);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeAccept(5, "hi", kingdomController), TradeControllerMessages.ID_OUT_OF_BOUNDS);
        Assertions.assertEquals(tradeController.tradeAccept(1, "hi", kingdomController), TradeControllerMessages.NOT_ENOUGH_RESOURCES);
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 5));
        Assertions.assertEquals(tradeController.tradeAccept(1, "hi", kingdomController), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.tradeList().size(), 3);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeList().size(), 3);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeList().size(), 3);
    }

    @Test
    void tradeHistory() {
        Assertions.assertEquals(tradeController.addTrade("stone", 1, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 2, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 3, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 4, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        gameDatabase.nextTurn();
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 5));
        Assertions.assertEquals(tradeController.tradeHistory().size(), 0);
        Assertions.assertEquals(tradeController.tradeAccept(1, "hi", kingdomController), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.tradeHistory().size(), 1);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeHistory().size(), 0);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.tradeHistory().size(), 4);
    }

    @Test
    void getNotifications() {
        Assertions.assertEquals(tradeController.addTrade("stone", 1, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 2, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 3, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.addTrade("stone", 4, 0, "i need stone"), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.getNotifications().length, 0);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.getNotifications().length, 4);
        Assertions.assertEquals(tradeController.getNotifications().length, 0);
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 5));
        Assertions.assertEquals(tradeController.tradeAccept(1, "hi", kingdomController), TradeControllerMessages.SUCCESS);
        Assertions.assertEquals(tradeController.getNotifications().length, 0);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.getNotifications().length, 3);
        Assertions.assertEquals(tradeController.getNotifications().length, 0);
        gameDatabase.nextTurn();
        Assertions.assertEquals(tradeController.getNotifications().length, 1);
        Assertions.assertEquals(tradeController.getNotifications().length, 0);
    }
}