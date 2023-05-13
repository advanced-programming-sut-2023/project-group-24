package model.army;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WarMachineTypeTest {

    private final WarMachineType warMachineType = WarMachineType.FIRE_BALLISTA;

    @Test
    void stringToEnum() {
        for (WarMachineType value : WarMachineType.values()) {
            Assertions.assertEquals(value, WarMachineType.stringToEnum(value.toString()));
        }
    }

    @Test
    void getEngineerNeeded() {
        Assertions.assertEquals(warMachineType.getEngineerNeeded(), 2);
    }
}