package view.menus;

import controller.AppController;
import controller.CreateMapController;
import model.databases.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.MenusName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

class CreateMapMenuTest {
    private Database database = new Database();
    private CreateMapController createMapController = new CreateMapController(database);
    {
        createMapController.createMap(200, "mapForTesting");
    }
    private CreateMapMenu createMapMenu = new CreateMapMenu(createMapController);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

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
        AppController.setCurrentMenu(MenusName.CREATE_MAP_MENU);
        String data = "create map -i testing -s 200\n" +
                "settexture -x 1 -y 2 -t sea\n" +
                "settexture -x1 2 -y1 2 -x2 3 -y2 5 -t sea\n" +
                "clear -x 3 -y 3\n" +
                "droprock -x 10 -y 10 -d up\n" +
                "droptree -x 11 -y 11 -t olive\n" +
                "new kingdom -c red -x 12 -y 12\n" +
                "change kingdom -c red\n" +
                "dropbuilding -x 13 -y 13\n" +
                "dropunit -x 14 -y 14 -t archer\n" +
                "new kingdom -x 15 -y 15 -c blue\n" +
                "exit\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assertions.assertDoesNotThrow(() -> {
            try {
                createMapMenu.run();
            }
            catch (NoSuchElementException ignored) {

            }
        });
    }
}