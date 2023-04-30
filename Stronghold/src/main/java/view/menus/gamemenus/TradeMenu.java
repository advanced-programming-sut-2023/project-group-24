package view.menus.gamemenus;

import controller.AppController;
import controller.MainController;
import controller.gamecontrollers.TradeController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.TradeControllerMessages;
import view.menus.GetInputFromUser;

import java.util.regex.Matcher;

public class TradeMenu {
    private final TradeController tradeController;

    public TradeMenu(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public void run() {
        String command;
        Matcher matcher;
        printNotifications(tradeController.getNotifications());
        while (AppController.getCurrentMenu() == MenusName.TRADE_MENU) {
            command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.TRADE_REQUEST)) != null)
                requestTrade(matcher);
        }
    }

    private void printNotifications(String[] notifications) {
    }

    private void requestTrade(Matcher matcher) {
        String resourceType = MainController.removeDoubleQuotation(matcher.group("resourceType"));
        int resourceAmount = Integer.parseInt(MainController.removeDoubleQuotation(matcher.group(
                "resourceAmount")));
        int price = Integer.parseInt(MainController.removeDoubleQuotation(matcher.group("price")));
        String message = MainController.removeDoubleQuotation(matcher.group("message"));

        TradeControllerMessages errorMessage = tradeController.addTrade(resourceType, resourceAmount, price, message);
    }

    private void tradeList() {
        //TODO connect to tradeController and sout
    }

    private void acceptTrade(Matcher matcher) {
        //TODO connect to tradeController and sout
    }
}
