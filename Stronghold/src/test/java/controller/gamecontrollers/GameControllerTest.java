package controller.gamecontrollers;

import model.Kingdom;
import model.User;
import model.army.ArmyType;
import model.army.Soldier;
import model.army.SoldierType;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Cell;
import model.map.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private final Map map = new Map(10, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
        User user = new User("a", "b", "c", "d", "e", new Pair<>(1, "2"));
        kingdom1.setOwner(user);
        kingdom2.setOwner(user);
        kingdom3.setOwner(user);
        new Soldier(map.getMap()[2][2], ArmyType.LORD, kingdom1, SoldierType.LORD);
        new Soldier(map.getMap()[2][2], ArmyType.LORD, kingdom2, SoldierType.LORD);
        new Soldier(map.getMap()[2][2], ArmyType.LORD, kingdom3, SoldierType.LORD);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[0][0], BuildingType.TOWN_HALL);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[0][1], BuildingType.TOWN_HALL);
        Building.getBuildingFromBuildingType(kingdom3, map.getMap()[0][2], BuildingType.TOWN_HALL);
    }
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private final UnitController unitController = new UnitController(gameDatabase);
    private final KingdomController kingdomController = new KingdomController(gameDatabase);


    private GameController gameController = new GameController(gameDatabase);

    @Test
    void nextTurn() {
        gameController.nextTurn(kingdomController);
    }
}