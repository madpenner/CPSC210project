package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;

import static java.lang.Integer.parseInt;

// represents the application for selling and restocking items
public class DoStuffWithItems {
    JLabel name;
    JTextField myName;
    JLabel quantity;
    JTextField myQuantity;
    JButton restock;
    JButton sell;
    MenuGUI menuGUI;

    public DoStuffWithItems(MenuGUI menuGUI) {
        name = new JLabel("name");
        myName = new JTextField();
        quantity = new JLabel("quantity");
        myQuantity = new JTextField();
        this.menuGUI = menuGUI;

        restock = new JButton("restock");
        sell = new JButton("sell");

        restock.setActionCommand("restock");
        restock.addActionListener(this::actionPerformed);


        sell.setActionCommand("sell");
        sell.addActionListener(this::actionPerformed);
    }

    // EFFECTS: processes the action performed
    private void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("sell")) {
            sell();
        } else if (e.getActionCommand().equals("restock")) {
            restock();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for user to sell an item
    public void sellItem() {
        if (MenuGUI.menu.getNumItems() == 0) {
            JOptionPane.showMessageDialog(menuGUI.frame, "no items", "error", JOptionPane.ERROR_MESSAGE);
        } else {
            JPanel panel = new JPanel();

            JLabel title = new JLabel("Enter the name of the item you would like to sell.");
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            title.setBorder(new EmptyBorder(50, 50, 30, 60));

            panel.add(name);
            panel.add(myName);
            panel.add(sell);
            menuGUI.pack();
            panel.setVisible(true);
            menuGUI.setResizable(false);

            menuGUI.displayLabel(title);
            menuGUI.displayComponent(panel);
            menuGUI.frame.setVisible(true);
        }
    }


    // MODIFIES: this
    // EFFECTS: sells an items and says how many of that item are now in stock
    public void sell() {
        String name = myName.getText();


        if (menuGUI.menu.getItemWithName(name) == null) {
            JOptionPane.showMessageDialog(menuGUI.frame, "this item doesn't exist",
                    "error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                if (menuGUI.menu.getItemWithName(name).getQuantity() <= 0) {
                    throw new OutOfStockException();
                }
                menuGUI.menu.getItemWithName(name).sellItem();
                JOptionPane.showMessageDialog(menuGUI.frame, "sold " + name + ". There are now "
                        + menuGUI.menu.getItemWithName(name).getQuantity() + " in stock");
            } catch (OutOfStockException | model.OutOfStockException e) {
                JOptionPane.showMessageDialog(menuGUI.frame, "this item is out of stock",
                        "error", JOptionPane.ERROR_MESSAGE);
            }
        }

        reset();
        sellItem();
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for user to restock an item
    public void restockItem() {
        if (menuGUI.menu.getNumItems() == 0) {
            JOptionPane.showMessageDialog(menuGUI.frame, "no items",
                    "error", JOptionPane.ERROR_MESSAGE);
        } else {
            JPanel panel = new JPanel();

            JLabel title = new JLabel("<html>Enter the name of the item you would like to restock.<br/> "
                    + "Enter the quantity you want to add.</html>");
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            title.setBorder(new EmptyBorder(50, 50, 30, 50));

            panel.add(name);
            panel.add(myName);
            panel.add(quantity);
            panel.add(myQuantity);
            panel.add(restock);
            menuGUI.pack();
            panel.setVisible(true);
            menuGUI.setResizable(false);

            menuGUI.displayLabel(title);
            menuGUI.displayComponent(panel);
            menuGUI.frame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: restocks the given items and says how many of that item are now in stock
    public void restock() {
        String name = myName.getText();

        try {
            int quantity = parseInt(myQuantity.getText());

            if (quantity < 0) {
                throw new NegativeNumberException();
            }

            if (menuGUI.menu.getItemWithName(name) == null) {
                JOptionPane.showMessageDialog(menuGUI.frame, "this item doesn't exist",
                        "error", JOptionPane.ERROR_MESSAGE);
            } else {
                menuGUI.menu.getItemWithName(name).restock(quantity);
                JOptionPane.showMessageDialog(menuGUI.frame, "Your item " + name + " was restocked");
            }
        } catch (NumberFormatException | NegativeNumberException e) {
            JOptionPane.showMessageDialog(menuGUI.frame, "please enter quantity in whole non negative numbers",
                    "warning", JOptionPane.INFORMATION_MESSAGE);
        }

        reset();
        restockItem();
    }

    // MODIFIES: this
    // EFFECTS: resets the text fields
    public void reset() {
        myName = new JTextField();
        myQuantity = new JTextField();
    }
}
