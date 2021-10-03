package persistence;

import model.Menu;
import model.MenuItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//code in this class from JsonSerializationDemo

public class JsonWriterTest extends JsonTest {

    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Menu menu = new Menu("My Menu");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMenu() {
        try {
            Menu menu = new Menu("My Menu");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMenu.json");
            writer.open();
            writer.write(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
            menu = reader.read();
            assertEquals("My Menu", menu.getMenuName());
            assertEquals(0, menu.getNumItems());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMenu() {
        try {
            Menu menu = new Menu("My Menu");
            menu.addMenuItem(new MenuItem("egg", 5, 4));
            menu.addMenuItem(new MenuItem("toast", 3, 7));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.write(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            menu = reader.read();
            assertEquals("My Menu", menu.getMenuName());
            List<MenuItem> items = menu.getMenuItems();
            assertEquals(2, items.size());
            checkItem("egg", 5, 4, items.get(0));
            checkItem("toast", 3, 7, items.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
