package persistence;

import org.json.JSONObject;

//code in this class from JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
