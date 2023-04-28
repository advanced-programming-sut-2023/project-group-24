package view.menus.gamemenus;

import controller.gamecontrollers.TradeController;

import java.util.regex.Matcher;

public class TradeMenu {
    private final TradeController tradeController;

    public TradeMenu(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public void run() {
        //TODO get input from GetInputFromUser class and check command
    }

    private void requestTrade(Matcher matcher) {
        //TODO connect tradeController check errors and sout
    }

    private void tradeList() {
        //TODO connect to tradeController and sout
    }

    private void acceptTrade(Matcher matcher) {
        //TODO connect to tradeController and sout
    }
}
