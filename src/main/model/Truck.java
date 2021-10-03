package model;

// represents a truck with a name, location, and amount of gas
public class Truck {
    private final String location;     // location of the truck
    private final String name;         // name of the truck
    private int gasPercentage;   // between 0 and 100

    // REQUIRES: truckName and initialLocation have a non-zero length
    // EFFECTS: name is set to truckName and location is set to
    //          initialLocation. gasPercentage is set to 0
    public Truck(String truckName, String initialLocation) {
        name = truckName;
        location = initialLocation;
        gasPercentage = 0;
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // EFFECTS: returns location
    public String getLocation() {
        return location;
    }

    // REQUIRES: gasAmount must be non-negative
    // MODIFIES: this
    // EFFECTS: gas percentage is increased by gasAmount. If
    //          gas percentage goes over 100, it is set to 100.
    public void fillGasTank(int gasAmount) {
        gasPercentage += gasAmount;
        if (gasPercentage > 100) {
            gasPercentage = 100;
        }
    }

    // EFFECTS: returns gasPercentage
    public int getGasPercentage() {
        return gasPercentage;
    }


}
