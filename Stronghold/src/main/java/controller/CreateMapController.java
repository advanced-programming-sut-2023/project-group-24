package controller;

import model.databases.Database;
import model.enums.Direction;
import model.Kingdom;
import model.enums.KingdomColor;
import model.army.*;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.map.Cell;
import model.map.Map;
import model.map.Texture;
import model.map.Tree;
import utils.enums.MenusName;
import view.enums.messages.CreateMapMessages;

public class CreateMapController {
    private final Database database;
    private Map map;
    private Kingdom currentKingdom;

    public CreateMapController(Database database) {
        this.database = database;
    }

    public CreateMapMessages createMap(int size, String id) {
        if (database.getMapById(id) != null)
            return CreateMapMessages.ID_EXIST;
        if (!(size == 200 || size == 400))
            return CreateMapMessages.INVALID_SIZE;
        map = new Map(size, id);
        database.addMap(map);
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages setTexture(int x, int y, String texture) {
        if (checkLocation(x) || checkLocation(y))
            return CreateMapMessages.INVALID_LOCATION;
        Texture texture1 = Texture.stringToEnum(texture);
        if (texture1 == null)
            return CreateMapMessages.INVALID_TEXTURE;
        map.getMap()[x][y].changeTexture(texture1);
        checkChangeTexture(texture1.isCanBuild(), texture1.isCanPass(), map.getMap()[x][y]);
        return CreateMapMessages.SUCCESS;
    }

    private void checkChangeTexture(boolean canBuild, boolean canPass, Cell cell) {
        Building building = cell.getExistingBuilding();
        if (!canBuild)
            if (building != null) {
                building.getKingdom().removeBuilding(building);
                cell.setExistingBuilding(null);
            }
        if (!canPass)
            cell.clearArmies(map);
    }

    public CreateMapMessages setTexture(int x1, int y1, int x2, int y2, String texture) {
        if (checkLocation(x1) || checkLocation(y1) || checkLocation(x2) || checkLocation(y2))
            return CreateMapMessages.INVALID_LOCATION;
        if (Texture.stringToEnum(texture) == null)
            return CreateMapMessages.INVALID_TEXTURE;
        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                setTexture(i, j, texture);
        return CreateMapMessages.SUCCESS;
    }

    public boolean checkLocation(int index) {
        return index < 0 || index >= map.getSize();
    }

    public CreateMapMessages clear(int x, int y) {
        if (x >= map.getSize() || y >= map.getSize() || x < 0 || y < 0)
            return CreateMapMessages.INVALID_LOCATION;
        map.getMap()[x][y].clear(map);
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages dropRock(int x, int y, String direction) {
        if (checkLocation(x) || checkLocation(y))
            return CreateMapMessages.INVALID_LOCATION;
        if (!direction.matches("[newsr]"))
            return CreateMapMessages.INVALID_DIRECTION;
        Cell cell = map.getMap()[x][y];
        if (!cell.canBuild())
            return CreateMapMessages.NOT_HERE;
        cell.setRock(true);
        if (direction.equals("r"))
            direction = randomDirection();
        cell.setDirection(getDirection(direction));
        return CreateMapMessages.SUCCESS;
    }

    private Direction getDirection(String direction) {
        switch (direction) {
            case "n":
                return Direction.UP;
            case "e":
                return Direction.RIGHT;
            case "w":
                return Direction.LEFT;
            default:
                return Direction.DOWN;
        }
    }

    private String randomDirection() {
        int direct = (int) (Math.random() * 4);
        switch (direct) {
            case 0:
                return "n";
            case 1:
                return "e";
            case 2:
                return "w";
            default:
                return "s";
        }
    }

    public CreateMapMessages dropTree(int x, int y, String type) {
        if (checkLocation(x) || checkLocation(y))
            return CreateMapMessages.INVALID_LOCATION;
        Tree tree = Tree.stringToEnum(type);
        if (tree == null)
            return CreateMapMessages.INVALID_TYPE;
        Cell cell = map.getMap()[x][y];
        if (!cell.canBuild())
            return CreateMapMessages.NOT_HERE;
        cell.setTree(tree);
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages setCurrentKingdom(String color) {
        KingdomColor kingdomColor = KingdomColor.stringToEnum(color);
        if (kingdomColor == null)
            return CreateMapMessages.INVALID_COLOR;
        Kingdom kingdom = getKingdomWithColor(kingdomColor);
        if (kingdom == null)
            return CreateMapMessages.KINGDOM_NOT_EXIST;
        currentKingdom = kingdom;
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages newKingdom(int x, int y, String color) {
        if (checkLocation(x) || checkLocation(y))
            return CreateMapMessages.INVALID_LOCATION;
        KingdomColor color1 = KingdomColor.stringToEnum(color);
        if (color1 == null)
            return CreateMapMessages.INVALID_COLOR;
        if (getKingdomWithColor(color1) != null)
            return CreateMapMessages.KINGDOM_EXIST;
        Cell cell = map.getMap()[x][y];
        Cell cell1 = map.getMap()[x][y + 1];
        if (!(cell.canBuild() && cell1.canBuild()))
            return CreateMapMessages.NOT_HERE;
        createKingdom(cell, cell1, color1);
        return CreateMapMessages.SUCCESS;
    }

    private void createKingdom(Cell townHallLocation, Cell stockPileLocation, KingdomColor kingdomColor) {
        Kingdom kingdom = new Kingdom(kingdomColor);
        map.addKingdom(kingdom);
        new Building(kingdom, townHallLocation, BuildingType.TOWN_HALL);
        new Building(kingdom, stockPileLocation, BuildingType.STOCKPILE);
        new Soldier(townHallLocation, ArmyType.LORD, kingdom, SoldierType.LORD);
    }

    private Kingdom getKingdomWithColor(KingdomColor color) {
        for (Kingdom kingdom : map.getKingdoms())
            if (kingdom.getColor().equals(color))
                return kingdom;
        return null;
    }

    public CreateMapMessages dropBuilding(int x, int y, String type) {
        if (currentKingdom == null)
            return CreateMapMessages.CURRENT_KINGDOM_NULL;
        if (checkLocation(x) || checkLocation(y))
            return CreateMapMessages.INVALID_LOCATION;
        Cell cell = map.getMap()[x][y];
        BuildingType buildingType = BuildingType.getBuildingTypeFromName(type);
        if (buildingType == null)
            return CreateMapMessages.INVALID_TYPE;
        if (!cell.canBuild())
            return CreateMapMessages.NOT_HERE;
        if (!checkDropBuilding(cell, buildingType))
            return CreateMapMessages.NOT_HERE;
        Building.getBuildingFromBuildingType(currentKingdom, cell, buildingType);
        return CreateMapMessages.SUCCESS;
    }

    private boolean checkDropBuilding(Cell cell, BuildingType buildingType) {
        if (buildingType.getCanOnlyBuiltOn() != null)
            if (!buildingType.getCanOnlyBuiltOn().contains(cell.getTexture()))
                return false;
        if (buildingType.equals(BuildingType.STAIR))
            if (!(checkNeighbor(cell, BuildingType.SMALL_STONE_GATEHOUSE) || checkNeighbor(cell, BuildingType.
                    LARGE_STONE_GATEHOUSE) || checkNeighbor(cell, BuildingType.LOW_WALL)
                    || checkNeighbor(cell, BuildingType.HIGH_WALL)))
                return false;
        if (buildingType.getCategory().equals(BuildingType.Category.STORAGE)) {
            for (Building building : currentKingdom.getBuildings()) {
                if (building.getBuildingType() == buildingType) {
                    if (!checkNeighbor(cell, buildingType)) return false;
                    else break;
                }
            }
        }
        if (buildingType.equals(BuildingType.DRAWBRIDGE))
            return checkNeighbor(cell, BuildingType.SMALL_STONE_GATEHOUSE) &&
                    (!checkNeighbor(cell, BuildingType.LARGE_STONE_GATEHOUSE));
        return true;
    }

    private boolean checkNeighbor(Cell cell, BuildingType neededBuildingType) {
        int x = cell.getX();
        int y = cell.getY();
        if (x != 0) {
            Building building = map.getMap()[x - 1][y].getExistingBuilding();
            if (building != null && building.getBuildingType() == neededBuildingType
                    && building.getKingdom() == currentKingdom)
                return true;
        }
        if (y != 0) {
            Building building = map.getMap()[x][y - 1].getExistingBuilding();
            if (building != null && building.getBuildingType().equals(neededBuildingType)
                    && building.getKingdom().equals(currentKingdom))
                return true;
        }
        if (x != map.getSize() - 1) {
            Building building = map.getMap()[x + 1][y].getExistingBuilding();
            if (building != null && building.getBuildingType().equals(neededBuildingType)
                    && building.getKingdom().equals(currentKingdom))
                return true;
        }
        if (y != map.getSize() - 1) {
            Building building = map.getMap()[x][y + 1].getExistingBuilding();
            return building != null && building.getBuildingType().equals(neededBuildingType)
                    && building.getKingdom().equals(currentKingdom);
        }
        return false;
    }

    public CreateMapMessages dropUnit(int x, int y, String type, int count) {
        if (currentKingdom == null)
            return CreateMapMessages.CURRENT_KINGDOM_NULL;
        if (checkLocation(x) || checkLocation(y))
            return CreateMapMessages.INVALID_LOCATION;
        Cell cell = map.getMap()[x][y];
        ArmyType armyType = ArmyType.stringToEnum(type);
        if (armyType == null)
            return CreateMapMessages.INVALID_TYPE;
        if (count < 1)
            return CreateMapMessages.INVALID_COUNT;
        if (!cell.canDropUnit())
            return CreateMapMessages.NOT_HERE;
        if (SoldierType.stringToEnum(type) != null) for (int i = 0; i < count; i++)
            new Soldier(cell, armyType, currentKingdom, SoldierType.stringToEnum(type));
        else for (int i = 0; i < count; i++)
            new WarMachine(cell, armyType, currentKingdom, WarMachineType.stringToEnum(type));
        return CreateMapMessages.SUCCESS;
    }

    public CreateMapMessages exitCreateMapMenu() {
        if (map.getKingdoms().size() < 2)
            return CreateMapMessages.FEW_KINGDOM;
        AppController.setCurrentMenu(MenusName.MAIN_MENU);
        return CreateMapMessages.SUCCESS;
    }
}