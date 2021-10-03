package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {
    private MenuItem testItem;

    @BeforeEach
    void runBefore() {
        testItem = new MenuItem("Sandwich", 500, 5);
    }

    @Test
    void testConstructor() {
        assertEquals("Sandwich", testItem.getName());
        assertEquals(500, testItem.getPrice());
        assertEquals(5, testItem.getQuantity());
    }

    @Test
    void testSellItemNoException() {
        try {
            testItem.sellItem();
            assertEquals(500, testItem.getProfit());
            assertEquals(4, testItem.getQuantity());
        } catch (OutOfStockException e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testSellItemWithException() {
        try {
            MenuItem newItem = new MenuItem("tea", 30, 0);
            newItem.sellItem();
            fail("should have thrown exception");
        } catch (OutOfStockException e) {
            //expected
        }
    }

    @Test
    void testRestock() {
        testItem.restock(5);

        assertEquals(10, testItem.getQuantity());
    }


    @Test
    void testToJson() {
        JSONObject json = new JSONObject();
        json.put("name", testItem.getName());
        json.put("price", testItem.getPrice());
        json.put("quantity", testItem.getQuantity());

        assertEquals(json.getString("name"), "Sandwich");
        assertEquals(json.getInt("price"), 500);
        assertEquals(json.getInt("quantity"), 5);
    }
}