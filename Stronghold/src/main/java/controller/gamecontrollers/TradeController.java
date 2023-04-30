package controller.gamecontrollers;

import model.databases.GameDatabase;
import model.enums.Item;
import view.enums.messages.ProfileMenuMessages;
import view.enums.messages.TradeControllerMessages;

public class TradeController {
    private final GameDatabase gameDatabase;

    public TradeController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public TradeControllerMessages addTrade(String resourceType, int resourceAmount, int price, String message) {
        //TODO add trade to the game database
        return null;
    }

    public String tradeList() {
        //TODO show all trades as a string
        return null;
    }

    public TradeControllerMessages tradeAccept(int id, String message) {
        //TODO accept the trade request
        return null;
    }

    public String tradeHistory() {
        //TODO show all the accepted/declined trades
        return null;
    }

    public String[] getNotifications() {
        //TODO get notifs
        gameDatabase.getCurrentKingdom().getNotifications()
        return null;
    }
}
