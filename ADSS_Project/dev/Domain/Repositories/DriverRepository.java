package Domain.Repositories;


import Domain.Obejects.Driver;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class DriverRepository implements IRepository<Driver> {

    private static ArrayList<Driver> AllDrivers = new ArrayList<>();

    /**
     * Add a Driver to the AllDrivers
     */
    @Override
    public void Add(Driver driver) {
        AllDrivers.add(driver);
    }

    /**
     * Delete a Driver from the AllDrivers
     */
    @Override
    public void Delete(Driver driver) {
        AllDrivers.remove(driver);
    }

    /**
     * Find a Driver from the AllDrivers using String ID
     */
    @Override
    public Driver FindByID(String ID) {
        for (Driver d: AllDrivers){
            if (d.to_String().equals(ID))
                return d;
        }
        return null;
    }

    /**
     * Returns JsonObject of all the available Drivers in the AllDrivers
     */
    @Override
    public JsonObject ChooseAll(String s, String v) {

        JsonObject j = new JsonObject();
        int count = 1;
        for (Driver d: AllDrivers){
            if (d.getStatus().equals("available")){
                if (d.getLicense().compareTo(s) >= 0){
                    j.addProperty(String.valueOf(count), d.to_String());
                    count++;
                }
            }
        }
        return j;
    }

    /**
     * Update a Driver using JsonObject
     */
    @Override
    public void Update(JsonObject j) {

        int id = j.get("Driver ID").getAsInt();
        String newStatus = j.get("Status").getAsString();
        String newRoute = j.get("Route").getAsString();
        int newTransportID = j.get("Transport ID").getAsInt();
        String newTruckLicenceNumber = j.get("Truck Licence Number").getAsString();

        for (Driver d : AllDrivers){
            if (d.getDriverID() == id){
                d.setStatus(newStatus);
                d.setRoute(newRoute);
                d.setTransportID(newTransportID);
                d.setTruckLicenceNumber(newTruckLicenceNumber);
            }
        }
    }

    /**
     * @return the AllDrivers
     */
    @Override
    public ArrayList<Driver> FindAll() {
        return AllDrivers;
    }

    /**
     * @return the sum of all the Drivers
     */
    @Override
    public int getAmount() {
        return AllDrivers.size();
    }

}
