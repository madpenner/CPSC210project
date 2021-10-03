package model;


import org.json.JSONObject;
import persistence.Writable;

// represents a menu Item with a name, price, and quantity
public class MenuItem implements Writable {
    private final String name;          // name of the item
    private final int price;            // in cents
    private int quantity;         // how many of that item are in stock
    private int profit;           // total profit on that item

    // REQUIRES: menuItem has a non-zero length and item price and item
    //           quantity are non-negative.
    // EFFECTS: the items name is set to itemName, the price is set to item
    //           price, and the quantity is set to itemQuantity
    public MenuItem(String itemName, int itemPrice, int itemQuantity) {
        name = itemName;
        price = itemPrice;
        quantity = itemQuantity;
    }

    // EFFECTS: returns the name of the item
    public String getName() {
        return name;
    }

    // EFFECTS: returns the price of the item
    public int getPrice() {
        return price;
    }

    // EFFECTS: returns the quantity of the item
    public int getQuantity() {
        return quantity;
    }

    // MODIFIES: this
    // EFFECTS: quantity of item goes down by 1 and profit is
    //          increased by the price of the item sold
    public void sellItem() throws OutOfStockException {
        if (quantity <= 0) {
            throw new OutOfStockException();
        } else {
            quantity -= 1;
            profit += price;
        }
    }

    // note: not useful for current user stories but may be useful later
    // EFFECTS: returns the total profit from that item
    public int getProfit() {
        return profit;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds num to quantity
    public void restock(int num) {
        quantity += num;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds the menu item to the json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("quantity", quantity);
        return json;
    }
}
