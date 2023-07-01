package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.army.Soldier;
import model.army.SoldierType;
import model.buildings.*;
import model.databases.GameDatabase;
import model.enums.Direction;
import model.enums.Item;
import model.map.Cell;
import model.map.Map;
import view.enums.messages.BuildingControllerMessages;

import java.util.ArrayList;

public class BuildingController {
    private final GameDatabase gameDatabase;

    public BuildingController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public BuildingControllerMessages dropBuilding(Cell cell, String type) {
        BuildingType buildingType = BuildingType.getBuildingTypeFromName(type);
        if (buildingType == null)
            return BuildingControllerMessages.INVALID_TYPE;
        if (buildingType == BuildingType.MOAT || buildingType == BuildingType.TOWN_HALL)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (!cell.canBuild())
            return BuildingControllerMessages.CANNOT_BUILD_HERE;
        if (!canDropBuilding(cell, buildingType))
            return BuildingControllerMessages.CANNOT_BUILD_HERE;
        if (!hasEnoughMaterialToBuild(buildingType.getMaterialToBuild()))
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        if (gameDatabase.getCurrentKingdom().getGold() < buildingType.getPrice())
            return BuildingControllerMessages.NOT_ENOUGH_GOLD;
        return BuildingControllerMessages.SUCCESS;
    }

    public void drop(Cell cell, String type, KingdomController kingdomController) {
        BuildingType buildingType = BuildingType.getBuildingTypeFromName(type);
        Building building = Building.getBuildingFromBuildingType(gameDatabase.getCurrentKingdom(), cell, buildingType);
        gameDatabase.getCurrentKingdom().changeGold(-buildingType.getPrice());
        if (buildingType.getMaterialToBuild() != null)
            kingdomController.changeStockedNumber(buildingType.getMaterialToBuild());
        if (building instanceof GateAndStairs) {
            setUpClosedState((GateAndStairs) building);
        }
    }

    public BuildingControllerMessages selectBuilding(int x, int y) {
        if (checkLocationOutOfBounds(x, y))
            return BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS;
        Cell cell = gameDatabase.getMap().getMap()[x][y];
        if (cell.getExistingBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS;
        if (cell.getExistingBuilding().getBuildingType() == BuildingType.MOAT)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (cell.getExistingBuilding().getKingdom() != gameDatabase.getCurrentKingdom())
            return BuildingControllerMessages.NOT_OWNER;
        gameDatabase.setCurrentBuilding(cell.getExistingBuilding());
        if (cell.getExistingBuilding().getBuildingType() == BuildingType.MARKET)
            return BuildingControllerMessages.MARKET;
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages createUnit(String name, int count, KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding() == null) return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        if (gameDatabase.getCurrentBuilding().getBuildingType().getCategory() != BuildingType.Category.ARMY_MAKER)
            return BuildingControllerMessages.INCORRECT_BUILDING;
        if (count <= 0) return BuildingControllerMessages.INCORRECT_COUNT;
        ArmyType armyType = ArmyType.stringToEnum(name);
        SoldierType soldierType = SoldierType.stringToEnum(name);
        if (soldierType == null || armyType == null) return BuildingControllerMessages.INVALID_TYPE;
        if (gameDatabase.getCurrentBuilding().getBuildingType().getTroopsItCanMake() != soldierType.getNation())
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        Kingdom currentKingdom = gameDatabase.getCurrentKingdom();
        if (doesNotHaveEnoughResources(count, armyType, soldierType, currentKingdom))
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        if (gameDatabase.getCurrentKingdom().getUnemployment() < count)
            return BuildingControllerMessages.NOT_ENOUGH_PEOPLE;
        if (name.equals("knight") && kingdomController.getNumberOfAvailableHorses() < count)
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        if (name.equals("knight")) for (int i = 0; i < count; i++) kingdomController.useHorse();
        for (int i = 0; i < count; i++)
            new Soldier(gameDatabase.getCurrentBuilding().getLocation(), armyType, currentKingdom, soldierType);
        gameDatabase.getCurrentKingdom().removeUnemploymentPeople(count);
        useResources(count, armyType, soldierType, currentKingdom, kingdomController);
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages repair(KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        Building building = gameDatabase.getCurrentBuilding();
        if (!building.getBuildingType().canBeRepaired())
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        int stonesNeeded = 1;
        if (building.getBuildingType().getMaterialToBuild() != null)
            stonesNeeded = (building.getBuildingType().getMaxHp() - building.getHp()) *
                    (-building.getBuildingType().getMaterialToBuild().getObject2())
                    / building.getBuildingType().getMaxHp() + 1;
        if (gameDatabase.getCurrentKingdom().getStockedNumber(Item.STONE) < stonesNeeded)
            return BuildingControllerMessages.NOT_ENOUGH_MATERIAL;
        if (isThereAnEnemyNearby(building.getLocation().getX(), building.getLocation().getY()))
            return BuildingControllerMessages.ENEMY_IS_NEARBY;
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, -stonesNeeded));
        gameDatabase.getCurrentBuilding().repair();
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages changeGateClosedState() {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        Building building = gameDatabase.getCurrentBuilding();
        if (building.getBuildingType() != BuildingType.LARGE_STONE_GATEHOUSE
                && building.getBuildingType() != BuildingType.SMALL_STONE_GATEHOUSE)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (isThereAnEnemyHere(building.getLocation().getX(), building.getLocation().getY()))
            return BuildingControllerMessages.ENEMY_IS_NEARBY;
        ((GateAndStairs) building).changeClosedState();
        changeDrawBridgeClosedState(building.getLocation().getX(), building.getLocation().getY());
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages openDogCage() {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        Building building = gameDatabase.getCurrentBuilding();
        if (gameDatabase.getCurrentBuilding().getBuildingType() != BuildingType.CAGED_WAR_DOGS)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        for (int i = 0; i < 3; i++)
            new Soldier(building.getLocation(), ArmyType.DOG, gameDatabase.getCurrentKingdom(), SoldierType.DOG);
        building.takeDamage(building.getHp());
        building.getLocation().setExistingBuilding(null);
        gameDatabase.getCurrentKingdom().removeBuilding(building);
        gameDatabase.setCurrentBuilding(null);
        return BuildingControllerMessages.SUCCESS;
    }

    public ArrayList<String> showDetails(Cell cell) {
        if (cell.getExistingBuilding() != null)
            return cell.getExistingBuilding().showDetails();
        return null;
    }

    public BuildingControllerMessages produceLeather(KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        if (gameDatabase.getCurrentBuilding().getBuildingType() != BuildingType.DAIRY_FARM)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (kingdomController.freeSpace(Item.LEATHER_ARMOR) == 0)
            return BuildingControllerMessages.NOT_ENOUGH_SPACE;
        DairyProduce dairyProduce = (DairyProduce) gameDatabase.getCurrentBuilding();
        if (dairyProduce.getNumberOfAnimals() == 0)
            return BuildingControllerMessages.NOT_ENOUGH_COWS;
        dairyProduce.produceLeather();
        kingdomController.changeStockedNumber(new Pair<>(Item.LEATHER_ARMOR, 1));
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages selectItemToProduce(String name) {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        if (gameDatabase.getCurrentBuilding().getBuildingType().getProduces() == null)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (Item.stringToEnum(name) == null)
            return BuildingControllerMessages.ITEM_DOES_NOT_EXIST;
        ProducerBuilding building = (ProducerBuilding) gameDatabase.getCurrentBuilding();
        if (!building.setItemToProduce(Item.stringToEnum(name)))
            return BuildingControllerMessages.CANNOT_PRODUCE_ITEM;
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages removeMoat(int x, int y) {
        if (checkLocationOutOfBounds(x, y)) return BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS;
        Building building = gameDatabase.getMap().getMap()[x][y].getExistingBuilding();
        if (building == null || building.getBuildingType() != BuildingType.MOAT)
            return BuildingControllerMessages.NO_MOATS_HERE;
        if (building.getKingdom() != gameDatabase.getCurrentKingdom())
            return BuildingControllerMessages.NOT_OWNER;
        building.getKingdom().removeBuilding(building);
        gameDatabase.getMap().getMap()[x][y].setExistingBuilding(null);
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages setTaxRate(int taxRate, KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        if (gameDatabase.getCurrentBuilding().getBuildingType() != BuildingType.TOWN_HALL)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (taxRate < -3 || taxRate > 8)
            return BuildingControllerMessages.INVALID_NUMBER;
        kingdomController.handleTaxFactor(taxRate);
        return BuildingControllerMessages.SUCCESS;
    }

    public BuildingControllerMessages setFoodRate(int foodRate, KingdomController kingdomController) {
        if (gameDatabase.getCurrentBuilding() == null)
            return BuildingControllerMessages.NO_BUILDINGS_SELECTED;
        if (gameDatabase.getCurrentBuilding().getBuildingType() != BuildingType.GRANARY)
            return BuildingControllerMessages.IRRELEVANT_BUILDING;
        if (foodRate < -2 || foodRate > 2)
            return BuildingControllerMessages.INVALID_NUMBER;
        kingdomController.setFoodRate(foodRate);
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
            if (!(checkNeighborContains(cell, BuildingType.SMALL_STONE_GATEHOUSE) || checkNeighborContains(
                    cell, BuildingType.LARGE_STONE_GATEHOUSE) || checkNeighborContains(cell, BuildingType.LOW_WALL)
                    || checkNeighborContains(cell, BuildingType.HIGH_WALL)))
                return false;
        if (buildingType.getCategory().equals(BuildingType.Category.STORAGE)) {
            for (Building building : gameDatabase.getCurrentKingdom().getBuildings()) {
                if (building.getBuildingType() == buildingType) {
                    if (!checkNeighborContains(cell, buildingType)) return false;
                    else break;
                }
            }
        }
        if (buildingType.equals(BuildingType.DRAWBRIDGE))
            return checkNeighborContains(cell, BuildingType.SMALL_STONE_GATEHOUSE) ||
                    (checkNeighborContains(cell, BuildingType.LARGE_STONE_GATEHOUSE));
        return true;
    }

    boolean checkNeighborContains(Cell cell, BuildingType neededBuildingType) {
        return findNeighborContains(cell, neededBuildingType) != Direction.NONE;
    }

    private Direction findNeighborContains(Cell cell, BuildingType neededBuildingType) {
        int x = cell.getX();
        int y = cell.getY();
        Map map = gameDatabase.getMap();
        Kingdom currentKingdom = gameDatabase.getCurrentKingdom();

        Building building1 = null, building2 = null, building3 = null, building4 = null;
        if (x != 0) building1 = map.getMap()[x - 1][y].getExistingBuilding();
        if (y != 0) building2 = map.getMap()[x][y - 1].getExistingBuilding();
        if (x != map.getSize() - 1) building3 = map.getMap()[x + 1][y].getExistingBuilding();
        if (y != map.getSize() - 1) building4 = map.getMap()[x][y + 1].getExistingBuilding();

        if (building1 != null && building1.getBuildingType() == neededBuildingType
                && building1.getKingdom() == currentKingdom) return Direction.UP;
        if (building2 != null && building2.getBuildingType() == neededBuildingType
                && building2.getKingdom() == currentKingdom) return Direction.LEFT;
        if (building3 != null && building3.getBuildingType() == neededBuildingType
                && building3.getKingdom() == currentKingdom) return Direction.DOWN;
        if (building4 != null && building4.getBuildingType() == neededBuildingType
                && building4.getKingdom() == currentKingdom) return Direction.RIGHT;
        return Direction.NONE;
    }

    private boolean hasEnoughMaterialToBuild(Pair<Item, Integer> materialToBuild) {
        if (materialToBuild == null) return true;
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
        Direction direction = findNeighborContains(map[x][y], BuildingType.DRAWBRIDGE);
        GateAndStairs drawbridge = (GateAndStairs) getBuildingFromDirection(x, y, direction);

        if (drawbridge != null && drawbridge.isClosed() != ((GateAndStairs) map[x][y].getExistingBuilding()).isClosed())
            drawbridge.changeClosedState();
    }


    private void setUpDirection(GateAndStairs building) {

    }

    private void setUpClosedState(GateAndStairs building) {
        if (building.getBuildingType() != BuildingType.DRAWBRIDGE) return;
        Direction direction = findNeighborContains(building.getLocation(), BuildingType.SMALL_STONE_GATEHOUSE);
        if (direction == Direction.NONE)
            direction = findNeighborContains(building.getLocation(), BuildingType.LARGE_STONE_GATEHOUSE);
        Cell cell = building.getLocation();
        GateAndStairs gate = (GateAndStairs) getBuildingFromDirection(cell.getX(), cell.getY(), direction);
        if (gate != null && gate.isClosed() != building.isClosed()) building.changeClosedState();
    }

    private Building getBuildingFromDirection(int x, int y, Direction direction) {
        Cell[][] map = gameDatabase.getMap().getMap();
        switch (direction) {
            case UP:
                return map[x - 1][y].getExistingBuilding();
            case RIGHT:
                return map[x][y + 1].getExistingBuilding();
            case LEFT:
                return map[x][y - 1].getExistingBuilding();
            case DOWN:
                return map[x + 1][y].getExistingBuilding();
            default:
                return null;
        }
    }
}
