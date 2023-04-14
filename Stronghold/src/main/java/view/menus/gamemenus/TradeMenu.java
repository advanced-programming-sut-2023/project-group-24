package view.menus.gamemenus;

import controller.gamecontrollers.TradeController;

import java.util.regex.Matcher;

public class TradeMenu {
    private TradeController tradeController;

    public TradeMenu(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public void run() {
        //TODO get input from GetInputFromUser class and check command
    }

    public void requestTrade(Matcher matcher) {
        //TODO connect tradeController check errors and sout
    }

    public void tradeList() {
        //TODO connect to tradeController and sout
    }

    public void acceptTrade(Matcher matcher) {
        //TODO connect to tradeController and sout
    }
}
