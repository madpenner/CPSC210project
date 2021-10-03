package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// represents a menu having a collection of menu items
public class Menu implements Writable {
    private String name;
    private final List<MenuItem> items;

    //constructor
    // EFFECTS: create a menu with no menu items
    public Menu(String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    public String getMenuName() {
        return name;
    }

    // EFFECTS: returns an unmodifiable list of items in this menu
    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(items);
    }


    // add a new menu item
    // MODIFIES: this
    // EFFECTS: adds a new item to the Menu
    public void addMenuItem(MenuItem item) {
        items.add(item);
    }

    // add a new menu item
    // REQUIRES: item must already be on the Menu
    // MODIFIES: this
    // EFFECTS: removes an existing item from the Menu
    public void removeMenuItem(MenuItem item) {
        items.remove(item);
    }

    // how many items on the menu
    // EFFECTS: returns number of items on the menu
    public int getNumItems() {
        return items.size();
    }

    // name of everything on the menu
    // EFFECTS: returns the name of everything on the menu in a list
    public List<String> getNamesOfItems() {
        LinkedList<String> names = new LinkedList<>();
        for (MenuItem i : items) {
            names.add(i.getName());
        }
        return names;
    }

    // names of all the items in stock
    // EFFECTS: returns the name of everything on the menu in a list
    //          if the item is in stock
    public List<String> getItemsNamesInStock() {
        LinkedList<String> inStock = new LinkedList<>();
        for (MenuItem i : items) {
            if (i.getQuantity() > 0) {
                inStock.add(i.getName());
            }
        }
        return inStock;
    }

    // all the items in stock
    // EFFECTS: returns everything on the menu in a list
    //          if the item is in stock
    public List<MenuItem> getItemsInStock() {
        LinkedList<MenuItem> inStock = new LinkedList<>();
        for (MenuItem i : items) {
            if (i.getQuantity() > 0) {
                inStock.add(i);
            }
        }
        return inStock;
    }

    // get a specific item
    // EFFECTS: gets the MenuItem that has the given name
    public MenuItem getItemWithName(String name) {
        for (MenuItem i : items) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds the menu to the json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: returns items in this menu as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MenuItem i : items) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

}
