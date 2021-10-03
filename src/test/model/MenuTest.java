package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MenuTest {
    private Menu items;

    @BeforeEach
    public void testRunBefore() {
        items = new Menu("My Menu");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, items.getNumItems());
        assertEquals("My Menu", items.getMenuName());
    }


    @Test
    public void testAddMenuItem(){
        MenuItem i =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(i);

        assertEquals(1, items.getNumItems());

        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem buttered_scone =  new MenuItem("buttered scone", 4, 5);
        items.addMenuItem(buttered_scone);

        List<String> names = new LinkedList<>();
        names.add("coffee");
        names.add("scone");
        names.add("buttered scone");

        assertEquals(3, items.getNumItems());
        assertEquals(names, items.getNamesOfItems());
    }

    @Test
    public void testRemoveMenuItem(){
        MenuItem i =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(i);
        assertEquals(1, items.getNumItems());

        items.removeMenuItem(i);
        assertEquals(0, items.getNumItems());
    }

    @Test
    public void testGetItemsNamesInStockOneItem() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);

        List<String> inStock = new LinkedList<>();
        inStock.add("coffee");

        assertEquals(1, items.getNumItems());
        assertEquals(inStock, items.getItemsNamesInStock());
    }

    @Test
    public void testGetItemsInStockOneItem() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);

        List<MenuItem> inStock = new LinkedList<>();
        inStock.add(coffee);

        assertEquals(1, items.getNumItems());
        assertEquals(inStock, items.getItemsInStock());
    }

    @Test
    public void testGetItemsNamesInStockMultipleItems() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 5);
        items.addMenuItem(sandwich);

        List<String> inStock = new LinkedList<>();
        inStock.add("coffee");
        inStock.add("scone");
        inStock.add("sandwich");

        assertEquals(3, items.getNumItems());
        assertEquals(inStock, items.getItemsNamesInStock());
    }

    @Test
    public void testGetItemsNamesInStockNoQuantity() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 0);
        items.addMenuItem(sandwich);

        List<String> inStock = new LinkedList<>();
        inStock.add("coffee");
        inStock.add("scone");

        assertEquals(3, items.getNumItems());
        assertEquals(inStock, items.getItemsNamesInStock());
    }

    @Test
    public void testGetItemsInStockNoQuantity() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 0);
        items.addMenuItem(sandwich);

        List<MenuItem> inStock = new LinkedList<>();
        inStock.add(coffee);
        inStock.add(scone);

        assertEquals(3, items.getNumItems());
        assertEquals(inStock, items.getItemsInStock());
    }

    @Test
    public void testGetItemsNamesInStockSellItem() throws OutOfStockException {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 1);
        items.addMenuItem(sandwich);

        sandwich.sellItem();

        List<String> inStock = new LinkedList<>();
        inStock.add("coffee");
        inStock.add("scone");

        List<String> names = new LinkedList<>();
        names.add("coffee");
        names.add("scone");
        names.add("sandwich");

        assertEquals(3, items.getNumItems());
        assertEquals(inStock, items.getItemsNamesInStock());
        assertEquals(names, items.getNamesOfItems());
    }

    @Test
    public void testGetNamesOfItems() throws OutOfStockException {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 1);
        items.addMenuItem(sandwich);

        sandwich.sellItem();

        List<String> names = new LinkedList<>();
        names.add("coffee");
        names.add("scone");
        names.add("sandwich");

        assertEquals(3, items.getNumItems());
        assertEquals(names, items.getNamesOfItems());
    }

    @Test
    public void testGetItemWithName() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 1);
        items.addMenuItem(sandwich);
        MenuItem buttered_scone =  new MenuItem("buttered scone", 4, 5);
        items.addMenuItem(buttered_scone);


        assertEquals(coffee, items.getItemWithName("coffee"));
        assertEquals(scone, items.getItemWithName("scone"));
        assertEquals(buttered_scone, items.getItemWithName("buttered scone"));
    }

    @Test
    public void testGetItemWithNameNoItem() {
        MenuItem coffee =  new MenuItem("coffee", 3, 10);
        items.addMenuItem(coffee);
        MenuItem scone =  new MenuItem("scone", 4, 5);
        items.addMenuItem(scone);
        MenuItem sandwich =  new MenuItem("sandwich", 7, 1);
        items.addMenuItem(sandwich);

        assertNull(items.getItemWithName("cookie"));
    }
}
