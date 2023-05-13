package view.menus.gamemenus;

import controller.AppController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.TradeController;
import model.Kingdom;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.enums.MenusName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TradeMenuTest {
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
        AppController.setCurrentMenu(MenusName.TRADE_MENU);
        String data = "trade -t \"chert va pert\" -a 3 -p 2 -m \"12\"\n" +
                "trade -t \"stone\" -a 3 -p 2 -m \"12\"\n" +
                "trade -t \"stone\" -a 0 -p 2 -m \"12\"\n" +
                "trade -t \"stone\" -a 3 -p 0 -m \"12\"\n" +
                "trade list\n" +
                "trade history\n" +
                "trade accept -i 1 -m hello\n" +
                "chert\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assertions.assertDoesNotThrow(() -> {
            try {
                tradeMenu.run();
            }
            catch (NoSuchElementException ignored) {

            }
        });
    }
}