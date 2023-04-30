package view.menus.gamemenus;

import com.sun.net.httpserver.Authenticator;
import controller.AppController;
import controller.MainController;
import controller.gamecontrollers.ShopController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.ShopMenuMessages;
import view.menus.GetInputFromUser;

import javax.swing.*;
import java.util.regex.Matcher;

public class ShopMenu {
    private final ShopController shopController;

    public ShopMenu(ShopController shopController) {
        this.shopController = shopController;
    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu().equals(MenusName.SHOP_MENU)) {
            command = GetInputFromUser.getUserInput();
            if (Commands.getMatcher(command, Commands.SHOW_PRICE_LIST) != null)
                showPriceList();
            else if ((matcher = Commands.getMatcher(command, Commands.BUY_ITEM)) != null)
                buyItem(matcher);
            else if ((matcher = Commands.getMatcher(command, Commands.SELL_ITEM)) != null)
                sellItem(matcher);
            else System.out.println("Invalid command!");
        }
    }

    private void showPriceList() {
        JFrame frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setTitle("Shop");
        String[] header = new String[]{"Name", "Sell price", "Buy price", "Amount"};
        Object[][] data = shopController.showPriceList();
        JTable table = new JTable(data, header);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(950, 0, 30, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setLocation(10, 10);
        table.setBounds(0, 0, 1000, 1000);
        table.setRowHeight(40);
        frame.setResizable(false);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void buyItem(Matcher matcher) {
        String itemName = MainController.removeDoubleQuotation(matcher.group("itemName"));
        int itemAmount = Integer.parseInt(matcher.group("itemAmount"));
        ShopMenuMessages message = shopController.buyItem(itemName, itemAmount);
        switch (message) {
            case FULL_STORAGE -> System.out.println("Your storage is full!");
            case SUCCESS -> System.out.println("The item successfully purchased!");
            case INVALID_AMOUNT -> System.out.println("You entered invalid amount!");
            case INVALID_NAME -> System.out.println("You entered invalid name!");
        }
    }

    private void sellItem(Matcher matcher) {
        String itemName = MainController.removeDoubleQuotation(matcher.group("itemName"));
        int itemAmount = Integer.parseInt(matcher.group("itemAmount"));
        ShopMenuMessages message = shopController.sellItem(itemName, itemAmount);
        switch (message) {
            case SUCCESS -> System.out.println("The item successfully sold!");
            case INVALID_AMOUNT -> System.out.println("You entered invalid amount!");
            case INVALID_NAME -> System.out.println("You entered invalid name!");
            case NOT_ENOUGH_AMOUNT -> System.out.println("You don't have enough item in storage!");
        }
    }
}
