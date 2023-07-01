package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.databases.GameDatabase;
import model.enums.Item;
import view.enums.messages.ShopMenuMessages;

public class ShopController {
    private final Kingdom currentKingdom;

    public ShopController(GameDatabase gameDatabase) {
        currentKingdom = gameDatabase.getCurrentKingdom();
    }

    public Object[][] showPriceList() {
        Item[] items = Item.values();
        Object[][] list = new Object[items.length][4];
        for (int i = 0; i < items.length; i++)
            list[i] = new Object[]{items[i].getName(), (int) Math.ceil((double) items[i].getPrice() / 2),
                    items[i].getPrice(), currentKingdom.getStockedNumber(items[i])};
        return list;
    }

    public ShopMenuMessages buyItem(Item item, KingdomController kingdomController) {
        int amount = 5;
        int price = amount * item.getPrice();
        if (kingdomController.freeSpace(item) < amount) return ShopMenuMessages.FULL_STORAGE;
        else if (price > currentKingdom.getGold()) return ShopMenuMessages.NOT_ENOUGH_GOLD;
        Pair<Item, Integer> itemAndCount = new Pair<>(item, amount);
        kingdomController.changeStockedNumber(itemAndCount);
        currentKingdom.changeGold(-price);
        return ShopMenuMessages.SUCCESS;
    }

    public ShopMenuMessages sellItem(Item item, KingdomController kingdomController) {
        int amount = 5;
        int price = amount * (int) Math.ceil((double) item.getPrice() / 2);
        if (currentKingdom.getStockedNumber(item) < amount) return ShopMenuMessages.NOT_ENOUGH_AMOUNT;
        Pair<Item, Integer> itemAndCount = new Pair<>(item, -amount);
        kingdomController.changeStockedNumber(itemAndCount);
        currentKingdom.changeGold(price);
        return ShopMenuMessages.SUCCESS;
    }
}
