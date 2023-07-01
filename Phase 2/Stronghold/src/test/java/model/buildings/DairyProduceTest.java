package model.buildings;

import controller.functionalcontrollers.Pair;
import model.Kingdom;
import model.enums.Item;
import model.enums.KingdomColor;
import model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DairyProduceTest {
    private final Kingdom kingdom = new Kingdom(KingdomColor.RED);
    private final Map map = new Map(10, "testMap");
    private final DairyProduce dairyProduce = new DairyProduce(kingdom, map.getMap()[2][2], BuildingType.DAIRY_FARM);

    @Test
    void getNumberOfAnimals() {
        Assertions.assertEquals(dairyProduce.getNumberOfAnimals(), 0);
    }

    @Test
    void produceAnimal() {
        Assertions.assertEquals(dairyProduce.getNumberOfAnimals(), 0);
        dairyProduce.produceAnimal();
        Assertions.assertEquals(dairyProduce.getNumberOfAnimals(), 1);
        dairyProduce.produceAnimal();
        dairyProduce.produceAnimal();
        Assertions.assertEquals(dairyProduce.getNumberOfAnimals(), 3);
    }

    @Test
    void produceLeather() {
        Assertions.assertEquals(dairyProduce.getNumberOfAnimals(), 0);
        dairyProduce.produceLeather();
        Assertions.assertEquals(dairyProduce.getNumberOfAnimals(), -1);
    }

    @Test
    void produceCheese() {
        Assertions.assertNull(dairyProduce.produceCheese());
        dairyProduce.produceAnimal();
        dairyProduce.produceAnimal();
        dairyProduce.produceAnimal();
        Assertions.assertEquals(dairyProduce.produceCheese(), new Pair<>(Item.CHEESE, 4));
    }

    @Test
    void showDetails() {
        Assertions.assertEquals(dairyProduce.showDetails().size(), 4);
    }
}