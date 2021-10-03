package persistence;

import model.Menu;
import model.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//code in this class from JsonSerializationDemo

// Represents a reader that reads menu from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads menu from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Menu read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenu(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses menu from JSON object and returns it
    private Menu parseMenu(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Menu menu = new Menu(name);
        addItems(menu, jsonObject);
        return menu;
    }

    // MODIFIES: menu
    // EFFECTS: parses menu items from JSON object and adds them to menu
    private void addItems(Menu menu, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addMenuItem(menu, nextItem);
        }
    }

    // MODIFIES: menu
    // EFFECTS: parses item from JSON object and adds it to menu
    private void addMenuItem(Menu menu, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int price = (jsonObject.getInt("price"));
        int quantity = (jsonObject.getInt("quantity"));
        MenuItem item = new MenuItem(name, price, quantity);
        menu.addMenuItem(item);
    }
}
