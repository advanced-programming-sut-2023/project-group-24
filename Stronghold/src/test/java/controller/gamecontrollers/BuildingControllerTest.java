package controller.gamecontrollers;

import model.Kingdom;
import model.People;
import model.army.ArmyType;
import model.army.Soldier;
import model.army.SoldierType;
import model.buildings.*;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.KingdomColor;
import model.enums.PopularityFactor;
import model.map.Map;
import model.map.Texture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;
import view.enums.messages.BuildingControllerMessages;

import java.util.ArrayList;
import java.util.List;

class BuildingControllerTest {

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
    private BuildingController buildingController = new BuildingController(gameDatabase);
    private KingdomController kingdomController = new KingdomController(gameDatabase);

    @Test
    void dropBuilding() {
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.NOT_ENOUGH_MATERIAL);
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 200));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(-1, 2, "market", kingdomController), BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 10, "market", kingdomController), BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 3, "chert", kingdomController), BuildingControllerMessages.INVALID_TYPE);
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.CANNOT_BUILD_HERE);
        Assertions.assertEquals(buildingController.dropBuilding(5, 5, "granary", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(7, 7, "granary", kingdomController), BuildingControllerMessages.CANNOT_BUILD_HERE);
        Assertions.assertEquals(buildingController.dropBuilding(5, 6, "granary", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(5, 4, "granary", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(4, 5, "granary", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(6, 5, "granary", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(8, 8, "large stone gatehouse", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(8, 9, "drawbridge", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(9, 8, "drawbridge", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertNotEquals(((GateAndStairs) kingdom1.getBuildings().get(kingdom1.getBuildings().size() - 1)).getDirection(), ((GateAndStairs) kingdom1.getBuildings().get(kingdom1.getBuildings().size() - 2)).getDirection());
        Assertions.assertEquals(buildingController.dropBuilding(7, 7, "cathedral", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(7, 8, "cathedral", kingdomController), BuildingControllerMessages.NOT_ENOUGH_GOLD);
        Assertions.assertEquals(buildingController.dropBuilding(9, 3, "moat", kingdomController), BuildingControllerMessages.IRRELEVANT_BUILDING);
        gameDatabase.getMap().getMap()[1][1].changeTexture(Texture.SEA);
        Assertions.assertEquals(buildingController.dropBuilding(1, 1, "market", kingdomController), BuildingControllerMessages.CANNOT_BUILD_HERE);
    }

    @Test
    void selectBuilding() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 200));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "small stone gatehouse", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectBuilding(2, 3), BuildingControllerMessages.NO_BUILDINGS);
        Building.getBuildingFromBuildingType(gameDatabase.getCurrentKingdom(), gameDatabase.getMap().getMap()[4][4], BuildingType.MOAT);
        Assertions.assertEquals(buildingController.selectBuilding(4, 4), BuildingControllerMessages.IRRELEVANT_BUILDING);
        gameDatabase.nextTurn();
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.NOT_OWNER);
    }

    @Test
    void createUnit() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 20));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 15));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "barracks", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 3, "mercenary post", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "engineer guild", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 5, "small stone gatehouse", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.createUnit("archer", 1, kingdomController), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.createUnit("archer", 1, kingdomController), BuildingControllerMessages.NOT_ENOUGH_MATERIAL);
        kingdomController.changeStockedNumber(new Pair<>(Item.BOW, 10));
        Assertions.assertEquals(buildingController.createUnit("archer", 1, kingdomController), BuildingControllerMessages.NOT_ENOUGH_PEOPLE);
        kingdom1.addPeople(new People(kingdom1));
        kingdom1.addPeople(new People(kingdom1));
        kingdom1.addPeople(new People(kingdom1));
        kingdom1.addPeople(new People(kingdom1));
        Assertions.assertEquals(buildingController.createUnit("archer", 1, kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.createUnit("archer", 3, kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.createUnit("archer", 1, kingdomController), BuildingControllerMessages.NOT_ENOUGH_PEOPLE);
        Assertions.assertEquals(buildingController.selectBuilding(2, 3), BuildingControllerMessages.SUCCESS);
        kingdom1.addPeople(new People(kingdom1));
        kingdom1.addPeople(new People(kingdom1));
        kingdom1.addPeople(new People(kingdom1));
        kingdom1.addPeople(new People(kingdom1));
        Assertions.assertEquals(buildingController.createUnit("archer", 1, kingdomController), BuildingControllerMessages.IRRELEVANT_BUILDING);
        Assertions.assertEquals(map.getMap()[2][2].getArmies().size(), 4);
        Assertions.assertEquals(buildingController.createUnit("chert", 1, kingdomController), BuildingControllerMessages.INVALID_TYPE);
        Assertions.assertEquals(buildingController.createUnit("arabian archer", 0, kingdomController), BuildingControllerMessages.INCORRECT_COUNT);
        Assertions.assertEquals(buildingController.createUnit("arabian sword man", 1, kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.createUnit("engineer", 1, kingdomController), BuildingControllerMessages.SUCCESS);
    }

    @Test
    void repair() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "small stone gatehouse", kingdomController), BuildingControllerMessages.SUCCESS);
        map.getMap()[2][4].getExistingBuilding().takeDamage(100);
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.IRRELEVANT_BUILDING);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.NOT_ENOUGH_MATERIAL);
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 41));
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(4, 4, "round tower", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectBuilding(4, 4), BuildingControllerMessages.SUCCESS);
        map.getMap()[4][4].getExistingBuilding().takeDamage(1000);
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.NOT_ENOUGH_MATERIAL);
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 20));
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.NOT_ENOUGH_MATERIAL);
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 1));
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.SUCCESS);
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 10));
        map.getMap()[4][4].getExistingBuilding().takeDamage(100);
        new Soldier(map.getMap()[3][4], ArmyType.ARCHER, kingdom2, SoldierType.ARCHER);
        Assertions.assertEquals(buildingController.repair(kingdomController), BuildingControllerMessages.ENEMY_IS_NEARBY);
    }

    @Test
    void changeGateClosedState() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 99));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "small stone gatehouse", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.changeGateClosedState(), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.changeGateClosedState(), BuildingControllerMessages.IRRELEVANT_BUILDING);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertFalse(((GateAndStairs) map.getMap()[2][4].getExistingBuilding()).isClosed());
        Assertions.assertEquals(buildingController.changeGateClosedState(), BuildingControllerMessages.SUCCESS);
        Assertions.assertTrue(((GateAndStairs) map.getMap()[2][4].getExistingBuilding()).isClosed());
        Assertions.assertEquals(buildingController.dropBuilding(2, 3, "drawbridge", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertTrue(((GateAndStairs) map.getMap()[2][3].getExistingBuilding()).isClosed());
        Assertions.assertEquals(buildingController.changeGateClosedState(), BuildingControllerMessages.SUCCESS);
        Assertions.assertFalse(((GateAndStairs) map.getMap()[2][4].getExistingBuilding()).isClosed());
        Assertions.assertFalse(((GateAndStairs) map.getMap()[2][3].getExistingBuilding()).isClosed());
        Assertions.assertEquals(buildingController.changeGateClosedState(), BuildingControllerMessages.SUCCESS);
        Assertions.assertTrue(((GateAndStairs) map.getMap()[2][4].getExistingBuilding()).isClosed());
        Assertions.assertTrue(((GateAndStairs) map.getMap()[2][3].getExistingBuilding()).isClosed());
        Assertions.assertEquals(buildingController.changeGateClosedState(), BuildingControllerMessages.SUCCESS);
        Assertions.assertFalse(((GateAndStairs) map.getMap()[2][4].getExistingBuilding()).isClosed());
        Assertions.assertFalse(((GateAndStairs) map.getMap()[2][3].getExistingBuilding()).isClosed());
    }

    @Test
    void openDogCage() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 99));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "caged war dogs", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.openDogCage(), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.openDogCage(), BuildingControllerMessages.IRRELEVANT_BUILDING);
        int oldArmyCount = kingdom1.getArmies().size();
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.openDogCage(), BuildingControllerMessages.SUCCESS);
        int currentArmyCount = kingdom1.getArmies().size();
        Assertions.assertEquals(currentArmyCount - oldArmyCount , 3);
        Assertions.assertEquals(map.getMap()[2][4].getArmies().get(map.getMap()[2][4].getArmies().size() - 1).getArmyType() , ArmyType.DOG);
        Assertions.assertEquals(buildingController.openDogCage(), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.NO_BUILDINGS);
    }

    @Test
    void showDetails() {
        //TODO maybe fill out this one more thoroughly later? I'll just do one real quick
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 99));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.showDetails().size(), 1);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.showDetails().get(0), "market");
    }

    @Test
    void produceLeather() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 99));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "dairy farmer", kingdomController), BuildingControllerMessages.CANNOT_BUILD_HERE);
        map.getMap()[2][4].changeTexture(Texture.CONDENSED);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "dairy farmer", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.produceLeather(kingdomController), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.produceLeather(kingdomController), BuildingControllerMessages.IRRELEVANT_BUILDING);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.produceLeather(kingdomController), BuildingControllerMessages.NOT_ENOUGH_SPACE);
        Assertions.assertEquals(buildingController.dropBuilding(2, 3, "armoury", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.produceLeather(kingdomController), BuildingControllerMessages.NOT_ENOUGH_COWS);
        ((DairyProduce) map.getMap()[2][4].getExistingBuilding()).produceAnimal();
        Assertions.assertEquals(buildingController.produceLeather(kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.produceLeather(kingdomController), BuildingControllerMessages.NOT_ENOUGH_COWS);
    }

    @Test
    void selectItemToProduce() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 99));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "blacksmith", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectItemToProduce("mace"), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.selectItemToProduce("mace"), BuildingControllerMessages.IRRELEVANT_BUILDING);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectItemToProduce("chert"), BuildingControllerMessages.ITEM_DOES_NOT_EXIST);
        Assertions.assertEquals(buildingController.selectItemToProduce("spear"), BuildingControllerMessages.CANNOT_PRODUCE_ITEM);
        Assertions.assertEquals(buildingController.selectItemToProduce("mace"), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.selectItemToProduce("sword"), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(((ProducerBuilding) map.getMap()[2][4].getExistingBuilding()).getItemToProduce(), 1);
        Assertions.assertEquals(buildingController.selectItemToProduce("mace"), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(((ProducerBuilding) map.getMap()[2][4].getExistingBuilding()).getItemToProduce(), 0);
    }

    @Test
    void removeMoat() {
        Assertions.assertEquals(buildingController.removeMoat(1, 200), BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS);
        Assertions.assertEquals(buildingController.removeMoat(-1, 2), BuildingControllerMessages.LOCATION_OUT_OF_BOUNDS);
        Assertions.assertEquals(buildingController.removeMoat(2, 2), BuildingControllerMessages.NO_MOATS_HERE);
        Building.getBuildingFromBuildingType(kingdom2, map.getMap()[1][2], BuildingType.MOAT);
        Building.getBuildingFromBuildingType(kingdom1, map.getMap()[2][2], BuildingType.MOAT);
        Assertions.assertEquals(buildingController.removeMoat(1, 2), BuildingControllerMessages.NOT_OWNER);
        Assertions.assertEquals(buildingController.removeMoat(2, 2), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.removeMoat(2, 2), BuildingControllerMessages.NO_MOATS_HERE);
    }

    @Test
    void setTaxRate() {
        kingdomController.changeStockedNumber(new Pair<>(Item.WOOD, 200));
        kingdomController.changeStockedNumber(new Pair<>(Item.STONE, 99));
        Assertions.assertEquals(buildingController.dropBuilding(2, 2, "market", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.dropBuilding(2, 4, "town hall", kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.setTaxRate(2, kingdomController), BuildingControllerMessages.NO_BUILDINGS_SELECTED);
        Assertions.assertEquals(buildingController.selectBuilding(2, 2), BuildingControllerMessages.MARKET);
        Assertions.assertEquals(buildingController.setTaxRate(2, kingdomController), BuildingControllerMessages.IRRELEVANT_BUILDING);
        Assertions.assertEquals(buildingController.selectBuilding(2, 4), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(buildingController.setTaxRate(10, kingdomController), BuildingControllerMessages.INVALID_NUMBER);
        Assertions.assertEquals(buildingController.setTaxRate(3, kingdomController), BuildingControllerMessages.SUCCESS);
        Assertions.assertEquals(kingdom1.getPopularityFactor(PopularityFactor.TAX), -6);
    }
}