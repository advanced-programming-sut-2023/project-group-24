package controller.gamecontrollers;

import model.Kingdom;
import model.People;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.StorageBuilding;
import model.buildings.WorkersNeededBuilding;
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

    public void nextTurn() {
        //Todo ...
    }

    private void handlePopularity() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        int changePopularity = 0;
        for (PopularityFactor value : PopularityFactor.values())
            changePopularity += kingdom.getPopularityFactor(value);
        changePopularity += kingdom.getPopularity();
        if (changePopularity < 0)
            changePopularity = 0;
        else if (changePopularity > 100)
            changePopularity = 100;
        kingdom.setPopularity(changePopularity);
    }

    private void handlePopulation() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        int peopleChange = (2 * (kingdom.getPopularity() - 1) / 25 - 1);
        if (peopleChange < 0)
            removePeople(kingdom, -peopleChange);
        else
            for (int i = 0; i < peopleChange; i++)
                new People(kingdom);
    }

    private void handleProduct() {
        //TODO ...
    }

    private void handleWorkers() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        ArrayList<Building> buildings = kingdom.getBuildings();
        outer:
        while (kingdom.getUnemployment() > 0) {
            for (Building building : buildings) {
                if (building instanceof WorkersNeededBuilding) {
                    if (!((WorkersNeededBuilding) building).hasEnoughWorkers()) {
                        ((WorkersNeededBuilding) building).assignWorker(kingdom.getUnemploymentPeople());
                        continue outer;
                    }
                }
            }
            break;
        }
    }

    private void removePeople(Kingdom kingdom, int peopleWhoLeft) {
        if (kingdom.getUnemployment() > peopleWhoLeft) {
            kingdom.removeUnemploymentPeople(peopleWhoLeft);
            return;
        }
        peopleWhoLeft -= kingdom.getUnemployment();
        kingdom.removeUnemploymentPeople(kingdom.getUnemployment());
        for (int i = 0; i < peopleWhoLeft; i++)
            kingdom.removeEmploymentPeople();
    }

    public void handleTax() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        checkTaxRate(kingdom);
        int tax = (int) (kingdom.getPopulation() * getTax(kingdom.getWantedTaxRate()));
        if (kingdom.getGold() + tax >= 0)
            kingdom.changeGold(tax);
        else {
            kingdom.changeGold(kingdom.getGold());
            setTaxRate(0);
            kingdom.setWantedTaxRate(kingdom.getTaxRate());
        }
    }

    private void checkTaxRate(Kingdom kingdom) {
        if (kingdom.getWantedTaxRate() < 0 && kingdom.getTaxRate() == 0 &&
                -(kingdom.getPopulation() * getTax(kingdom.getWantedTaxRate())) <= kingdom.getGold()) {
            kingdom.setTaxRate(kingdom.getWantedTaxRate());
        }
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
                " homeless: " + kingdom.getPopularityFactor(PopularityFactor.HOMELESS) +
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

    public void handleHomeless() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        kingdom.setPopularityFactor(PopularityFactor.HOMELESS, 0);
        if (kingdom.getPopulation() > kingdom.getPopulationCapacity())
            kingdom.setPopularityFactor(PopularityFactor.HOMELESS, -4);
        else
            kingdom.setPopularityFactor(PopularityFactor.HOMELESS, 0);
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
            return "select TownHall first\n";
        if (taxRate < -3 || taxRate > 8)
            return "Invalid taxRate!\n";
        handleTaxFactor(gameDatabase.getCurrentKingdom(), taxRate);
        return "";
    }

    private double getTax(int taxRate) {
        if (taxRate > 0)
            return 0.6 + taxRate * 0.2;
        if (taxRate < 0)
            return -0.6 + taxRate * 0.2;
        else
            return 0;
    }

    private void handleTaxFactor(Kingdom currentKingdom, int taxRate) {
        int taxFactor = 0;
        switch (taxRate) {
            case -3 -> taxFactor = 7;
            case -2 -> taxFactor = 5;
            case -1 -> taxFactor = 3;
            case 0 -> taxFactor = 1;
            case 1 -> taxFactor = -2;
            case 2 -> taxFactor = -4;
            case 3 -> taxFactor = -6;
            case 4 -> taxFactor = -8;
            case 5 -> taxFactor = -12;
            case 6 -> taxFactor = -16;
            case 7 -> taxFactor = -20;
            case 8 -> taxFactor = -24;
        }
        currentKingdom.setPopularityFactor(PopularityFactor.TAX, taxFactor);

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
        if (fearFactor > 5)
            fearFactor = 5;
        if (fearFactor < -5)
            fearFactor = -5;
        gameDatabase.getCurrentKingdom().setFearRate(1 - fearFactor * 0.05);
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.RELIGION, fearFactor);
    }

    public void setInnFactor(boolean inn) {
        int innAmount = 0;
        if (inn)
            innAmount = 2;
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.INN, innAmount);
    }

}
