package model;

import model.databases.Database;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import utils.Pair;

import java.io.IOException;
import java.util.Vector;

class DatabaseTest {
    @Mock
    User user;
    @Mock
    Vector<Map> maps;
    @Mock
    Vector<User> allUsers;
    @InjectMocks
    Database database = new Database();

    User user1 = new User("username1", "password1", "nickname1", "slogan1",
            "email1", new Pair<>(1, "recoveryAnswer1"));
    User user2 = new User("username2", "password2", "nickname2",
            "slogan2", "email2", new Pair<>(2, "recoveryAnswer2"));
    Map map1 = new Map(200, "map1Id");
    Map map2 = new Map(300, "map2Id");

    @Test
    void checkSetStayedLoggedInUser() {
        Database database1 = new Database();
        Assertions.assertDoesNotThrow(() -> database1.setStayedLoggedInUser(user));
    }

    @Test
    void testSave() {
        Assertions.assertDoesNotThrow(() -> database.saveDataIntoFile());
    }

    @Test
    void testAddingAndGetting() {
        Database database1 = new Database();
        database1.addMap(new Map(200, "map1id"));
        Assertions.assertTrue(database1.mapIdExists("map1id"));
        Assertions.assertFalse(database1.mapIdExists("map10id"));
        Assertions.assertNotNull(database1.getMapById("map1id"));
    }

    @Test
    void testSaveAndLoadAllUsers() throws IOException {
        Database database1 = new Database();
        database1.addUser("username1", "password1", "nickname1", "slogan1",
                "email1", new Pair<>(1, "recoveryAnswer1"));
        database1.addUser("username2", "password2", "nickname2",
                "slogan2", "email2", new Pair<>(2, "recoveryAnswer2"));
        database1.saveDataIntoFile();

        Database database2 = new Database();
        database2.loadDataFromFile();
        Assertions.assertEquals(database1.getAllUsers().get(1).getUsername(), database2.getAllUsers().get(1).getUsername());
        Assertions.assertEquals(database1.getAllUsers().get(0).getUsername(), database2.getAllUsers().get(0).getUsername());
    }

    @Test
    void testSavingStayedLoggedInUser() {
        Database database1 = new Database();
        database1.setStayedLoggedInUser(user1);
        Database database2 = new Database();
        database2.loadDataFromFile();
        Assertions.assertEquals(database2.getStayedLoggedInUser().getUsername(),
                database1.getStayedLoggedInUser().getUsername());
    }

    @Test
    void testGettingUser() {
        Database database1 = new Database();
        database1.addUser("username1", "password1", "nickname1", "slogan1",
                "email1", new Pair<>(1, "recoveryAnswer1"));
        database1.addUser("username2", "password2", "nickname2",
                "slogan2", "email2", new Pair<>(2, "recoveryAnswer2"));
        Assertions.assertNotNull(database1.getUserByUsername("username1"));
        Assertions.assertEquals(database1.getUserByUsername("username1").getNickname(), "nickname1");
        Assertions.assertEquals(database1.getUserByUsername("username2").getNickname(), "nickname2");
        Assertions.assertNull(database1.getUserByUsername("chert"));
    }

    @Test
    void testGettingUserRank() {
        Database database1 = new Database();
        database1.addUser("username1", "password1", "nickname1", "slogan1",
                "email1", new Pair<>(1, "recoveryAnswer1"));
        database1.addUser("username2", "password2", "nickname2",
                "slogan2", "email2", new Pair<>(2, "recoveryAnswer2"));
        database1.getUserByUsername("username1").setHighScore(100);
        database1.getUserByUsername("username2").setHighScore(200);
        Assertions.assertEquals(database1.getAllUsersByRank().get(0).getUsername(), "username2");
        Assertions.assertEquals(database1.getAllUsersByRank().get(1).getUsername(), "username1");
        database1.getUserByUsername("username1").setHighScore(500);
        Assertions.assertEquals(database1.getAllUsersByRank().get(0).getUsername(), "username1");
        Assertions.assertEquals(database1.getAllUsersByRank().get(1).getUsername(), "username2");
    }
}