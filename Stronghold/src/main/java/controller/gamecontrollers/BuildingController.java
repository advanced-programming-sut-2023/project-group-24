package controller.gamecontrollers;

import model.Kingdom;
import model.buildings.Building;
import model.buildings.BuildingType;
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
        if (!checkLocationInBounds(x, y))
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
        //TODO check if there is a building in that cell and the owner is the current player. then set the current building.
        return null;
    }

    public BuildingControllerMessages createUnit(String name, int count) {
        //TODO check if the building can create a troop, check if it can create this troop, and check for resources
        return null;
    }

    public BuildingControllerMessages repair() {
        //TODO check if the current building can be repaired
        return null;
    }

    public BuildingControllerMessages setTaxRate(int taxRate) {
        //TODO setTaxRate
        return null;
    }

    public BuildingControllerMessages createResources(String resourceName) {
        //TODO check if it can make that resource
        return null;
    }

    private boolean checkLocationInBounds(int x, int y) {
        return x >= 0 && x < gameDatabase.getMap().getSize() && y >= 0 && y < gameDatabase.getMap().getSize();
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
}
