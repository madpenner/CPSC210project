package ui;

import model.Menu;
import model.MenuItem;
import model.OutOfStockException;
import model.Truck;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//code in this class from JsonSerializationDemo and from AccountNotRobust

// represents the food truck application
public class FoodTruckApp {
    private static Menu menu;
    public Menu notOnMenu;
    private static Scanner input;
    private static String selection = "";
    private static Truck truck;
    private static final String JSON_STORE = "./data/menu.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the food truck application
    public FoodTruckApp() throws FileNotFoundException {
        menu = new Menu("Madeleine's Menu");
        notOnMenu = new Menu("My backup menu");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFoodTruck();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFoodTruck() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Welcome to the main menu! What would you like to do?");
        System.out.println("Select from:");
        System.out.println("\ti -> Create a new Menu Item");
        System.out.println("\tm -> Manage my menu");
        System.out.println("\tt -> Manage my truck");
        System.out.println("\ts -> save menu to file");
        System.out.println("\tl -> load menu from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("i")) {
            doItem();
        } else if (command.equals("m")) {
            doMenu();
        } else if (command.equals("t")) {
            doTruck();
        } else if (command.equals("s")) {
            saveMenu();
        } else if (command.equals("l")) {
            loadMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: creates a new menu item
    private void doItem() {
        System.out.print("Enter the name of your item\n");
        String name = input.nextLine();
        System.out.print("Enter the price of your item in whole numbers\n");
        int price = input.nextInt();
        System.out.print("Enter the quantity you have of your item in whole numbers\n");
        int quantity = input.nextInt();
        MenuItem first = new MenuItem(name, price, quantity);
        System.out.print("You now have your new item, " + "Would you like to add your new item to your menu?\n"
                + "If you chose not to add your item to the menu, it will be discarded\n");
        //note: keeping track of discarded menu items for future purposes

        String answer = input.next();
        //selection = selection.toLowerCase();
        if (answer.equals("yes")) {
            menu.addMenuItem(first);
            System.out.print("Here is your current menu\n" + menu.getNamesOfItems() + "\n\n");
            input = new Scanner(System.in);
        }
        if (answer.equals("no")) {
            notOnMenu.addMenuItem(first);
            System.out.print("Your item was not added.Here is your current menu\n" + menu.getNamesOfItems() + "\n\n");
            input = new Scanner(System.in);
        }
    }

    // EFFECTS: menu display menu
    private void doMenu() {
        System.out.print("What would you like to do with your menu?\n");
        System.out.println("\tsell -> sell an item");
        System.out.println("\trestock -> restock an item");
        System.out.println("\tnames -> get all the names of the items on the menu");
        System.out.println("\tstock -> get a list of all the items in stock\n");
        System.out.println("press any key to return to the main menu\n");
        selectMenuOption();
    }

    // MODIFIES: this
    // EFFECTS: processes user command for options
    private void selectMenuOption() {
        while (true) {
            selection = input.nextLine();

            if (selection.equals("sell")) {
                doSellItem();
            }
            if (selection.equals("restock")) {
                doRestockItem();
            }
            if (selection.equals("names")) {
                doGetNames();
            }
            if (selection.equals("stock")) {
                doGetItemsInStock();
            } else {
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sells an items and says how many of that item are now in stock
    private void doSellItem() {
        if (menu.getNumItems() == 0) {
            System.out.print("There is currently nothing on your menu.\n"
                    + "You must create an item in order to sell it");
        } else {
            System.out.print("Which item would you like to sell? \n" + menu.getItemsNamesInStock() + "\n");
            String item = input.nextLine();
            for (String s : menu.getItemsNamesInStock()) {
                if (s.equals(item)) {
                    try {
                        menu.getItemWithName(s).sellItem();
                    } catch (OutOfStockException e) {
                        System.out.println("this item is out of stock");
                    }
                    System.out.print("selling this item\n"
                            + "Your item now has " + menu.getItemWithName(item).getQuantity() + " in stock\n\n");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: restocks the given items and says how many of that item are now in stock
    private void doRestockItem() {
        if (menu.getNumItems() == 0) {
            System.out.print("There is currently nothing on your menu\n"
                    + "You must create an item before you can restock it\n");
        } else {
            System.out.print("Which Item would you like to restock? \n" + menu.getNamesOfItems() + "\n");
            String item = input.nextLine();
            System.out.print("How much quantity would you like to restock\n");
            int quantity = input.nextInt();
            menu.getItemWithName(item).restock(quantity);
            System.out.print("Your item now has " + menu.getItemWithName(item).getQuantity() + " in stock\n\n");
            input = new Scanner(System.in);
        }
    }

    // EFFECTS: gets names of all the items on the menu
    private void doGetNames() {
        if (menu.getNumItems() == 0) {
            System.out.print("There is currently nothing on your menu.\n" + "Create an item to get its name!\n");
        } else {
            System.out.print("Here are all the names\n" + menu.getNamesOfItems() + "\n\n");
        }
    }

    // EFFECTS: gets names of all items in stock
    private void doGetItemsInStock() {
        if (menu.getItemsNamesInStock().size() == 0) {
            System.out.print("There is currently nothing on your menu in stock.\n Try restocking an item!\n");
        } else {
            System.out.print("Here are all the items in stock\n" + menu.getItemsNamesInStock() + "\n\n");
        }
        doMenu();

    }

    // EFFECTS: displays truck options
    private void doTruck() {
        truck = new Truck("My Truck", "Vancouver");
        System.out.print("What would you like to do with your truck?");
        System.out.print("\n");
        System.out.println("\tname -> Get name of truck");
        System.out.println("\tlocation -> get location of truck");
        System.out.println("\tgas -> get gas percentage");
        System.out.println("\tfill -> fill gas tank");
        System.out.println("\n Press any key to return to the main menu");
        selectTruckOptions();
    }

    // MODIFIES: this
    // EFFECTS: processes user command for truck options
    private void selectTruckOptions() {
        while (true) {
            selection = input.nextLine();

            if (selection.equals("name")) {
                doGetTruckName();
            }
            if (selection.equals("location")) {
                doGetTruckLocation();

            }
            if (selection.equals("gas")) {
                doGetGasPercentage();
            }
            if (selection.equals("fill")) {
                doFillGasTank();
            } else {
                break;
            }
        }
    }

    // EFFECTS: returns truck name
    private void doGetTruckName() {
        System.out.print("The trucks name is " + truck.getName() + "\n");
        System.out.print("\nChoose another option or press any key to return to the main menu\n");
        selectTruckOptions();

    }

    // EFFECTS: returns truck location
    private void doGetTruckLocation() {
        System.out.print("The trucks location is " + truck.getLocation() + "\n");
        System.out.print("\nChoose another option or press any key to return to the main menu\n");
        selectTruckOptions();
    }

    // EFFECTS: returns gas percentage
    private void doGetGasPercentage() {
        System.out.print("The trucks gas percentage is " + truck.getGasPercentage() + "\n");
        System.out.print("\nChoose another option or press any key to return to the main menu\n");
        selectTruckOptions();
    }

    // MODIFIES: this
    // EFFECTS: fills the gas tank with given amount
    private void doFillGasTank() {
        System.out.print("How much percent would you like to add to your gas tank?\n");
        int gas = input.nextInt();
        truck.fillGasTank(gas);
        System.out.print("The trucks gas percentage is now " + truck.getGasPercentage());
        System.out.print("\nChoose another option or press any key to return to the main menu\n");
        input = new Scanner(System.in);
    }


    // EFFECTS: saves the menu to file
    public void saveMenu() {
        try {
            jsonWriter.open();
            jsonWriter.write(menu);
            jsonWriter.close();
            System.out.println("Saved " + menu.getMenuName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads menu from file
    public void loadMenu() {
        try {
            menu = jsonReader.read();
            System.out.println("Loaded " + menu.getMenuName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
