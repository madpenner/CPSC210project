package persistence;

import model.Menu;
import model.MenuItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//code in this class from JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Menu menu = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMenu() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMenu.json");
        try {
            Menu menu = reader.read();
            assertEquals("My Menu", menu.getMenuName());
            assertEquals(0, menu.getNumItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMenu() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMenu.json");
        try {
            Menu menu = reader.read();
            assertEquals("My Menu", menu.getMenuName());
            List<MenuItem> items = menu.getMenuItems();
            assertEquals(2, items.size());
            checkItem("egg", 5, 4, items.get(0));
            checkItem("toast", 3, 7, items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
