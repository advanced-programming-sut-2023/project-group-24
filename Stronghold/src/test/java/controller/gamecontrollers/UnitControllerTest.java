package controller.gamecontrollers;

import model.Kingdom;
import model.army.*;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.messages.UnitControllerMessages;

import java.util.ArrayList;
import java.util.List;

class UnitControllerTest {
    private final Map map = new Map(10, "test");
    private final Kingdom kingdom1 = new Kingdom(KingdomColor.RED);
    private final Kingdom kingdom2 = new Kingdom(KingdomColor.BLUE);
    private final Kingdom kingdom3 = new Kingdom(KingdomColor.GREEN);
    private final GameDatabase gameDatabase = new GameDatabase(new ArrayList<>(List.of(kingdom1, kingdom2, kingdom3)), map);
    private final UnitController unitController = new UnitController(gameDatabase);
    private final KingdomController kingdomController = new KingdomController(gameDatabase);

    {
        map.addKingdom(kingdom1);
        map.addKingdom(kingdom2);
        map.addKingdom(kingdom3);
    }

    @Test
    void selectUnit() {
        Assertions.assertEquals(unitController.selectUnit(2, 2, "chert"), UnitControllerMessages.INVALID_TYPE);
        Assertions.assertEquals(unitController.selectUnit(-1, 2, "archer"), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.selectUnit(0, 10, "archer"), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.selectUnit(0, 10, "archer"), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.selectUnit(0, 2, "archer"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "archer"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(gameDatabase.getSelectedUnits().size(), 3);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(gameDatabase.getSelectedUnits().size(), 5);
    }

    @Test
    void moveUnit() {
        Assertions.assertEquals(unitController.moveUnit(0, 0), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "assassin"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.moveUnit(-1, 4), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.moveUnit(0, 10), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.moveUnit(2, 2), UnitControllerMessages.ALREADY_IN_DESTINATION);
        Assertions.assertEquals(unitController.moveUnit(2, 3), UnitControllerMessages.SUCCESS);
    }

    @Test
    void patrolUnit() {
        Assertions.assertEquals(unitController.patrolUnit(0, 0), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "assassin"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.patrolUnit(-1, 4), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.patrolUnit(0, 10), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.patrolUnit(2, 2), UnitControllerMessages.ALREADY_IN_DESTINATION);
        Assertions.assertEquals(unitController.patrolUnit(2, 3), UnitControllerMessages.SUCCESS);
    }

    @Test
    void stopUnitsPatrol() {
        Assertions.assertEquals(unitController.stopUnitsPatrol(), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "assassin"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.stopUnitsPatrol(), UnitControllerMessages.NOT_PATROL);
        Assertions.assertEquals(unitController.patrolUnit(2, 3), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.stopUnitsPatrol(), UnitControllerMessages.SUCCESS);
        Assertions.assertNull(kingdom1.getArmies().get(0).getPatrol());
    }

    @Test
    void setStateForUnits() {
        Assertions.assertEquals(unitController.setStateForUnits("offensive"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "assassin"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.setStateForUnits("chert"), UnitControllerMessages.INVALID_STATE);
        Assertions.assertEquals(unitController.setStateForUnits("offensive"), UnitControllerMessages.SUCCESS);
    }

    @Test
    void attackEnemy() {
        Assertions.assertEquals(unitController.attackEnemy(0, 1), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][7], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][7], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][7], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        new Soldier(map.getMap()[7][7], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[7][7], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[9][9], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[0][0], ArmyType.ARABIAN_SWORD_MAN, kingdom2, SoldierType.ARABIAN_SWORD_MAN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "assassin"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.attackEnemy(10, 1), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.attackEnemy(7, 7), UnitControllerMessages.NOT_ENEMY);
        Assertions.assertEquals(unitController.attackEnemy(2, 7), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.selectUnit(9, 9, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.attackEnemy(0, 0), UnitControllerMessages.OUT_OF_RANGE);
    }

    @Test
    void archerAttack() {
        Assertions.assertEquals(unitController.archerAttack(0, 1), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][7], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][7], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][7], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        new Soldier(map.getMap()[7][7], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[7][7], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[9][9], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[2][2], ArmyType.ASSASSIN, kingdom1, SoldierType.ASSASSIN);
        new Soldier(map.getMap()[0][0], ArmyType.ARABIAN_SWORD_MAN, kingdom2, SoldierType.ARABIAN_SWORD_MAN);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "assassin"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.archerAttack(10, 1), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.archerAttack(0, 1), UnitControllerMessages.NOT_ARCHER);
        Assertions.assertEquals(unitController.selectUnit(9, 9, "archer"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.archerAttack(0, 1), UnitControllerMessages.OUT_OF_RANGE);
        Assertions.assertEquals(unitController.archerAttack(7, 8), UnitControllerMessages.SUCCESS);
    }

    @Test
    void pourOil() {
        Assertions.assertEquals(unitController.pourOil("right"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ENGINEER_WITH_OIL, kingdom1, SoldierType.ENGINEER_WITH_OIL);
        new Soldier(map.getMap()[2][2], ArmyType.ENGINEER_WITH_OIL, kingdom1, SoldierType.ENGINEER_WITH_OIL);
        new Soldier(map.getMap()[2][3], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 3, "archer"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.pourOil("right"), UnitControllerMessages.NOT_SELECT_OIL);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[1][2], BuildingType.ROUND_TOWER);
        Assertions.assertEquals(unitController.pourOil("up"), UnitControllerMessages.CAN_NOT_POUR_OIL);
        Assertions.assertEquals(unitController.pourOil("c"), UnitControllerMessages.INVALID_DIRECTION);
        Assertions.assertEquals(unitController.pourOil("right"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(kingdom1.getArmies().size(), 2);
    }

    @Test
    void digTunnel() {
        Assertions.assertEquals(unitController.digTunnel(), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new Soldier(map.getMap()[2][3], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 3, "archer"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.digTunnel(), UnitControllerMessages.IRRELEVANT_UNIT);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.digTunnel(), UnitControllerMessages.BUILDING_NOT_FOUND);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[1][1], BuildingType.HIGH_WALL);
        Assertions.assertEquals(unitController.digTunnel(), UnitControllerMessages.BUILDING_NOT_FOUND);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[2][1], BuildingType.DEFENCE_TURRET);
        Assertions.assertEquals(unitController.digTunnel(), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(map.getMap()[2][1].getExistingBuilding().getHp(), 800);
    }

    @Test
    void buildEquipment() {
        Assertions.assertEquals(unitController.buildEquipment("catapult"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.ENGINEER, kingdom1, SoldierType.ENGINEER);
        new Soldier(map.getMap()[2][2], ArmyType.ENGINEER, kingdom1, SoldierType.ENGINEER);
        new Soldier(map.getMap()[2][2], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "archer"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.buildEquipment("catapult"), UnitControllerMessages.NOT_SELECTED_ENGINEER);
        Assertions.assertEquals(unitController.selectUnit(2, 2, "engineer"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.buildEquipment("battering rams"), UnitControllerMessages.NOT_ENOUGH_ENGINEER);
        Assertions.assertEquals(unitController.buildEquipment("chert"), UnitControllerMessages.INVALID_TYPE);
        Assertions.assertEquals(unitController.buildEquipment("catapult"), UnitControllerMessages.SUCCESS);
    }

    @Test
    void digMoat() {
        Assertions.assertEquals(unitController.digMoat("up"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new Soldier(map.getMap()[2][3], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.digMoat("up"), UnitControllerMessages.IRRELEVANT_UNIT);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[1][3], BuildingType.TOWN_HALL);
        Assertions.assertEquals(unitController.selectUnit(2, 3, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.digMoat("up"), UnitControllerMessages.CANNOT_BUILD);
        Assertions.assertEquals(unitController.digMoat("chert"), UnitControllerMessages.INVALID_DIRECTION);
        Assertions.assertEquals(unitController.digMoat("right"), UnitControllerMessages.SUCCESS);
    }

    @Test
    void fillMoat() {
        Assertions.assertEquals(unitController.fillMoat("up"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new Soldier(map.getMap()[2][3], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[1][3], BuildingType.MOAT);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.fillMoat("up"), UnitControllerMessages.IRRELEVANT_UNIT);
        Assertions.assertEquals(unitController.selectUnit(2, 3, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.fillMoat("up"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.fillMoat("chert"), UnitControllerMessages.INVALID_DIRECTION);
        Assertions.assertEquals(unitController.fillMoat("down"), UnitControllerMessages.MOAT_DOES_NOT_EXIST);
    }

    @Test
    void stop() {
        Assertions.assertEquals(unitController.stop(), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new Soldier(map.getMap()[2][3], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.stop(), UnitControllerMessages.SUCCESS);
    }

    @Test
    void disbandUnit() {
        Assertions.assertEquals(unitController.disbandUnit(), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new Soldier(map.getMap()[2][3], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.disbandUnit(), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(kingdom1.getArmies().size(), 1);
    }

    @Test
    void attackBuilding() {
        Assertions.assertEquals(unitController.attackBuilding(1, 1), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.KNIGHT, kingdom1, SoldierType.KNIGHT);
        new Soldier(map.getMap()[9][9], ArmyType.ARCHER, kingdom1, SoldierType.ARCHER);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.attackBuilding(1, 10), UnitControllerMessages.INVALID_LOCATION);
        Assertions.assertEquals(unitController.attackBuilding(1, 1), UnitControllerMessages.NULL_SELECTED_BUILDING);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[1][1], BuildingType.TOWN_HALL);
        Assertions.assertEquals(unitController.attackBuilding(1, 1), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.selectUnit(9, 9, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.attackBuilding(1, 1), UnitControllerMessages.OUT_OF_RANGE);
    }

    @Test
    void setLadder() {
        Assertions.assertEquals(unitController.setLadder("up"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new Soldier(map.getMap()[2][3], ArmyType.LADDER_MAN, kingdom1, SoldierType.LADDER_MAN);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[1][3], BuildingType.LOW_WALL);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.setLadder("up"), UnitControllerMessages.IRRELEVANT_UNIT);
        Assertions.assertEquals(unitController.selectUnit(2, 3, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.setLadder("up"), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.setLadder("chert"), UnitControllerMessages.INVALID_DIRECTION);
        Assertions.assertEquals(unitController.setLadder("down"), UnitControllerMessages.NO_BUILDING);
    }

    @Test
    void attackWall() {
        Assertions.assertEquals(unitController.attackWall("up"), UnitControllerMessages.NULL_SELECTED_UNIT);
        new Soldier(map.getMap()[2][2], ArmyType.TUNNELLER, kingdom1, SoldierType.TUNNELLER);
        new WarMachine(map.getMap()[2][3], ArmyType.SIEGE_TOWER, kingdom1, WarMachineType.SIEGE_TOWER);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[1][3], BuildingType.LOW_WALL);
        Assertions.assertEquals(unitController.selectUnit(2, 2, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.attackWall("up"), UnitControllerMessages.IRRELEVANT_UNIT);
        Assertions.assertEquals(unitController.selectUnit(2, 3, null), UnitControllerMessages.SUCCESS);
        Assertions.assertEquals(unitController.attackWall("chert"), UnitControllerMessages.INVALID_DIRECTION);
        Assertions.assertEquals(unitController.attackWall("down"), UnitControllerMessages.NO_BUILDING);
        Assertions.assertEquals(unitController.attackWall("up"), UnitControllerMessages.SUCCESS);
    }
}