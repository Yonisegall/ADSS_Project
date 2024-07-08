package Domain.Controllers;

import Domain.Obejects.Driver;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class DriverController {

    public static String driverLogIn(JsonObject j) throws SQLException {

        ArrayList<Driver> drivers = DataController.getAllDrivers();

        for (Driver driver : drivers) {
            if (j.get("Name").getAsString().equals(driver.getName()) && j.get("Password").getAsString().equals(driver.getPassword())) {
                return driver.getName();
            }
        }
        return null;
    }
    public static String printDriverRoute(JsonObject j) throws SQLException{

        ArrayList<Driver> drivers = DataController.getAllDrivers();

        for (Driver driver : drivers) {
            if (j.get("Name").getAsString().equals(driver.getName()) &&
                    j.get("Password").getAsString().equals(driver.getPassword())) {
                return driver.getRoute();
            }
        }
        return null;
    }
    public static String updateBackDriver(JsonObject j) throws SQLException{

        ArrayList<Driver> drivers = DataController.getAllDrivers();

        JsonObject DriverJson = new JsonObject();
        JsonObject TruckJson = new JsonObject();
        JsonObject TransportJson = new JsonObject();

        for (Driver driver : drivers) {
            if (j.get("Name").getAsString().equals(driver.getName()) && j.get
                    ("Password").getAsString().equals(driver.getPassword())) {
                if (driver.getStatus().equals("On the road")){

                    DriverJson.addProperty("Driver ID", driver.getDriverID());
                    DriverJson.addProperty("Status", "available");
                    DriverJson.addProperty("Route", "Not Have");
                    DriverJson.addProperty("Transport ID", 0);
                    DriverJson.addProperty("Truck Licence Number", "000-00-000");

                    TruckJson.addProperty("Licence number", driver.getTruck().getLicence_number());
                    TruckJson.addProperty("Status", "available");

                    TransportJson.addProperty("Transportation ID",driver.getTransportDocument().getID());
                    TransportJson.addProperty("Status", "Delivered!");

                    DataController.updateDriver(DriverJson);
                    DataController.updateTruck(TruckJson);
                    DataController.updateTransport(TransportJson);

                    return ("\nWelcome back " + driver.getName() + "!");
                }
                return ("\nYou can't report back because you didnt made Transportation!\n");
            }
        }
        return ("\nYou are not exist in the system!\n");
    }
    public static String updateLeavingDriver(JsonObject j) throws SQLException {

        ArrayList<Driver> drivers = DataController.getAllDrivers();

        JsonObject DriverJson = new JsonObject();
        JsonObject TruckJson = new JsonObject();
        JsonObject TransportJson = new JsonObject();

        for (Driver driver : drivers) {
            if (j.get("Name").getAsString().equals(driver.getName()) && j.get
                    ("Password").getAsString().equals(driver.getPassword())) {

                if (driver.getTransportDocument().getID() == 0) {
                    return ("\nYou didnt assigned to any Transportation!");
                }
                else if(driver.getStatus().equals("On the road")) {
                    return "\nYou are currently in transit";
                }
                else {

                    DriverJson.addProperty("Driver ID", driver.getDriverID());
                    DriverJson.addProperty("Status", "On the road");
                    DriverJson.addProperty("Route", driver.getRoute());
                    DriverJson.addProperty("Transport ID", driver.getTransportDocument().getID());
                    DriverJson.addProperty("Truck Licence Number", driver.getTruck().getLicence_number());

                    TruckJson.addProperty("Licence number", driver.getTruck().getLicence_number());
                    TruckJson.addProperty("Status", "On the road");

                    TransportJson.addProperty("Transportation ID",driver.getTransportDocument().getID());
                    TransportJson.addProperty("Status", "Out for Delivery..");

                    DataController.updateDriver(DriverJson);
                    DataController.updateTruck(TruckJson);
                    DataController.updateTransport(TransportJson);

                    return ("\nHave a good trip " + driver.getName() + "!");
                }
            }
        }
        return ("\nYou are not exist in the system!");
    }
}
