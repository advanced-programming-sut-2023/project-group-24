package view.menus.gamemenus;

import controller.AppController;
import controller.gamecontrollers.*;
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

class GameMenuTest {
    private final Map map = new Map(10, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }
    private GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private TradeController tradeController = new TradeController(gameDatabase);
    private KingdomController kingdomController = new KingdomController(gameDatabase);
    private UnitController unitController = new UnitController(gameDatabase);
    private BuildingController buildingController = new BuildingController(gameDatabase);
    private GameController gameController = new GameController(gameDatabase);
    private GameMenu gameMenu = new GameMenu(kingdomController, unitController, buildingController, gameController);

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
    void run() {
        AppController.setCurrentMenu(MenusName.GAME_MENU);
        String data = "how many round played\n" +
                "how many turn played\n" +
                "select unit -x 1 -y 2 -t type\n" +
                "move unit to -x 1 -y 2\n" +
                "patrol unit -x 2 -y 2\n" +
                "stop patrol\n" +
                "set offensive\n" +
                "set ladder -d up\n" +
                "attack -e 2 3\n" +
                "attack -x 1 -y 2\n" +
                "pour oil -d up\n" +
                "dig tunnel\n" +
                "build -q \"siege tower\"\n" +
                "disband unit\n" +
                "dig moat -d up\n" +
                "fill moat -d up\n" +
                "attackbuilding -x 1 -y 2\n" +
                "stop\n" +
                "show popularity factors\n" +
                "show popularity\n" +
                "show food list\n" +
                "food rate -r 3\n" +
                "food rate show\n" +
                "tax rate -r 3\n" +
                "tax rate show\n" +
                "drop building -x 2 -y 1 -t \"town hall\"\n" +
                "select building -x 2 -y 2\n" +
                "create unit -t archer -c 2\n" +
                "change gate state\n" +
                "open dog cage\n" +
                "remove moate -x 1 -y 2\n" +
                "show building details\n" +
                "produce leather\n" +
                "produce item -n \"sword\"\n" +
                "repair\n" +
                "show current menu\n" +
                "chert\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assertions.assertDoesNotThrow(() -> {
            try {
                gameMenu.run();
            }
            catch (NoSuchElementException ignored) {

            }
        });
    }
}