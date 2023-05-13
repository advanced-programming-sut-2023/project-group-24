package view.menus;

import controller.AppController;
import controller.ProfileMenuController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.TradeController;
import model.Kingdom;
import model.databases.Database;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.functionalcontrollers.Pair;
import controller.MenusName;
import view.menus.gamemenus.TradeMenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


class ProfileMenuTest {
    private Map map = new Map(10, "test");
    private Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }
    private GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private TradeController tradeController = new TradeController(gameDatabase);
    private KingdomController kingdomController = new KingdomController(gameDatabase);
    private TradeMenu tradeMenu = new TradeMenu(tradeController, kingdomController);
    private final Database database = new Database();
    {
        database.addUser("username", "pass", "nick", "s", "e", new Pair<>(1, "h"));
        AppController.setLoggedInUser(database.getUserByUsername("username"));
        AppController.setCurrentMenu(MenusName.PROFILE_MENU);
    }
    private final ProfileMenuController profileMenuController = new ProfileMenuController(database);
    private final ProfileMenu profileMenu = new ProfileMenu(profileMenuController);

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
        AppController.setCurrentMenu(MenusName.PROFILE_MENU);
        String data = "profile change -u \"j j\"\n" +
                "profile change -n nickname\n" +
                "profile change password -o 123 -p 234\n" +
                "profile change -e username@a.c\n" +
                "profile change slogan -s username\n" +
                "profile remove slogan\n" +
                "profile display highscore\n" +
                "profile display rank\n" +
                "profile display slogan\n" +
                "profile display\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assertions.assertDoesNotThrow(() -> {
            try {
                profileMenu.run();
            }
            catch (NoSuchElementException ignored) {

            }
        });
    }
}