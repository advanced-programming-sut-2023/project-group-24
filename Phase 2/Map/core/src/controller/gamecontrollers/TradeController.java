package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.Trade;
import model.databases.GameDatabase;
import model.enums.Item;
import view.enums.messages.TradeControllerMessages;

import java.util.ArrayList;

public class TradeController {
    private final GameDatabase gameDatabase;

    public TradeController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

//    public TradeControllerMessages addTrade(Item resourceType, int resourceAmount, Kingdom kingdom) {
//        kingdom.getTrades().add(trade);
//        if (kingdom == gameDatabase.getCurrentKingdom()) continue;
//        kingdom.getNotifications().add(trade);
//        return TradeControllerMessages.SUCCESS;
//    }

    public ArrayList<String> tradeList() {
        ArrayList<String> output = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < gameDatabase.getCurrentKingdom().getTrades().size(); i++) {
            if (gameDatabase.getCurrentKingdom().getTrades().get(i).getAcceptingKingdom() == null)
                output.add((id++) + ")" + gameDatabase.getCurrentKingdom().getTrades().get(i).toString());
        }
        return output;
    }

    public TradeControllerMessages tradeAccept(Trade trade, KingdomController kingdomController) {

        if (trade.getRequester() == gameDatabase.getCurrentKingdom()) return TradeControllerMessages.TRADE_IS_YOURS;
        if (gameDatabase.getCurrentKingdom().getStockedNumber(trade.getResourceType())
                < trade.getResourceAmount()) return TradeControllerMessages.NOT_ENOUGH_RESOURCES;
        gameDatabase.getCurrentKingdom().getNotifications().remove(trade);
        trade.getRequester().getNotifications().remove(trade);
        return TradeControllerMessages.SUCCESS;
    }

    public ArrayList<String> tradeHistory() {
        ArrayList<String> output = new ArrayList<>();
        for (Trade trade : gameDatabase.getCurrentKingdom().getTrades()) {
            if (trade.getRequester() == gameDatabase.getCurrentKingdom()
                    || trade.getAcceptingKingdom() == gameDatabase.getCurrentKingdom())
                output.add(trade.toString());
        }
        return output;
    }

    public String[] getNotifications() {
        ArrayList<Trade> notifications = gameDatabase.getCurrentKingdom().getNotifications();
        String[] output = new String[notifications.size()];
        for (int i = 0; i < notifications.size(); i++) {
            output[i] = notifications.get(i).toString();
        }
        gameDatabase.getCurrentKingdom().resetNotifications();
        return output;
    }

    private Trade getTradeFromId(int id) {
        for (int i = 0; i < gameDatabase.getCurrentKingdom().getTrades().size(); i++) {
            Trade trade = gameDatabase.getCurrentKingdom().getTrades().get(i);
            if (trade.getAcceptingKingdom() == null) id--;
            if (id == 0) return trade;
        }
        return null;
    }
}
