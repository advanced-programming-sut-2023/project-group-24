package controller.gamecontrollers;

import model.Kingdom;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.PopularityFactor;

import java.util.HashMap;

public class KingdomController {
    private final GameDatabase gameDatabase;

    public KingdomController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public String showPopularityFactors() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        return "fear: " + kingdom.getPopularityFactor(PopularityFactor.FEAR) +
                " food: " + kingdom.getPopularityFactor(PopularityFactor.FOOD) +
                " tax: " + kingdom.getPopularityFactor(PopularityFactor.TAX) +
                " religion: " + kingdom.getPopularityFactor(PopularityFactor.RELIGION);
    }

    public int showPopularity() {
        return gameDatabase.getCurrentKingdom().getPopularity();
    }

    public String setFoodRate(int foodRate) {
        if (foodRate < -2 || foodRate > 2)
            return "Invalid foodRate!\n";
        gameDatabase.getCurrentKingdom().setFoodRate(foodRate);
        return "";
    }

    public String showFoodList() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        StringBuilder foodList= new StringBuilder();
        for (Item item : Item.values()) {
            if (item.getCategory().equals(Item.Category.FOOD))
                if (kingdom.getStockedNumber(item) > 0)
                    foodList.append(item.name()).append(kingdom.getStockedNumber(item)).append("\n");
        }
        return foodList.toString();
    }

    public int showFoodRate() {
        return gameDatabase.getCurrentKingdom().getFoodRate();
    }

    public String setTaxRate(int taxRate) {
        if (taxRate < -3 || taxRate > 8)
            return "Invalid taxRate!\n";
        gameDatabase.getCurrentKingdom().setTaxRate(taxRate);
        return "";
    }

    public int showTaxRate() {
        return gameDatabase.getCurrentKingdom().getTaxRate();
    }

    public void setReligionFactor() {
        //TODO
    }

    public void setFearFactor() {
        //TODO
    }

}
