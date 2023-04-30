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
        Trade trade = new Trade(gameDatabase.getCurrentKingdom(),
                Item.stringToEnum(resourceType), resourceAmount, price, message);
        for (Kingdom kingdom : gameDatabase.getKingdoms()) {
            if (kingdom == gameDatabase.getCurrentKingdom()) continue;
            kingdom.getTrades().add(trade);
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
        for (int i = 0; i < gameDatabase.getCurrentKingdom().getTrades().size(); i++) {
            if (gameDatabase.getCurrentKingdom().getTrades().get(i).getAcceptingKingdom() == null)
                id--;
            if (id == 0) {
                gameDatabase.getCurrentKingdom().getTrades().get(i).accept(gameDatabase.getCurrentKingdom(), message);
                kingdomController.changeStockedNumber(new
                        Pair<>(gameDatabase.getCurrentKingdom().getTrades().get(i).getResourceType(),
                        gameDatabase.getCurrentKingdom().getTrades().get(i).getResourceAmount()));
                gameDatabase.getCurrentKingdom().changeGold(
                        gameDatabase.getCurrentKingdom().getTrades().get(i).getPrice());
                return TradeControllerMessages.SUCCESS;
            }
        }
        return TradeControllerMessages.ID_OUT_OF_BOUNDS;
    }

    public String tradeHistory() {
        //TODO show all the accepted/declined trades
        return null;
    }

    public String[] getNotifications() {
        ArrayList<Trade> notifications = gameDatabase.getCurrentKingdom().getNotifications();
        String[] output = new String[notifications.size()];
        for (int i = 0; i < notifications.size(); i++) {
            output[i] = notifications.get(i).toString();
        }
        return output;
    }
}
