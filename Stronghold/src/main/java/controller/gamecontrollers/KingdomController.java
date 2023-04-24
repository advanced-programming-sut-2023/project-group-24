package controller.gamecontrollers;

import model.GameDatabase;

public class KingdomController {
    private final GameDatabase gameDatabase;

    public KingdomController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public String showPopularityFactors() {
        //TODO return popularity factors
        return null;
    }

    public int showPopularity() {
        //TODO return popularity
        return 0;
    }

    public void setFoodRate(int foodRate) {
        //TODO
    }

    public int showFoodRate() {
        return 0;
    }

    public void setTaxRate(int taxRate) {
        //TODO set that
    }

    public int showTaxRate() {
        return 0;
    }

    public void setFearRate(int fearRate) {
        //TODO set that
    }
}
