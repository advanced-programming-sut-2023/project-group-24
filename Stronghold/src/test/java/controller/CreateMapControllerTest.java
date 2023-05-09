package controller;

import model.databases.Database;
import model.map.Map;
import model.map.Texture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.messages.CreateMapMessages;

import java.io.File;

class CreateMapControllerTest {
    private Database database = new Database();
    private CreateMapController createMapController = new CreateMapController(database);
    {
        createMapController.createMap(200, "mapForTesting");
    }

    @Test
    void createMap() {
        CreateMapController createMapController = new CreateMapController(database);
        Assertions.assertEquals(createMapController.createMap(200, "testMap1"), CreateMapMessages.SUCCESS);
        File file = new File("info/maps/testMap1.json");
        Assertions.assertTrue(file.exists());
        Map map = database.getMapById("testMap1");
        Assertions.assertNotNull(map);
        Assertions.assertEquals(200, map.getSize());
        Assertions.assertEquals("testMap1", map.getId());
        Assertions.assertEquals(createMapController.createMap(300, "testMap2"), CreateMapMessages.INVALID_SIZE);
        Assertions.assertEquals(createMapController.createMap(400, "testMap1"), CreateMapMessages.ID_EXIST);
    }

    @Test
    void setTexture() {
        Assertions.assertEquals(createMapController.setTexture(0, 0, "condensed"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(0, 0, "pond big"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(0, 0, "chert"), CreateMapMessages.INVALID_TEXTURE);
        Assertions.assertEquals(createMapController.setTexture(-1, 0, "condensed"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.setTexture(200, 0, "condensed"), CreateMapMessages.INVALID_LOCATION);
    }

    @Test
    void testSetTexture() {
        Assertions.assertEquals(createMapController.setTexture(0, 1, 1, 2, "condensed"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(10, 0, 11, 1, "condensed"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(10, 0, 11, 1, "pond big"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setTexture(10, 0, 11, 1, "chert"), CreateMapMessages.INVALID_TEXTURE);
        Assertions.assertEquals(createMapController.setTexture(-1, 0, 11, 1, "condensed"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.setTexture(200, 0, 11, 1, "condensed"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.setTexture(10, 0, -1, 1, "condensed"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.setTexture(10, 0, 11, 200, "condensed"), CreateMapMessages.INVALID_LOCATION);
    }

    @Test
    void clear() {
        Assertions.assertEquals(CreateMapMessages.SUCCESS, createMapController.clear(10, 1));
        Assertions.assertEquals(CreateMapMessages.INVALID_LOCATION, createMapController.clear(200, 1));
        Assertions.assertEquals(CreateMapMessages.INVALID_LOCATION, createMapController.clear(100, -1));
    }

    @Test
    void dropRock() {
        Assertions.assertEquals(createMapController.dropRock(1, 1, "r"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropRock(-1, 1, "r"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.dropRock(1, 1, "a"), CreateMapMessages.INVALID_DIRECTION);
        createMapController.setTexture(5, 5,"sea");
        Assertions.assertEquals(createMapController.dropRock(5, 5, "r"), CreateMapMessages.NOT_HERE);
    }

    @Test
    void dropTree() {
        Assertions.assertEquals(createMapController.dropTree(13, 13, "olive tree"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropTree(-1, 1, "olive tree"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.dropTree(1, 1, "chert"), CreateMapMessages.INVALID_TYPE);
        createMapController.setTexture(5, 5,"sea");
        Assertions.assertEquals(createMapController.dropTree(5, 5, "olive tree"), CreateMapMessages.NOT_HERE);
    }

    @Test
    void newKingdom() {
        Assertions.assertEquals(createMapController.newKingdom(24, 24, "red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.newKingdom(23, 24, "red"), CreateMapMessages.KINGDOM_EXIST);
        Assertions.assertEquals(createMapController.newKingdom(24, 24, "blue"), CreateMapMessages.NOT_HERE);
        Assertions.assertEquals(createMapController.newKingdom(24, 23, "blue"), CreateMapMessages.NOT_HERE);
        Assertions.assertEquals(createMapController.newKingdom(24, 20, "chert"), CreateMapMessages.INVALID_COLOR);
        Assertions.assertEquals(createMapController.newKingdom(200, 0, "yellow"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.newKingdom(0, -1, "yellow"), CreateMapMessages.INVALID_LOCATION);
    }

    @Test
    void setCurrentKingdom() {
        Assertions.assertEquals(createMapController.newKingdom(24, 24, "red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.newKingdom(24, 20, "blue"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("blue"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("a"), CreateMapMessages.INVALID_COLOR);
        Assertions.assertEquals(createMapController.setCurrentKingdom("blue"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("yellow"), CreateMapMessages.KINGDOM_NOT_EXIST);
    }

    @Test
    void dropBuilding() {
        Assertions.assertEquals(createMapController.dropBuilding(20, 21, "granary"), CreateMapMessages.CURRENT_KINGDOM_NULL);
        Assertions.assertEquals(createMapController.newKingdom(24, 24, "red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(20, 21, "granary"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(-1, 21, "granary"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.dropBuilding(1, 200, "granary"), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.dropBuilding(1, 3, "chert va pert"), CreateMapMessages.INVALID_TYPE);
        Assertions.assertEquals(createMapController.dropBuilding(1, 20, "apple orchard"), CreateMapMessages.NOT_HERE);
        createMapController.setTexture(5, 5,"sea");
        Assertions.assertEquals(createMapController.dropBuilding(5, 5, "caged war dogs"), CreateMapMessages.NOT_HERE);
        Assertions.assertEquals(createMapController.dropBuilding(8, 8, "small stone gatehouse"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropBuilding(9, 9, "drawbridge"), CreateMapMessages.NOT_HERE);
    }

    @Test
    void dropUnit() {
        Assertions.assertEquals(createMapController.dropUnit(1, 1, "knight", 1), CreateMapMessages.CURRENT_KINGDOM_NULL);
        Assertions.assertEquals(createMapController.newKingdom(24, 24, "red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.setCurrentKingdom("red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropUnit(1, 1, "knight", 1), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.dropUnit(-1, 1, "knight", 1), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.dropUnit(200, 0, "knight", 1), CreateMapMessages.INVALID_LOCATION);
        Assertions.assertEquals(createMapController.dropUnit(0, 0, "c", 1), CreateMapMessages.INVALID_TYPE);
        Assertions.assertEquals(createMapController.dropUnit(0, 0, "knight", 0), CreateMapMessages.INVALID_COUNT);
        createMapController.setTexture(32, 32, "sea");
        Assertions.assertEquals(createMapController.dropUnit(32, 32, "knight", 10), CreateMapMessages.NOT_HERE);
    }

    @Test
    void exitCreateMapMenu() {
        Assertions.assertEquals(createMapController.exitCreateMapMenu(), CreateMapMessages.FEW_KINGDOM);
        Assertions.assertEquals(createMapController.newKingdom(0, 0, "red"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.newKingdom(0, 2, "blue"), CreateMapMessages.SUCCESS);
        Assertions.assertEquals(createMapController.exitCreateMapMenu(), CreateMapMessages.SUCCESS);
    }
}