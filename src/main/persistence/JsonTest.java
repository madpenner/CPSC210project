package persistence;

import model.MenuItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

//code in this class from JsonSerializationDemo

public class JsonTest {
    protected void checkItem(String name, int price, int quantity, MenuItem item) {
        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(quantity, item.getQuantity());
    }
}
