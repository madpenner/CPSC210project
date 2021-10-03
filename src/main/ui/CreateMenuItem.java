package ui;

import model.MenuItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;

import static java.lang.Integer.parseInt;

// represents the application for creating a new item
public class CreateMenuItem {
    JLabel name;
    JTextField myName;
    JLabel price;
    JTextField myPrice;
    JLabel quantity;
    JTextField myQuantity;
    JButton create;
    MenuGUI menuGUI;

    public CreateMenuItem(MenuGUI menuGUI) {
        name = new JLabel("name");
        myName = new JTextField();
        price = new JLabel("price");
        myPrice = new JTextField();
        quantity = new JLabel("quantity");
        myQuantity = new JTextField();
        create = new JButton("create");
        this.menuGUI = menuGUI;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for user to enter a new item
    public void createItem() {
        JPanel panel = new JPanel();

        JLabel title = new JLabel("<html>Enter the name, price, and quantity of your new item.<br/> "
                + "Then press create to add the item to your menu.</html>");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        title.setBorder(new EmptyBorder(50, 50, 30, 50));

        panel.add(name);
        panel.add(myName);
        panel.add(price);
        panel.add(myPrice);
        panel.add(quantity);
        panel.add(myQuantity);
        panel.add(create);
        menuGUI.pack();
        //panel.setLocationRelativeTo(null);
        panel.setVisible(true);
        menuGUI.setResizable(false);


        create.setActionCommand("create");
        create.addActionListener(this::actionPerformed);

        menuGUI.displayLabel(title);
        menuGUI.displayComponent(panel);
        menuGUI.frame.setVisible(true);
    }

    // EFFECTS: processes the action performed
    private void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("create")) {
            makeItem();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the item the user has entered
    public void makeItem() {
        try {
            tryItem();
        } catch (NoEntryException e) {
            showWarning("name");
            reset();
            createItem();
        } catch (NegativeNumberException e) {
            showWarning("negative");
            reset();
            createItem();
        } catch (ItemAlreadyExistsException e) {
            showWarning("exists");
            reset();
            createItem();
        } catch (NumberFormatException e) {
            reset();
            createItem();
            //showWarning("price and quantity");
        }
    }

    // MODIFIES: this
    // EFFECTS: turns the users entries into data
    public void tryItem() throws ItemAlreadyExistsException, NoEntryException, NegativeNumberException {
        String name = myName.getText();
        int price = parseInt(myPrice.getText());
        int quantity = parseInt(myQuantity.getText());

        if (menuGUI.menu.getNamesOfItems().contains(name)) {
            throw new ItemAlreadyExistsException();
        } else if (name.equals("")) {
            throw new NoEntryException();
        } else if (price < 0 || quantity < 0) {
            throw new NegativeNumberException();
        } else {
            MenuItem item = new MenuItem(name, price, quantity);
            menuGUI.menu.addMenuItem(item);

            reset();
            createItem();
            JOptionPane.showMessageDialog(menuGUI.frame, "Your item " + name + " was created");
        }
    }

    // EFFECTS: display a warning for caught exceptions
    public void showWarning(String type) {
        if (type.equals("price and quantity")) {
            JOptionPane.showMessageDialog(menuGUI.frame, "please enter a price and quantity in whole numbers",
                    "warning", JOptionPane.INFORMATION_MESSAGE);
        } else if (type.equals("name")) {
            JOptionPane.showMessageDialog(menuGUI.frame, "Please enter a name",
                    "error", JOptionPane.ERROR_MESSAGE);
        } else if (type.equals("negative")) {
            JOptionPane.showMessageDialog(menuGUI.frame, "Please enter a whole non negative number",
                    "error", JOptionPane.ERROR_MESSAGE);
        } else if (type.equals("exists")) {
            JOptionPane.showMessageDialog(menuGUI.frame, "This item already exists",
                    "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the text fields
    public void reset() {
        myName = new JTextField();
        myPrice = new JTextField();
        myQuantity = new JTextField();
    }

}


//    JPanel panel = new JPanel();
//    JLabel title = new JLabel("enter");
//    JLabel name = new JLabel("name");
//    JTextField myName = new JTextField();
//    JLabel price = new JLabel("price");
//    JTextField myPrice = new JTextField();
//    JLabel quantity = new JLabel("quantity");
//    JTextField myQuantity = new JTextField();
//    JButton create = new JButton("create");
