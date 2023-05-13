package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.User;
import model.army.*;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GameControllerTest {
    private final Map map = new Map(200, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private final UnitController unitController = new UnitController(gameDatabase);
    private final KingdomController kingdomController = new KingdomController(gameDatabase);
    private final GameController gameController = new GameController(gameDatabase);

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

    @Test
    void nextTurn() {
        int i = 0;
        for (BuildingType value : BuildingType.values()) {
            Building.getBuildingFromBuildingType(kingdom1, map.getMap()[40][i++], value);
        }
        i = 0;
        for (BuildingType value : BuildingType.values()) {
            Building.getBuildingFromBuildingType(kingdom2, map.getMap()[50][i++], value);
        }
        i = 0;
        for (BuildingType value : BuildingType.values()) {
            Building.getBuildingFromBuildingType(kingdom3, map.getMap()[60][i++], value);
        }

        Kingdom[] kingdoms = new Kingdom[]{kingdom1, kingdom2, kingdom3};

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < SoldierType.values().length; k++) {
                Soldier soldier = new Soldier(map.getMap()[100 + j][k], ArmyType.values()[k], kingdoms[j], SoldierType.values()[k]);
                soldier.changeState(UnitState.OFFENSIVE);
            }
        }

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < WarMachineType.values().length; k++) {
                WarMachine soldier = new WarMachine(map.getMap()[103 + j][k], ArmyType.values()[k], kingdoms[j], WarMachineType.values()[k]);
                soldier.changeState(UnitState.OFFENSIVE);
            }
        }

        Assertions.assertDoesNotThrow(() -> gameController.nextTurn(kingdomController));
        Assertions.assertDoesNotThrow(() -> gameController.nextTurn(kingdomController));
        Assertions.assertDoesNotThrow(() -> gameController.nextTurn(kingdomController));
        Assertions.assertDoesNotThrow(() -> gameController.nextTurn(kingdomController));
        Assertions.assertDoesNotThrow(() -> gameController.nextTurn(kingdomController));
    }
}