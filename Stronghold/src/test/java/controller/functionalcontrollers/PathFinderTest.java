package controller.functionalcontrollers;

import controller.CreateMapController;
import model.databases.Database;
import model.databases.GameDatabase;
import model.map.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    private Database database = new Database();
    private CreateMapController createMapController = new CreateMapController(database);
    {
        createMapController.createMap(10, "mapForTesting");
    }

    @Test
    void search() {
    }

    @Test
    void findPath() {
    }
}