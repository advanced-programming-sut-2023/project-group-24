package view.menus.gamemenus;

import controller.gamecontrollers.ShopController;

import java.util.regex.Matcher;

public class ShopMenu {
    private ShopController shopController;

    public ShopMenu(ShopController shopController) {
        this.shopController = shopController;
    }

    public void run() {
        //TODO get input from GetInputFromUser class and check command
    }

    public void showPriceList() {
        //TODO connect to shopController and sout
    }

    public void buyItem(Matcher matcher) {
        //TODO connect to shopController and sout
    }

    public void sellItem(Matcher matcher) {
        //TODO connect to shopController and sout
    }
}
