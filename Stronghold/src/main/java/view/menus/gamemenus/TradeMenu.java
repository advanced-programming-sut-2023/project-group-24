package view.menus.gamemenus;

import controller.AppController;
import controller.MainController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.TradeController;
import model.enums.Color;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.TradeControllerMessages;
import view.menus.GetInputFromUser;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class TradeMenu {
    private final TradeController tradeController;
    private final KingdomController kingdomController;

    public TradeMenu(TradeController tradeController, KingdomController kingdomController) {
        this.tradeController = tradeController;
        this.kingdomController = kingdomController;
    }

    public void run() {
        String command;
        Matcher matcher;
        printNotifications(tradeController.getNotifications());
        while (AppController.getCurrentMenu() == MenusName.TRADE_MENU) {
            command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.TRADE_REQUEST)) != null)
                requestTrade(matcher);
            else if (Commands.getMatcher(command, Commands.TRADE_LIST) != null)
                tradeList();
            else if ((matcher = Commands.getMatcher(command, Commands.TRADE_ACCEPT)) != null)
                acceptTrade(matcher);
        }
    }

    private void printNotifications(String[] notifications) {
        for (String notification : notifications) {
            System.out.println(notification);
        }
    }

    private void requestTrade(Matcher matcher) {
        String resourceType = MainController.removeDoubleQuotation(matcher.group("resourceType"));
        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount"));
        int price = Integer.parseInt(matcher.group("price"));
        String message = MainController.removeDoubleQuotation(matcher.group("message"));

        TradeControllerMessages errorMessage = tradeController.addTrade(resourceType, resourceAmount, price, message);
        switch (errorMessage) {
            case INVALID_RESOURCE_NAME:
                System.out.println(Color.RED + "this item doesn't exist" + Color.RESET);
                break;
            case SUCCESS:
                System.out.println(Color.GREEN + "successfully added your trade request" + Color.RESET);
                break;
        }
    }

    private void tradeList() {
        ArrayList<String> trades = tradeController.tradeList();
        for (String trade : trades) {
            System.out.println(trade);
        }
    }

    private void acceptTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String acceptingMessage = MainController.removeDoubleQuotation(matcher.group("message"));
        TradeControllerMessages message = tradeController.tradeAccept(id, acceptingMessage, kingdomController);
    }
}
