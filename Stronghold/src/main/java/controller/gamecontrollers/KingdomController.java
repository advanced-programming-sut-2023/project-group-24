package controller.gamecontrollers;

import model.Kingdom;
import model.databases.GameDatabase;
import model.enums.PopularityFactor;

import java.util.HashMap;

public class KingdomController {
    private final GameDatabase gameDatabase;

    public KingdomController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public String showPopularityFactors() {
        HashMap<PopularityFactor, Integer> popularityFactors = gameDatabase.getCurrentKingdom().getPopularityFactors();
        return "fear: " + popularityFactors.get(PopularityFactor.FEAR) + " food: " +
                popularityFactors.get(PopularityFactor.FOOD) +" tax: " + popularityFactors.get(PopularityFactor.TAX) +
                " religion: " + popularityFactors.get(PopularityFactor.RELIGION);
    }

    public int showPopularity() {
        return gameDatabase.getCurrentKingdom().getPopularity();
    }

    public void setFoodRate(int foodRate) {
        //TODO
    }

    public int showFoodRate() {
        return gameDatabase.getCurrentKingdom().getPopularityFactors().get(PopularityFactor.FOOD);
    }

    public void setTaxRate(int taxRate) {
        //TODO set that
    }

    public int showTaxRate() {
        return gameDatabase.getCurrentKingdom().getPopularityFactors().get(PopularityFactor.TAX);
    }

    public int showReligionRate() {
        return gameDatabase.getCurrentKingdom().getPopularityFactors().get(PopularityFactor.RELIGION);
    }

    public void setFearRate(int fearRate) {
        //TODO set that
    }

    public int showFearRate() {
        return gameDatabase.getCurrentKingdom().getPopularityFactors().get(PopularityFactor.FEAR);
    }
}
