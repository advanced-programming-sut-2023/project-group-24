package controller.gamecontrollers;

import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.army.Soldier;
import model.army.SoldierType;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DairyProduce;
import model.buildings.GateAndStairs;
import model.databases.GameDatabase;
import model.enums.Item;
import model.map.Cell;
import utils.Pair;
import view.enums.messages.BuildingControllerMessages;

public class BuildingController {
    private final GameDatabase gameDatabase;

    public BuildingController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public BuildingControllerMessages dropBuilding(int x, int y, String type, KingdomController kingdomController) {
        if (checkLocationOutOfBounds(x, y))
            return BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS;
        Cell cell = gameDatabase.getMap().getMap()[x][y];
        BuildingType buildingType = BuildingType.getBuildingTypeFromName(type);
        if (buildingType == null)
            return BuildingControllerMessages.INVALID_TYPE;
        if (!cell.canBuild())
            return BuildingControllerMessages.CANNOT_BUILD_HERE;
        if (!canDropBuilding(cell, buildingType))
            return BuildingControllerMessages.CANNOT_BUILD_HERE;
        if (!hasEnoughMaterialToBuild(buildingType.getMaterialToBuild()))
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        if (gameDatabase.getCurrentKingdom().getGold() < buildingType.getPrice())
            return BuildingControllerMessages.NOT_ENOUGH_GOLD;
        Building.getBuildingFromBuildingType(gameDatabase.getCurrentKingdom(), cell, buildingType);
        gameDatabase.getCurrentKingdom().changeGold(-buildingType.getPrice());
        kingdomController.changeStockedNumber(buildingType.getMaterialToBuild());
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages selectBuilding(int x, int y) {
        if (checkLocationOutOfBounds(x, y))
            return BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS;
        Cell cell = gameDatabase.getMap().getMap()[x][y];
        if (cell.getExistingBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS;
        if (cell.getExistingBuilding().getKingdom() != gameDatabase.getCurrentKingdom())
            return BuildingControllerMessages.NOT_OWNER;
        gameDatabase.setCurrentBuilding(cell.getExistingBuilding()); //TODO remember to deselect building on next turn
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages createUnit(String name, int count, KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding().getBuildingType().getCategory() != BuildingType.Category.ARMY_MAKER)
            return BuildingControllerMessages.INCORRECT_BUILDING;
        if (count <= 0)
            return BuildingControllerMessages.INCORRECT_COUNT;
        ArmyType armyType = ArmyType.stringToEnum(name);
        SoldierType soldierType = SoldierType.stringToEnum(name);
        if (soldierType == null || armyType == null)
            return BuildingControllerMessages.INVALID_TYPE;
        Kingdom currentKingdom = gameDatabase.getCurrentKingdom();
        if (doesNotHaveEnoughResources(count, armyType, soldierType, currentKingdom))
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        //TODO check if there are enough unemployed people and employ them
        if (gameDatabase.getCurrentBuilding().getBuildingType().getTroopsItCanMake() != soldierType.getNation())
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        for (int i = 0; i < count; i++)
            new Soldier(gameDatabase.getCurrentBuilding().getLocation(), armyType, currentKingdom, soldierType);
        useResources(count, armyType, soldierType, currentKingdom, kingdomController);
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages repair(KingdomController kingdomController) {
        Building building = gameDatabase.getCurrentBuilding();
        if (!building.getBuildingType().canBeRepaired())
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        int stonesNeeded = building.getHp() * (-building.getBuildingType().getMaterialToBuild().getObject2())
                / building.getBuildingType().getMaxHp();
        if (gameDatabase.getCurrentKingdom().getStockedNumber(Item.STONE) < stonesNeeded)
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        if (isThereAnEnemyNearby(building.getLocation().getX(), building.getLocation().getY()))
            return BuildingControllerMessages.ENEMY_IS_NEARBY;
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, -stonesNeeded));
        gameDatabase.getCurrentBuilding().repair();
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages changeGateClosedState() {
        Building building = gameDatabase.getCurrentBuilding();
        if (building.getBuildingType() != BuildingType.LARGE_STONE_GATEHOUSE
                && building.getBuildingType() != BuildingType.SMALL_STONE_GATEHOUSE)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        ((GateAndStairs) building).changeClosedState();
        changeDrawBridgeClosedState(building.getLocation().getX(), building.getLocation().getY());
        return BuildingControllerMessages.SUCCESS;
    }

    public  BuildingControllerMessages openDogCage() {
        Building building = gameDatabase.getCurrentBuilding();
        if (gameDatabase.getCurrentBuilding().getBuildingType() != BuildingType.CAGED_WAR_DOGS)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        for (int i = 0; i < 3; i++)
            new Soldier(building.getLocation(), ArmyType.DOG, gameDatabase.getCurrentKingdom(), SoldierType.DOG);
        building.takeDamage(building.getHp());
        gameDatabase.getCurrentKingdom().removeBuilding(building);
        return BuildingControllerMessages.SUCCESS;
    }

    public String[] showDetails() {
        //TODO list all the information
        return null;
    }

    public BuildingControllerMessages produceLeather(KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding().getBuildingType() != BuildingType.DAIRY_FARM)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        //TODO is there empty space?
        DairyProduce dairyProduce = (DairyProduce) gameDatabase.getCurrentBuilding();
        if (dairyProduce.getNumberOfAnimals() == 0)
            return BuildingControllerMessages.NOT_ENOUGH_COWS;
        dairyProduce.produceLeather();
        kingdomController.changeStockedNumber(new Pair<>(Item.LEATHER_ARMOR, 1));
        return BuildingControllerMessages.SUCCESS;
    }

    private boolean checkLocationOutOfBounds(int x, int y) {
        return x < 0 || x >= gameDatabase.getMap().getSize() || y < 0 || y >= gameDatabase.getMap().getSize();
    }

    private boolean canDropBuilding(Cell cell, BuildingType buildingType) {
        if (buildingType.getCanOnlyBuiltOn() != null)
            if (!buildingType.getCanOnlyBuiltOn().contains(cell.getTexture()))
                return false;
        if (buildingType.equals(BuildingType.STAIR))
            if (!(checkNeighborContains(cell, BuildingType.SMALL_STONE_GATEHOUSE)
                    || checkNeighborContains(cell, BuildingType.LARGE_STONE_GATEHOUSE)
                    || checkNeighborContains(cell, BuildingType.LOW_WALL)
                    || checkNeighborContains(cell, BuildingType.HIGH_WALL)))
                return false;
        if (buildingType.getCategory().equals(BuildingType.Category.STORAGE))
            if (!checkNeighborContains(cell, buildingType))
                return false;
        if (buildingType.equals(BuildingType.DRAWBRIDGE))
            return checkNeighborContains(cell, BuildingType.SMALL_STONE_GATEHOUSE) &&
                    (!checkNeighborContains(cell, BuildingType.LARGE_STONE_GATEHOUSE));
        return true;
    }

    private boolean checkNeighborContains(Cell cell, BuildingType neededBuildingType) {
        int x = cell.getX();
        int y = cell.getY();
        Cell[][] map = gameDatabase.getMap().getMap();
        if (x != 0 && map[x - 1][y].getExistingBuilding().getBuildingType().equals(neededBuildingType)
                && map[x - 1][y].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            return true;
        if (y != 0 && map[x][y - 1].getExistingBuilding().getBuildingType().equals(neededBuildingType)
                && map[x][y - 1].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            return true;
        if (x != map.length - 1 && map[x + 1][y].getExistingBuilding().getBuildingType().equals(neededBuildingType)
                && map[x + 1][y].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            return true;
        return y != map.length - 1 && map[x][y + 1].getExistingBuilding().getBuildingType().equals(neededBuildingType)
                && map[x][y + 1].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom());
    }

    private boolean hasEnoughMaterialToBuild(Pair<Item, Integer> materialToBuild) {
        Kingdom currentKingdom = gameDatabase.getCurrentKingdom();
        return currentKingdom.getStockedNumber(materialToBuild.getObject1()) >= -materialToBuild.getObject2();
    }

    private boolean doesNotHaveEnoughResources(int count, ArmyType armyType, SoldierType soldierType, Kingdom kingdom) {
        return !((soldierType.getWeapon() == null || kingdom.getStockedNumber(soldierType.getWeapon()) >= count)
                && (soldierType.getArmor() == null || kingdom.getStockedNumber(soldierType.getArmor()) >= count)
                && kingdom.getGold() >= armyType.getPrice() * count);
    }

    private void useResources(int count, ArmyType armyType,
                                 SoldierType soldierType, Kingdom kingdom, KingdomController kingdomController) {
        if (soldierType.getWeapon() != null)
            kingdomController.changeStockedNumber(new Pair<>(soldierType.getWeapon(), -count));
        if (soldierType.getArmor() != null)
            kingdomController.changeStockedNumber(new Pair<>(soldierType.getArmor(), -count));
        kingdom.changeGold(-armyType.getPrice() * count);
    }

    private boolean isThereAnEnemyHere(int x, int y) {
        if (gameDatabase.getMap().getMap()[x][y].getArmies() == null) return false;
        for (Army army : gameDatabase.getMap().getMap()[x][y].getArmies()) {
            if (army.getOwner() != gameDatabase.getCurrentKingdom()) return true;
        }
        return false;
    }

    private boolean isThereAnEnemyNearby(int x, int y) {
        return isThereAnEnemyHere(x, y)
                || (x > 0 && isThereAnEnemyHere(x - 1, y))
                || (x < gameDatabase.getMap().getSize() - 1 && isThereAnEnemyHere(x + 1, y))
                || (y > 0 && isThereAnEnemyHere(x, y - 1))
                || (y < gameDatabase.getMap().getSize() - 1 && isThereAnEnemyHere(x, y + 1));
    }

    private void changeDrawBridgeClosedState(int x, int y) {
        Cell[][] map = gameDatabase.getMap().getMap();
        if (x != 0 && map[x - 1][y].getExistingBuilding().getBuildingType().equals(BuildingType.DRAWBRIDGE)
                && map[x - 1][y].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            ((GateAndStairs) map[x - 1][y].getExistingBuilding()).changeClosedState();
        if (y != 0 && map[x][y - 1].getExistingBuilding().getBuildingType().equals(BuildingType.DRAWBRIDGE)
                && map[x][y - 1].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            ((GateAndStairs) map[x][y - 1].getExistingBuilding()).changeClosedState();
        if (x != map.length - 1 && map[x + 1][y].getExistingBuilding().getBuildingType().equals(BuildingType.DRAWBRIDGE)
                && map[x + 1][y].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            ((GateAndStairs) map[x + 1][y].getExistingBuilding()).changeClosedState();
        if (y != map.length - 1 && map[x][y + 1].getExistingBuilding().getBuildingType().equals(BuildingType.DRAWBRIDGE)
                && map[x][y + 1].getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
            ((GateAndStairs) map[x][y + 1].getExistingBuilding()).changeClosedState();
    }
}
