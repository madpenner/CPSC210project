package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TruckTest {
    private Truck testTruck;

    @BeforeEach
    void runBefore() {
        testTruck = new Truck ("FoodTruck", "Vancouver");
    }

    @Test
    void testConstructor() {
        assertEquals("FoodTruck", testTruck.getName());
        assertEquals("Vancouver", testTruck.getLocation());
        assertEquals(0, testTruck.getGasPercentage());
    }

    @Test
    void testFillGas() {
        testTruck.fillGasTank(50);
        assertEquals(50, testTruck.getGasPercentage());
    }

    @Test
    void testFillGasToOneHundred() {
        testTruck.fillGasTank(50);
        assertEquals(50, testTruck.getGasPercentage());

        testTruck.fillGasTank(50);
        assertEquals(100, testTruck.getGasPercentage());
    }

    @Test
    void testFillGasMoreThanOneHundred() {
        testTruck.fillGasTank(100);
        assertEquals(100, testTruck.getGasPercentage());

        testTruck.fillGasTank(50);
        assertEquals(100, testTruck.getGasPercentage());
    }


}