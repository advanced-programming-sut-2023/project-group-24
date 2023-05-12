package controller.functionalcontrollers;

import controller.CreateMapController;
import model.buildings.Building;
import model.buildings.DefenceBuilding;
import model.databases.Database;
import model.databases.GameDatabase;
import model.enums.Direction;
import model.enums.KingdomColor;
import model.enums.MovingType;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    private Database database = new Database();
    private CreateMapController createMapController = new CreateMapController(database);

    @Test
    void findPath() {
        createMapController.createMap(200, "mapForTesting");
        Map map = database.getMapById("mapForTesting");
        createMapController.newKingdom(0, 9, "red");
        createMapController.setCurrentKingdom("red");
        createMapController.setTexture(8, 0, 8, 8, "sea");
        createMapController.setTexture(0, 8, 8, 8, "sea");
        createMapController.setTexture(8, 1, "ground");
        createMapController.setTexture(6, 0, 6, 6, "sea");
        createMapController.setTexture(0, 6, 6, 6, "sea");
        createMapController.setTexture(1, 6, "ground");
        createMapController.dropBuilding(4, 0, "low wall");
        createMapController.dropBuilding(4, 1, "low wall");
        createMapController.dropBuilding(4, 2, "low wall");
        createMapController.dropBuilding(4, 3, "low wall");
        createMapController.dropBuilding(4, 4, "low wall");
        createMapController.dropBuilding(3, 4, "low wall");
        createMapController.dropBuilding(2, 4, "low wall");
        createMapController.dropBuilding(1, 4, "low wall");
        createMapController.dropBuilding(0, 4, "small stone gatehouse");
        ((DefenceBuilding) map.getMap()[4][0].getExistingBuilding()).addLadder(Direction.DOWN);
        createMapController.dropBuilding(2, 4, "stair");
        createMapController.dropBuilding(1, 1, "high wall");
        createMapController.dropBuilding(1, 0, "high wall");
        createMapController.dropBuilding(0, 1, "high wall");


        PathFinder pathFinder1 = new PathFinder(map, new Pair<>(9, 9), MovingType.CAN_NOT_CLIMB_LADDER);
        PathFinder pathFinder2 = new PathFinder(map, new Pair<>(9, 9), MovingType.CAN_CLIMB_LADDER);
        PathFinder pathFinder3 = new PathFinder(map, new Pair<>(9, 9), MovingType.ASSASSIN);
        PathFinder pathFinder4 = new PathFinder(map, new Pair<>(9, 9), MovingType.TUNNELLER);

        Assertions.assertEquals(pathFinder1.search(new Pair<>(0, 0)), PathFinder.OutputState.BLOCKED);
        Assertions.assertEquals(pathFinder2.search(new Pair<>(0, 0)), PathFinder.OutputState.BLOCKED);
        Assertions.assertEquals(pathFinder3.search(new Pair<>(0, 0)), PathFinder.OutputState.NO_ERRORS);
        Assertions.assertEquals(pathFinder4.search(new Pair<>(0, 0)), PathFinder.OutputState.NO_ERRORS);
    }
}