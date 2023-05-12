package controller.gamecontrollers;

import model.Kingdom;
import model.People;
import model.buildings.*;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.PopularityFactor;
import model.map.Cell;
import utils.Pair;

import java.util.ArrayList;

public class KingdomController {
    private final GameDatabase gameDatabase;

    public KingdomController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public void nextTurn() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        ArrayList<Building> buildings = kingdom.getBuildings();
        handleFood(kingdom);
        handleHomeless(kingdom);
        handleInn(kingdom);
        handleTax(kingdom);
        setReligionFactor(buildings);
        setFearFactor(buildings);
        handlePopularity(kingdom);
        handleProduct(buildings);
        handlePopulation(kingdom);
        handleWorkers(kingdom, buildings);
    }

    private void handleFood(Kingdom kingdom) {
        checkFoodRate(kingdom);
        int foodNeeded = kingdom.getPopulation() * (2 + kingdom.getFoodRate()) / 4;
        if (foodNeeded < kingdom.getFoodNumber()) {
            useFood(kingdom.getFoodNumber());
            kingdom.setWantedFoodRate(kingdom.getFoodRate());
            kingdom.setFoodRate(-2);
        } else useFood(foodNeeded);
        kingdom.setPopularityFactor(PopularityFactor.FOOD, kingdom.getFoodRate() * 4);
    }

    private void handlePopularity(Kingdom kingdom) {
        int changePopularity = 0;
        for (PopularityFactor value : PopularityFactor.values())
            changePopularity += kingdom.getPopularityFactor(value);
        changePopularity += kingdom.getPopularity();
        if (changePopularity < 0) changePopularity = 0;
        else if (changePopularity > 100) changePopularity = 100;
        kingdom.setPopularity(changePopularity);
    }

    private void handlePopulation(Kingdom kingdom) {
        int peopleChange = (2 * ((kingdom.getPopularity() - 1) / 25 - 1));
        if (peopleChange < 0) removePeople(kingdom, -peopleChange);
        else for (int i = 0; i < peopleChange; i++)
            new People(kingdom);
    }

    private void handleProduct(ArrayList<Building> buildings) {
        Pair<Pair<Item, Integer>, Pair<Item, Integer>> product;
        int efficiency;
        for (Building building : buildings)
            if (building.getBuildingType().equals(BuildingType.OX_TETHER)) handleOxTether(building);
            else if (building instanceof ProducerBuilding) if (((ProducerBuilding) building).hasEnoughWorkers())
                if ((efficiency = fearChance()) > 0) for (int i = 0; i < efficiency; i++)
                    if ((product = ((ProducerBuilding) building).produceItem()) != null) checkProduce(product);
    }

    private void checkProduce(Pair<Pair<Item, Integer>, Pair<Item, Integer>> product) {
        if (removeMaterialNeeded(product.getObject1())) changeStockedNumber(product.getObject2());
    }

    private boolean removeMaterialNeeded(Pair<Item, Integer> neededItem) {
        if (neededItem == null) return true;
        if (gameDatabase.getCurrentKingdom().getStockedNumber(neededItem.getObject1()) < -neededItem.getObject2())
            return false;
        changeStockedNumber(new Pair<>(neededItem.getObject1(), neededItem.getObject2()));
        return true;
    }

    private void handleOxTether(Building building) {
        Cell[][] map = gameDatabase.getMap().getMap();
        int stoneCanBeMoved = building.getBuildingType().getStorageCapacity();
        int x = building.getLocation().getX();
        int y = building.getLocation().getY();
        BuildingType quarry = BuildingType.QUARRY;
        if (isInBounds(x, y + 1) && map[x][y + 1].getExistingBuilding().getBuildingType().equals(quarry))
            if ((stoneCanBeMoved = oxTetherWorks((ProducerBuilding) building, stoneCanBeMoved)) == 0) return;
        if (isInBounds(x, y - 1) && map[x][y - 1].getExistingBuilding().getBuildingType().equals(quarry))
            if ((stoneCanBeMoved = oxTetherWorks((ProducerBuilding) building, stoneCanBeMoved)) == 0) return;
        if (isInBounds(x + 1, y) && map[x + 1][y].getExistingBuilding().getBuildingType().equals(quarry))
            if ((stoneCanBeMoved = oxTetherWorks((ProducerBuilding) building, stoneCanBeMoved)) == 0) return;
        if (isInBounds(x - 1, y) && map[x - 1][y].getExistingBuilding().getBuildingType().equals(quarry))
            oxTetherWorks((ProducerBuilding) building, stoneCanBeMoved);
    }

    private int oxTetherWorks(ProducerBuilding building, int stoneCanBeMoved) {
        int stones = building.getNumberOfItemsWaitingToBeLoaded();
        if (stones > stoneCanBeMoved) {
            changeStockedNumber(new Pair<>(Item.STONE, stoneCanBeMoved));
            building.loadItem(stoneCanBeMoved);
            return 0;
        }
        if (stones > 0) {
            changeStockedNumber(new Pair<>(Item.STONE, stones));
            building.loadItem(stones);
            return stoneCanBeMoved - stones;
        }
        return stoneCanBeMoved;
    }

    private int fearChance() {
        int efficiency = (int) (Math.random() * 100);
        if (efficiency > (gameDatabase.getCurrentKingdom().getFearRate() * 100)) return 0;
        if (efficiency < (int) (gameDatabase.getCurrentKingdom().getFearRate() * 100) - 100) return 2;
        return 1;
    }

    private void handleWorkers(Kingdom kingdom, ArrayList<Building> buildings) {
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

    private void handleTax(Kingdom kingdom) {
        checkTaxRate(kingdom);
        int tax = (int) (kingdom.getPopulation() * getTax(kingdom.getWantedTaxRate()));
        if (kingdom.getGold() + tax >= 0) kingdom.changeGold(tax);
        else {
            kingdom.changeGold(kingdom.getGold());
            handleTaxFactor(0);
            kingdom.setWantedTaxRate(kingdom.getTaxRate());
        }
    }

    private void checkTaxRate(Kingdom kingdom) {
        if (kingdom.getWantedTaxRate() < 0 && kingdom.getTaxRate() == 0 && -(kingdom.getPopulation() *
                getTax(kingdom.getWantedTaxRate())) <= kingdom.getGold()) {
            kingdom.setTaxRate(kingdom.getWantedTaxRate());
        }
    }

    private void handleInn(Kingdom kingdom) {
        if (!innExists(kingdom)) {
            setInnFactor(false);
            return;
        }
        int aleNeeded = BuildingType.INN.getUses().get(0).getObject2();
        boolean isParty = kingdom.getStockedNumber(Item.ALE) >= aleNeeded;
        if (isParty) changeStockedNumber(new Pair<>(Item.ALE, aleNeeded));
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

    public void changeStockedNumber(Pair<Item, Integer> pair) {
        gameDatabase.getCurrentKingdom().changeStockNumber(pair);
        ArrayList<Building> buildings = gameDatabase.getCurrentKingdom().getBuildings();
        BuildingType type = getBuildingType(pair.getObject1().getCategory());
        Pair<Item, Integer> newPair;
        for (Building building : buildings)
            if (building.getBuildingType().equals(type)) {
                newPair = ((StorageBuilding) building).changeItemCount(pair);
                if (0 == newPair.getObject2()) break;
            }
    }

    private BuildingType getBuildingType(Item.Category category) {
        switch (category) {
            case FOOD:
                return BuildingType.GRANARY;
            case MATERIAL:
                return BuildingType.STOCKPILE;
            default:
                return BuildingType.ARMOURY;
        }
    }

    public String showPopularityFactors() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        return "fear: " + kingdom.getPopularityFactor(PopularityFactor.FEAR) + '\n' +
                " food: " + kingdom.getPopularityFactor(PopularityFactor.FOOD) + '\n' +
                " Inn: " + kingdom.getPopularityFactor(PopularityFactor.INN) + '\n' +
                " tax: " + kingdom.getPopularityFactor(PopularityFactor.TAX) + '\n' +
                " homeless: " + kingdom.getPopularityFactor(PopularityFactor.HOMELESS) + '\n' +
                " religion: " + kingdom.getPopularityFactor(PopularityFactor.RELIGION);
    }

    public int showPopularity() {
        return gameDatabase.getCurrentKingdom().getPopularity();
    }

    void setFoodRate(int foodRate) {
        gameDatabase.getCurrentKingdom().setFoodRate(foodRate);
    }

    private void handleHomeless(Kingdom kingdom) {
        kingdom.setPopularityFactor(PopularityFactor.HOMELESS, 0);
        if (kingdom.getPopulation() > kingdom.getPopulationCapacity())
            kingdom.setPopularityFactor(PopularityFactor.HOMELESS, -4);
        else kingdom.setPopularityFactor(PopularityFactor.HOMELESS, 0);
    }


    public String showFoodList() {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        StringBuilder foodList = new StringBuilder();
        for (Item item : Item.values()) {
            if (item.getCategory().equals(Item.Category.FOOD)) if (kingdom.getStockedNumber(item) > 0)
                foodList.append(item.name()).append(kingdom.getStockedNumber(item)).append("\n");
        }
        return foodList.toString();
    }

    public int showFoodRate() {
        return gameDatabase.getCurrentKingdom().getFoodRate();
    }

    private double getTax(int taxRate) {
        if (taxRate > 0) return 0.6 + taxRate * 0.2;
        if (taxRate < 0) return -0.6 + taxRate * 0.2;
        else return 0;
    }

    public void handleTaxFactor(int taxRate) {
        int taxFactor = 0;
        switch (taxRate) {
            case -3:
                taxFactor = 7;
                break;
            case -2:
                taxFactor = 5;
                break;
            case -1:
                taxFactor = 3;
                break;
            case 0:
                taxFactor = 1;
                break;
            case 1:
                taxFactor = -2;
                break;
            case 2:
                taxFactor = -4;
                break;
            case 3:
                taxFactor = -6;
                break;
            case 4:
                taxFactor = -8;
                break;
            case 5:
                taxFactor = -12;
                break;
            case 6:
                taxFactor = -16;
                break;
            case 7:
                taxFactor = -20;
                break;
            case 8:
                taxFactor = -24;
                break;
        }
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.TAX, taxFactor);
    }

    public int showTaxRate() {
        return gameDatabase.getCurrentKingdom().getTaxRate();
    }

    private void setReligionFactor(ArrayList<Building> buildings) {
        int religionFactor = 0;
        for (Building building : buildings)
            if (building.getBuildingType().equals(BuildingType.CHURCH) ||
                    building.getBuildingType().equals(BuildingType.CATHEDRAL))
                religionFactor += 2;
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.RELIGION, religionFactor);
    }

    private void setFearFactor(ArrayList<Building> buildings) {
        int fearFactor = 0;
        for (Building building : buildings) {
            if (building.getBuildingType().equals(BuildingType.GOOD_THING)) fearFactor += 1;
            if (building.getBuildingType().equals(BuildingType.BAD_THING)) fearFactor -= 1;
        }
        fearFactor /= 2;
        if (fearFactor > 5) fearFactor = 5;
        if (fearFactor < -5) fearFactor = -5;
        gameDatabase.getCurrentKingdom().setFearRate(1 - fearFactor * 0.05);
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.FEAR, fearFactor);
    }

    private void setInnFactor(boolean inn) {
        int innAmount = 0;
        if (inn) innAmount = 2;
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.INN, innAmount);
    }

    public int freeSpace(Item item) {
        int freeSpace = 0;
        for (Building building : gameDatabase.getCurrentKingdom().getBuildings()) {
            if (building.getBuildingType().getItemsItCanHold() == item.getCategory())
                freeSpace += building.getBuildingType().getStorageCapacity()
                        - ((StorageBuilding) building).getNumberOfItemsInStorage();
        }
        return freeSpace;
    }

    private boolean innExists(Kingdom kingdom) {
        for (Building building : kingdom.getBuildings()) {
            if (building.getBuildingType() == BuildingType.INN) return true;
        }
        return false;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < gameDatabase.getMap().getSize() && y < gameDatabase.getMap().getSize();
    }
}