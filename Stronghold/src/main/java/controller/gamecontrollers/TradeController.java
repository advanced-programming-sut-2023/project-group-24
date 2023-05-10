package controller.gamecontrollers;

import model.Kingdom;
import model.Trade;
import model.databases.GameDatabase;
import model.enums.Item;
import utils.Pair;
import view.enums.messages.TradeControllerMessages;

import java.util.ArrayList;

public class TradeController {
    private final GameDatabase gameDatabase;

    public TradeController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public TradeControllerMessages addTrade(String resourceType, int resourceAmount, int price, String message) {
        if (Item.stringToEnum(resourceType) == null)
            return TradeControllerMessages.INVALID_RESOURCE_NAME;
        if (resourceAmount < 1)
            return TradeControllerMessages.INVALID_AMOUNT;
        Trade trade = new Trade(gameDatabase.getCurrentKingdom(),
                Item.stringToEnum(resourceType), resourceAmount, price, message);
        for (Kingdom kingdom : gameDatabase.getKingdoms()) {
            kingdom.getTrades().add(trade);
            if (kingdom == gameDatabase.getCurrentKingdom()) continue;
            kingdom.getNotifications().add(trade);
        }
        return TradeControllerMessages.SUCCESS;
    }

    public ArrayList<String> tradeList() {
        ArrayList<String> output = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < gameDatabase.getCurrentKingdom().getTrades().size(); i++) {
            if (gameDatabase.getCurrentKingdom().getTrades().get(i).getAcceptingKingdom() == null)
                output.add((id++) + ")" + gameDatabase.getCurrentKingdom().getTrades().get(i).toString());
        }
        return output;
    }

    public TradeControllerMessages tradeAccept(int id, String message, KingdomController kingdomController) {
        Trade trade = getTradeFromId(id);
        if (trade == null) return TradeControllerMessages.ID_OUT_OF_BOUNDS;
        if (trade.getRequester() == gameDatabase.getCurrentKingdom()) return TradeControllerMessages.TRADE_IS_YOURS;
        if (gameDatabase.getCurrentKingdom().getStockedNumber(trade.getResourceType())
                < trade.getResourceAmount()) return TradeControllerMessages.NOT_ENOUGH_RESOURCES;

        trade.accept(gameDatabase.getCurrentKingdom(), message);
        kingdomController.changeStockedNumber(new Pair<>(trade.getResourceType(), -trade.getResourceAmount()));
        gameDatabase.getCurrentKingdom().changeGold(trade.getPrice());

        for (Kingdom kingdom : gameDatabase.getKingdoms()) {
            kingdom.getNotifications().remove(trade);
            if (kingdom != trade.getRequester() && kingdom != trade.getAcceptingKingdom())
                kingdom.getTrades().remove(trade);
        }

        trade.getRequester().getNotifications().add(trade);
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
