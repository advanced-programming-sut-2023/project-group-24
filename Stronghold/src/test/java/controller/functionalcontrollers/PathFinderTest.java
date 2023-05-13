package controller.functionalcontrollers;

import controller.AppController;
import controller.CreateMapController;
import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefenceBuilding;
import model.buildings.GateAndStairs;
import model.databases.Database;
import model.databases.GameDatabase;
import model.enums.Color;
import model.enums.Direction;
import model.enums.KingdomColor;
import model.enums.MovingType;
import model.map.Cell;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import controller.MenusName;
import view.enums.messages.CreateMapMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class PathFinderTest {
    private Database database = new Database();
    private CreateMapController createMapController = new CreateMapController(database);
    {
        createMapController.createMap(200, "mapForTesting");
    }
    private Map mapMap = database.getMapById("mapForTesting");

    @Test
    void findPath() {
        Assertions.assertEquals(createMapController.newKingdom(0, 9, "red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(8, 0, 8, 8, "sea"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(0, 8, 8, 8, "sea"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(8, 1, "ground"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(6, 0, 6, 6, "sea"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(0, 6, 6, 6, "sea"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(1, 6, "ground"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(4, 0, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(4, 1, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(4, 2, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(4, 3, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(4, 4, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(3, 4, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(2, 4, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(1, 4, "low wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(0, 4, "small stone gatehouse"), CreateMapMessages.SUCCESS);
        ((DefenceBuilding) mapMap.getMap()[4][0].getExistingBuilding()).addLadder(Direction.UP);
        ((GateAndStairs) mapMap.getMap()[0][4].getExistingBuilding()).changeClosedState();
        Assertions.assertEquals(createMapController.dropBuilding(2, 3, "stair"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(1, 1, "high wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(1, 0, "high wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(0, 1, "high wall"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.clear(5, 7), CreateMapMessages.SUCCESS);

        Assertions.assertTrue(mapMap.getMap()[2][3].canMove(Direction.RIGHT, mapMap.getMap()[2][4], MovingType.CAN_CLIMB_LADDER));
        Assertions.assertTrue(mapMap.getMap()[4][0].canMove(Direction.UP, mapMap.getMap()[5][0], MovingType.CAN_CLIMB_LADDER));


        PathFinder pathFinder1 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.CAN_NOT_CLIMB_LADDER);
        PathFinder pathFinder2 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.CAN_CLIMB_LADDER);
        PathFinder pathFinder3 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.ASSASSIN);

        Assertions.assertEquals(pathFinder1.search(new Pair<>(0, 0)), PathFinder.OutputState.BLOCKED);
        Assertions.assertEquals(pathFinder2.search(new Pair<>(0, 0)), PathFinder.OutputState.BLOCKED);
        Assertions.assertEquals(pathFinder3.search(new Pair<>(0, 0)), PathFinder.OutputState.NO_ERRORS);

        pathFinder1 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.CAN_NOT_CLIMB_LADDER);
        pathFinder2 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.CAN_CLIMB_LADDER);
        pathFinder3 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.ASSASSIN);

        Assertions.assertEquals(pathFinder1.search(new Pair<>(4, 0)), PathFinder.OutputState.BLOCKED);
        Assertions.assertEquals(pathFinder2.search(new Pair<>(2, 0)), PathFinder.OutputState.NO_ERRORS);
        Assertions.assertEquals(pathFinder3.search(new Pair<>(2, 0)), PathFinder.OutputState.NO_ERRORS);

        Assertions.assertEquals(pathFinder2.findPath().size(), 44);

        pathFinder1 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.CAN_NOT_CLIMB_LADDER);
        pathFinder2 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.CAN_CLIMB_LADDER);
        pathFinder3 = new PathFinder(mapMap, new Pair<>(9, 9), MovingType.ASSASSIN);

        Assertions.assertEquals(pathFinder1.search(new Pair<>(5, 0)), PathFinder.OutputState.NO_ERRORS);
        Assertions.assertEquals(pathFinder2.search(new Pair<>(5, 0)), PathFinder.OutputState.NO_ERRORS);
        Assertions.assertEquals(pathFinder3.search(new Pair<>(5, 0)), PathFinder.OutputState.NO_ERRORS);
    }

    private final int height = 2;// real height = 2 * height + 1
    private final int width = 5;// real width = 2 * height + 1
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(new Kingdom(KingdomColor.RED))), mapMap);
    private final Cell[][] map = mapMap.getMap();
    private int currentMapX;
    private int currentMapY;

    public String showMap(int x, int y) {
        checkIndex(x, y);
        StringBuilder outputMap = new StringBuilder();
        String boarder = "\n|" + "-".repeat(65) + "|\n";
        for (int j = currentMapY - height; j <= currentMapY + height; j++) {
            outputMap.append('|');
            for (int i = currentMapX - width; i <= currentMapX + width; i++)
                outputMap.append(FirstLine(i, j)).append('|');
            outputMap.append("\n");
            outputMap.append('|');
            for (int i = currentMapX - width; i <= currentMapX + width; i++)
                outputMap.append(SecondLine(i, j)).append('|');
            outputMap.append(boarder);
        }
        return outputMap.toString();
    }

    public boolean checkInvalidIndex(int x, int y) {
        return x > map.length || y > map.length;
    }

    private void checkIndex(int x, int y) {
        if (x < width) currentMapX = width;
        else if (x >= map[1].length - width) currentMapX = map.length - width - 1;
        else currentMapX = x;
        if (y < height) currentMapY = height;
        else if (y >= map.length - height) currentMapY = map.length - height - 1;
        else currentMapY = y;
    }

    private String SecondLine(int i, int j) {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        Cell cell = map[i][j];
        Building building = cell.getExistingBuilding();
        char buildingIcon = 'B';
        if (building == null || (building.getBuildingType().equals(BuildingType.KILLING_PIT)
                && !building.getKingdom().equals(kingdom))) buildingIcon = ' ';
        else if (building instanceof DefenceBuilding || building.getBuildingType().getName().contains("tower") ||
                building.getBuildingType().getName().contains("turret")) buildingIcon = 'W';
        char troop = ' ';
        for (Army army : cell.getArmies())
            if (army.getPath() == null) {
                troop = 'S';
                break;
            }
        return cell.getTexture().getColor().toString() + " " + buildingIcon + ' ' + troop + ' ' + Color.RESET;
    }

    private String FirstLine(int i, int j) {
        Cell cell = map[i][j];
        char movingTroop = ' ';
        for (Army army : cell.getArmies())
            if (army.getPath() != null) {
                movingTroop = 'M';
                break;
            }
        char tree = ' ';
        if (cell.getTree() != null) tree = 'T';
        return cell.getTexture().getColor().toString() + " " + movingTroop + ' ' + tree + " " + Color.RESET;
    }

    public String moveMap(int changeY, int changeX) {
        return showMap(currentMapX + changeX, currentMapY + changeY);
    }

    public String showDetails(int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map.length) return "\033[0;31mIndex out of bounds!\033[0m";
        Cell cell = map[x][y];
        String building = "";
        if (cell.getExistingBuilding() != null)
            building = "building: " + cell.getExistingBuilding().getBuildingType().getName() + "    ";
        String texture = "texture: " + cell.getTexture().name() + "    ";
        String tree = "";
        if (cell.getTree() != null) tree = "tree: " + cell.getTree().name() + "    ";
        String army = getArmiesString(cell.getArmies());
        return cell.getTexture().getColor().toString() + " " + building + texture + tree + '\n' + army + Color.RESET;
    }

    private String getArmiesString(ArrayList<Army> armies) {
        StringBuilder outputArmy = new StringBuilder();
        HashMap<ArmyType, Integer> armyCount = new HashMap<>();
        for (Army army : armies) {
            ArmyType armyType = army.getArmyType();
            if (armyCount.containsKey(armyType)) armyCount.replace(armyType, armyCount.get(armyType) + 1);
            else armyCount.put(armyType, 1);
        }
        int amount = 0;
        for (ArmyType armyType : armyCount.keySet()) {
            outputArmy.append(armyType.toString()).append(" : ").append(armyCount.get(armyType)).append("  ");
            amount++;
            if (amount % 5 == 0)
                outputArmy.append('\n');
        }
        return outputArmy.toString();
    }

    public void exit() {
        AppController.setCurrentMenu(MenusName.GAME_MENU);
    }
}