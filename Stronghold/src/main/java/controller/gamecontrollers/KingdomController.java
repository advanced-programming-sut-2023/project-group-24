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

    public void handleFood() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        checkFoodRate(kingdom);
        int foodNeeded = kingdom.getPopulation() * (2 + kingdom.getFoodRate()) / 4;
        if (foodNeeded < kingdom.getFoodNumber()) {
            useFood(kingdom.getFoodNumber());
            kingdom.setWantedFoodRate(kingdom.getFoodRate());
            kingdom.setFoodRate(-2);
        } else
            useFood(foodNeeded);
        kingdom.setPopularityFactor(PopularityFactor.FOOD, kingdom.getFoodRate() * 4);
    }

    public void handleInn() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        int aleNeeded = BuildingType.INN.getUses().get(0).getObject2();
        boolean isParty = kingdom.getStockedNumber(Item.ALE) >= aleNeeded;
        if (isParty)
            changeStockedNumber(new Pair<>(Item.ALE, aleNeeded));
        setInnFactor(isParty);
    }

    private void checkFoodRate(Kingdom kingdom) {
        if (-2 == kingdom.getFoodRate() && kingdom.getWantedFoodRate() != -2 && kingdom.getFoodNumber() >=
                kingdom.getPopulation() * (2 + kingdom.getWantedFoodRate()) / 4)
            kingdom.setFoodRate(kingdom.getWantedFoodRate());
    }

    private void useFood(int foodNumber) {
        ArrayList<Building> buildings = gameDatabase.getCurrentKingdom().getBuildings();
        for (Building building : buildings) {
            if (building.getBuildingType().equals(BuildingType.GRANARY)) {
                foodNumber = ((StorageBuilding) building).useFood(foodNumber);
                if (0 == foodNumber) break;
            }
        }
    }

    public void beginningTurn() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
    }

    public int getFreeSpace(Item item) {
        int freeSpace = 0;
        for (Building building : gameDatabase.getCurrentKingdom().getBuildings()) {
            if (building.getBuildingType().getItemsItCanHold().equals(item.getCategory()))
                freeSpace += (building.getBuildingType().getStorageCapacity() - ((StorageBuilding) building).
                        getNumberOfItemsInStorage());
        }
        return freeSpace;
    }

    public void changeStockedNumber(Pair<Item, Integer> pair) {
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
        if (!gameDatabase.getCurrentBuilding().getBuildingType().equals(BuildingType.TOWN_HALL))
            return "";
        if (taxRate < -3 || taxRate > 8)
            return "Invalid taxRate!\n";
        gameDatabase.getCurrentKingdom().setTaxRate(taxRate);
        return "";
    }

    public int showTaxRate() {
        return gameDatabase.getCurrentKingdom().getTaxRate();
    }

    public void setReligionFactor(ArrayList<Building> buildings) {
        int religionFactor = 0;
        for (Building building : buildings)
            if (building.getBuildingType().equals(BuildingType.CHURCH) || building.getBuildingType().equals(BuildingType.CATHEDRAL))
                religionFactor += 2;
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.RELIGION, religionFactor);
    }

    //right 7 left 8 up down 4
    public void setFearFactor(ArrayList<Building> buildings) {
        int fearFactor = 0;
        for (Building building : buildings) {
            if (building.getBuildingType().equals(BuildingType.GOOD_THING))
                fearFactor += 1;
            if (building.getBuildingType().equals(BuildingType.BAD_THING))
                fearFactor -= 1;
        }
        fearFactor /= 2;
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.RELIGION, fearFactor / 2);
    }

    public void setInnFactor(boolean inn) {
        int innAmount = 0;
        if (inn)
            innAmount = 2;
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.INN, innAmount);
    }

}
