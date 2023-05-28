package model;

import model.enums.Item;
import model.enums.KingdomColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TradeTest {
    Kingdom kingdom1 = new Kingdom(KingdomColor.GREEN);
    Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Trade trade = new Trade(kingdom1, Item.WOOD, 23, 10, "please help mee");


    @Test
    void getResourceType() {
        Assertions.assertEquals(trade.getResourceType(), Item.WOOD);
    }

    @Test
    void getResourceAmount() {
        Assertions.assertEquals(trade.getResourceAmount(), 23);
    }

    @Test
    void getRequesterMessage() {
        Assertions.assertEquals(trade.getRequesterMessage(), "please help mee");
    }

    @Test
    void getPrice() {
        Assertions.assertEquals(trade.getPrice(), 10);
    }

    @Test
    void getRequester() {
        Assertions.assertEquals(trade.getRequester(), kingdom1);
    }

    @Test
    void getAcceptingMessage() {
        Assertions.assertNull(trade.getAcceptingMessage());
        trade.accept(kingdom2, "i help everyone");
        Assertions.assertEquals(trade.getAcceptingMessage(), "i help everyone");
    }

    @Test
    void getAcceptingKingdom() {
        Assertions.assertNull(trade.getAcceptingKingdom());
        trade.accept(kingdom2, "i help everyone");
        Assertions.assertEquals(trade.getAcceptingKingdom(), kingdom2);
    }

    @Test
    void acquireItems() {
        Assertions.assertFalse(trade.canBeGotten());
        trade.accept(kingdom2, "i help everyone");
        Assertions.assertTrue(trade.canBeGotten());
        trade.acquireItems();
        Assertions.assertFalse(trade.canBeGotten());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("O  green -            wood -  23 - price:   10 - \"please help mee\"", trade.toString());
        trade.accept(kingdom2, "i help everyone");
        Assertions.assertEquals("X  green -            wood -  23 - price:   10 - \"please help mee\"" +
                " - accepted by   blue: \"i help everyone\"", trade.toString());
    }
}