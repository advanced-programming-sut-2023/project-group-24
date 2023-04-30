package controller.gamecontrollers;

import model.Kingdom;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.StorageBuilding;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.PopularityFactor;
import utils.Pair;

import java.util.ArrayList;

public class KingdomController {
    private final GameDatabase gameDatabase;

    public KingdomController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public void handleKingdomPopularity() {
        //Todo use food;
    }

    public void beginningTurn() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();

    }

    public void ChangeStockedNumber(Pair<Item, Integer> pair) {
        gameDatabase.getCurrentKingdom().changeStockNumber(pair);
        ArrayList<Building> buildings = gameDatabase.getCurrentKingdom().getBuildings();
        BuildingType type = getBuildingType(pair.getObject1().getCategory());
        Pair<Item, Integer> newPair = null;
        for (Building building : buildings)
            if (building.getBuildingType().equals(type)) {
                newPair = ((StorageBuilding) building).changeItemCount(pair);
                if (0 == newPair.getObject2())
                    break;
            }
        gameDatabase.getCurrentKingdom().changeStockNumber(new Pair<>(pair.getObject1(), -newPair.getObject2()));
    }

    private BuildingType getBuildingType(Item.Category category) {
        switch (category) {
            case FOOD -> {
                return BuildingType.GRANARY;
            }
            case MATERIAL -> {
                return BuildingType.STOCKPILE;
            }
            default -> {
                return BuildingType.ARMOURY;
            }
        }
    }

    public String showPopularityFactors() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        return "fear: " + kingdom.getPopularityFactor(PopularityFactor.FEAR) +
                " food: " + kingdom.getPopularityFactor(PopularityFactor.FOOD) +
                " Inn: " + kingdom.getPopularityFactor(PopularityFactor.INN) +
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

    public void setFoodFactor() {

    }

    public String showFoodList() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        StringBuilder foodList = new StringBuilder();
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

    public void setInnFactor() {
        //TODO
    }

}
