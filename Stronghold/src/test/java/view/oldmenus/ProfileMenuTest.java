package view.oldmenus;

import controller.AppController;
import controller.MenusName;
import controller.ProfileController;
import controller.functionalcontrollers.Pair;
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
import view.oldmenus.gamemenus.TradeMenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


class ProfileMenuTest {
    private final Database database = new Database();
    private final ProfileController profileController;
    private final ProfileMenu profileMenu;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final Map map = new Map(10, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private final TradeController tradeController = new TradeController(gameDatabase);
    private final KingdomController kingdomController = new KingdomController(gameDatabase);
    private final TradeMenu tradeMenu = new TradeMenu(tradeController, kingdomController);

    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }

    {
        database.addUser("username", "pass", "nick", "s", "e", new Pair<>(1, "h"));
        AppController.setLoggedInUser(database.getUserByUsername("username"));
        AppController.setCurrentMenu(MenusName.PROFILE_MENU);
        profileController = new ProfileController(database);
        profileMenu = new ProfileMenu(profileController);
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
            } catch (NoSuchElementException ignored) {

            }
        });
    }
}