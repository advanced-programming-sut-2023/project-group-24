package view.menus.gamemenus;

import controller.AppController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.ShopController;
import model.Kingdom;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.MenusName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class ShopMenuTest {
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
    private ShopController shopController = new ShopController(gameDatabase);
    private KingdomController kingdomController = new KingdomController(gameDatabase);
    private ShopMenu shopMenu = new ShopMenu(shopController, kingdomController);

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
        AppController.setCurrentMenu(MenusName.SHOP_MENU);
        String data = "buy -i stone -a 1\n" +
                "buy -i stone -a 1\n" +
                "buy -i stone -a 0\n" +
                "show price list\n" +
                "sell -i c -a 1\n" +
                "sell -i stone -a 12\n" +
                "sell -i stone -a 0\n" +
                "chert\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assertions.assertDoesNotThrow(() -> {
            try {
                shopMenu.run();
            }
            catch (NoSuchElementException ignored) {

            }
        });
    }
}