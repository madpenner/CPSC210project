package ui;

import model.Menu;
import model.MenuItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
// code in this class is based off robust traffic lights and the edx page for phase 3

// represents the menu application
public class MenuGUI extends JFrame {
    static Menu menu;
    private static final String JSON_STORE = "./data/menu.json";
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader jsonReader = new JsonReader(JSON_STORE);
    JFrame frame = new JFrame("My menu");
    JComponent current;
    JLabel currentLabel;
    //JLabel reminder = new JLabel("Don't forget to load your menu");
    CreateMenuItem createMenuItem = new CreateMenuItem(this);
    DoStuffWithItems doStuffWithItems = new DoStuffWithItems(this);

    public MenuGUI() throws FileNotFoundException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new FlowLayout());

        JButton loadButton = new JButton("Load Menu");
        JButton saveButton = new JButton("Save Menu");
        JButton menuButton = new JButton("menu");
        JButton itemButton = new JButton("create an item");

        frame.getContentPane().add(loadButton);
        frame.getContentPane().add(saveButton);
        frame.getContentPane().add(menuButton);
        frame.getContentPane().add(itemButton);
        //frame.getContentPane().add(reminder);


        frame.setVisible(true);
        loadButton.setActionCommand("load");
        saveButton.setActionCommand("save");
        menuButton.setActionCommand("menu");
        itemButton.setActionCommand("item");
        loadButton.addActionListener(this::actionPerformed);
        saveButton.addActionListener(this::actionPerformed);
        menuButton.addActionListener(this::actionPerformed);
        itemButton.addActionListener(this::actionPerformed);
    }

    // EFFECTS: processes the action performed
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadMenu();
            playSound(new File("./data/CantinaBand3.wav"));
        } else if (e.getActionCommand().equals("save")) {
            saveMenu();
            playSound(new File("./data/chime.wav"));
        } else if (e.getActionCommand().equals("menu")) {
            doMenu();
        } else if (e.getActionCommand().equals("allItems")) {
            displayMenu();
        } else if (e.getActionCommand().equals("inStock")) {
            displayMenuItemsInStock();
        } else if (e.getActionCommand().equals("item")) {
            createMenuItem.createItem();
        } else if (e.getActionCommand().equals("sell")) {
            doStuffWithItems.sellItem();
        } else if (e.getActionCommand().equals("restock")) {
            doStuffWithItems.restockItem();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes existing component if there is one and displays the given one
    public void displayComponent(JComponent component) {
        if (current != null) {
            frame.remove(current);
        }
        frame.add(component);
        current = component;
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: removes the existing label if there is one and displays the given one
    public void displayLabel(JLabel label) {
        if (currentLabel != null) {
            frame.remove(currentLabel);
        }
        frame.add(label);
        currentLabel = label;
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES:
    // EFFECTS: displays buttons related to the menu
    public void doMenu() {
        JPanel panel = new JPanel();

        JButton allItems = new JButton("view all items");
        JButton inStock = new JButton("view items in stock");
        JButton sell = new JButton("sell an item from your menu");
        JButton restock = new JButton("restock an item from your menu");

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(allItems);
        panel.add(inStock);
        panel.add(sell);
        panel.add(restock);

        allItems.setActionCommand("allItems");
        inStock.setActionCommand("inStock");
        sell.setActionCommand("sell");
        restock.setActionCommand("restock");
        allItems.addActionListener(this::actionPerformed);
        inStock.addActionListener(this::actionPerformed);
        sell.addActionListener(this::actionPerformed);
        restock.addActionListener(this::actionPerformed);

        JLabel title = new JLabel("Choose an option to view your menu");
        title.setBorder(new EmptyBorder(30, 50, 20, 50));
        displayLabel(title);
        displayComponent(panel);
        frame.setVisible(true);
    }

    // EFFECTS: displays all items on the menu in a table
    public void displayMenu() {
        String[] columns = new String[]{
                "Name", "Price", "Quantity"
        };


        Object[][] data = new Object[menu.getNumItems()][3];

        for (int i = 0; i < menu.getNumItems(); i++) {
            Object[] menuData = new Object[3];
            MenuItem item = menu.getMenuItems().get(i);
            menuData[0] = item.getName();
            menuData[1] = item.getPrice();
            menuData[2] = item.getQuantity();

            data[i] = menuData;
        }

        JTable table = new JTable(data, columns);

        JLabel title = new JLabel("All items on your menu");
        title.setBorder(new EmptyBorder(20, 50, 20, 50));
        displayLabel(title);
        displayComponent(new JScrollPane(table));
        frame.setVisible(true);
    }

    // EFFECTS: displays only the items in stock in a table
    public void displayMenuItemsInStock() {
        String[] columns = new String[]{
                "Name", "Price", "Quantity"
        };

        Object[][] data = new Object[menu.getItemsNamesInStock().size()][3];

        for (int i = 0; i < menu.getItemsNamesInStock().size(); i++) {
            Object[] menuData = new Object[3];
            MenuItem item = menu.getItemsInStock().get(i);
            menuData[0] = item.getName();
            menuData[1] = item.getPrice();
            menuData[2] = item.getQuantity();

            data[i] = menuData;
        }

        JTable table = new JTable(data, columns);

        JLabel title = new JLabel("All items on your menu that are currently in stock");
        title.setBorder(new EmptyBorder(20, 50, 20, 50));
        displayLabel(title);
        //frame.add(new JScrollPane(table));
        displayComponent(new JScrollPane(table));
        frame.setVisible(true);
    }

    //Make sure the file paths looks like ‘./data/FILENAME’

    // EFFECTS: plays sound from file
    public void playSound(File soundName) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundName));
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
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

    // EFFECTS: runs the gui
    public static void main(String[] args) throws FileNotFoundException {
        new MenuGUI();
    }
}
