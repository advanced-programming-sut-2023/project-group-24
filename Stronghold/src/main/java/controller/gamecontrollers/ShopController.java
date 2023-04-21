package controller.gamecontrollers;

import model.GameDatabase;
import view.menus.messages.ShopMenuMessages;

public class ShopController {
    private final GameDatabase gameDatabase;

    public ShopController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public String showPriceList() {
        //TODO show the price of all the elements in the Item enum
        return null;
    }

    public ShopMenuMessages buyItem(String name, int amount) {
        //TODO check for errors and buy item
        return null;
    }

    public ShopMenuMessages sellItem(String name, int amount) {
        //TODO check for errors and buy item
        return null;
    }
}
