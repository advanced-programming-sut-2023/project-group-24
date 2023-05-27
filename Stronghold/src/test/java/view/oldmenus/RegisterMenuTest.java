package view.oldmenus;

import controller.AppController;
import controller.MenusName;
import controller.RegisterMenuController;
import controller.functionalcontrollers.Pair;
import model.databases.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

class RegisterMenuTest {
    private final Database database = new Database();
    private final RegisterMenuController registerMenuController = new RegisterMenuController(database);
    private final RegisterMenu registerMenu = new RegisterMenu(registerMenuController);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    {
        database.addUser("username", "pass", "nick", "s", "e", new Pair<>(1, "h"));
        AppController.setLoggedInUser(database.getUserByUsername("username"));
        AppController.setCurrentMenu(MenusName.PROFILE_MENU);
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testRun() {
        AppController.setCurrentMenu(MenusName.REGISTER_MENU);
        String data = "user create -u aref -n omid1 -p Omid2004! -c Omid2004! -s random -e Omid1@gmail.com\n" +
                "show current menu\n" +
                "chert\n" +
                "exit\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assertions.assertDoesNotThrow(() -> {
            try {
                registerMenu.run();
            } catch (NoSuchElementException ignored) {

            }
        });
    }
}