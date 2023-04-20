package model;

import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
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
    @Test
    void checkStayedLoggedInUser() {
        Database database1 = new Database();
        Assertions.assertDoesNotThrow(() -> database1.setStayedLoggedInUser(user));
    }

    @Test
    void testSave() {
        Assertions.assertDoesNotThrow(() -> database.saveDataIntoFile());
    }
}