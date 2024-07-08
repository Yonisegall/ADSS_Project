package Domain.Controllers;

import DAL.*;
import Domain.Obejects.*;
import Domain.Repositories.*;

import java.sql.SQLException;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataController {

    private static IRepository<Truck> Trucks = new TrucksRepository();
    private static IRepository<Driver> Drivers = new DriverRepository();
    private static SitesRepository Sites = new SitesRepository();
    private static IRepository<TransportDocument> Transports = new TransportsRepository();


    private static IDAO<Truck> DB_Trucks = new TrucksDAO();
    private static IDAO<Driver> DB_Drivers = new DriversDAO();
    private static IDAO<Site> DB_Sites = new SitesDAO();
    private static IDAO<TransportDocument> DB_Transports = new TransportsDAO();


    public static void AddDriver(JsonObject j) throws SQLException {

        if (Drivers.getAmount() == 0)

            SelectAllDriversFromDB();

        String name = j.get("Name").getAsString();
        String licence = j.get("Licence").getAsString();
        String password = j.get("Password").getAsString();

        int driverID = getDriverId();

        Driver new_driver = new Driver(name, licence, password,"000-00-000",
                                       0 , "Not Have",driverID , "available");

        Drivers.Add(new_driver);

        j.addProperty("Status", "available");
        j.addProperty("Route", "Not Have");
        j.addProperty("Transport ID","0" );
        j.addProperty("Truck Licence Number", "000-00-000");
        j.addProperty("Driver ID", driverID);

        DB_Drivers.INSERT(j);

    }
    public static void AddTruck(JsonObject j) throws SQLException {

        if (Trucks.getAmount() == 0)

            SelectAllTrucksFromDB();

        String n = j.get("Licence number").getAsString();
        String l = j.get("Licence level").getAsString();
        double net = j.get("Net weight").getAsDouble();
        double max = j.get("Max weight").getAsDouble();

        Truck new_truck = new Truck(n, l, net, max, "available");

        Trucks.Add(new_truck);

        j.addProperty("Status", "available");

        DB_Trucks.INSERT(j);
    }
    public static void AddSite(JsonObject j) throws SQLException {

        if (Sites.getAmount() == 0)

            SelectAllSitesFromDB();

        String name = j.get("Name").getAsString();
        String address = j.get("Address").getAsString();
        String phone = j.get("Phone number").getAsString();
        String contact = j.get("Contact").getAsString();
        String Shipping_area = j.get("Shipping area").getAsString();
        String type = j.get("Type").getAsString();

        int siteID = getSiteId();

        Site new_site = new Site(name, address, phone, siteID, contact, Shipping_area, type);

        Sites.Add(new_site);

        j.addProperty("Site ID", siteID);

        DB_Sites.INSERT(j);
    }
    public static void AddTransportDocument(TransportDocument d) throws SQLException {

        if (Transports.getAmount() == 0)
            SelectAllTransportsFromDB();

        Transports.Add(d);

        JsonObject j = new JsonObject();

        j.addProperty("Transportation ID", d.getID());
        j.addProperty("Details", d.getDetails());
        j.addProperty("Status", d.getStatus());

        DB_Transports.INSERT(j);
    }

    public static JsonObject ChooseTruck() throws SQLException {

        if (Trucks.getAmount() == 0)
            SelectAllTrucksFromDB();

        return Trucks.ChooseAll("a", "b");
    }
    public static JsonObject ChooseDriver(String truckLicence) throws SQLException {

        if (Drivers.getAmount() == 0)

            SelectAllDriversFromDB();

        return Drivers.ChooseAll(truckLicence, "b");
    }
    public static JsonObject ChooseArea() throws SQLException {

        if (Sites.getAmount() == 0)
            SelectAllSitesFromDB();

        return Sites.FindShippingArea();
    }
    public static JsonObject ChooseSite(String area, String type) throws SQLException {

        if (Sites.getAmount() == 0)
            SelectAllSitesFromDB();

        return Sites.ChooseAll(area, type);
    }
    public static JsonObject chooseAllTransports() throws SQLException {

        if (Transports.getAmount() == 0)
            SelectAllTransportsFromDB();

        return Transports.ChooseAll("a", "b");
    }

    public static Truck getTruck(String s) throws SQLException {

        if (Trucks.getAmount() == 0)
            SelectAllTrucksFromDB();

        return Trucks.FindByID(s);
    }
    public static Driver getDriver(String s) throws SQLException {

        if (Drivers.getAmount() == 0)
            SelectAllDriversFromDB();

        return Drivers.FindByID(s);
    }
    public static Site getSite(String s) throws SQLException {

        if (Sites.getAmount() == 0 )
            SelectAllSitesFromDB();

        return Sites.FindByID(s);
    }
    public static TransportDocument getTransportDoc(String s) throws SQLException {

        if (Transports.getAmount() == 0)
            SelectAllTransportsFromDB();

        return Transports.FindByID(s);
    }

    public static ArrayList<Truck> getAllTrucks() throws SQLException {

        if (Trucks.getAmount() == 0)
            SelectAllTrucksFromDB();

        return Trucks.FindAll();
    }
    public static ArrayList<Driver> getAllDrivers() throws SQLException {

        if (Drivers.getAmount() == 0)
            SelectAllDriversFromDB();

        return Drivers.FindAll();
    }
    public static ArrayList<TransportDocument> getAllTransportsDoc() throws SQLException {

        if (Transports.getAmount() == 0)
            SelectAllTransportsFromDB();

        return Transports.FindAll();
    }
    public static Map<String, Map<String, Map<String, Site>>> getAllSites() throws SQLException {

        if (Sites.getAmount() == 0)
            SelectAllSitesFromDB();

        return Sites.FindAllSites();
    }

    public static void SelectAllDriversFromDB() throws SQLException {

        List<JsonObject> all_drivers = DB_Drivers.SELECT_ALL();

        for (JsonObject j : all_drivers){

            String name = j.get("Name").getAsString();
            String licence = j.get("Licence").getAsString();
            String password = j.get("Password").getAsString();
            String status = j.get("Status").getAsString();
            String route = j.get("Route").getAsString();
            int TransportID = j.get("Transport ID").getAsInt();
            String TruckLicenceNumber = j.get("Truck Licence Number").getAsString();
            int DriverID = j.get("Driver ID").getAsInt();

            Driver d = new Driver(name, licence, password, TruckLicenceNumber, TransportID, route, DriverID, status);

            Drivers.Add(d);
        }
    }
    public static void SelectAllTrucksFromDB() throws SQLException {

        List<JsonObject> all_trucks = DB_Trucks.SELECT_ALL();

        for (JsonObject j : all_trucks){

            String Licence_number = j.get("Licence number").getAsString();
            String Licence_Level = j.get("Licence level").getAsString();
            double Net_Weight = j.get("Net weight").getAsDouble();
            double Max_Weight = j.get("Max weight").getAsDouble();
            String status = j.get("Status").getAsString();

            Truck t = new Truck(Licence_number, Licence_Level, Net_Weight, Max_Weight, status);

            Trucks.Add(t);
        }
    }
    public static void SelectAllSitesFromDB() throws SQLException {

        List<JsonObject> all_sites = DB_Sites.SELECT_ALL();

        for (JsonObject j : all_sites){

            String name = j.get("Name").getAsString();
            String address = j.get("Address").getAsString();
            String phone = j.get("Phone number").getAsString();
            int Site_ID = j.get("Site ID") .getAsInt();
            String contact = j.get("Contact").getAsString();
            String Shipping_area = j.get("Shipping area").getAsString();
            String type = j.get("Type").getAsString();

            Site s = new Site(name, address, phone, Site_ID, contact, Shipping_area, type);

            Sites.Add(s);
        }
    }
    public static void SelectAllTransportsFromDB() throws SQLException {

        List<JsonObject> all_transports = DB_Transports.SELECT_ALL();

        for (JsonObject j : all_transports){

            int Transportation_ID = j.get("Transportation ID").getAsInt();
            String Details = j.get("Details").getAsString();
            String Status = j.get("Status").getAsString();

            TransportDocument t = new TransportDocument(Status, Details, Transportation_ID);

            Transports.Add(t);

        }
    }

    public static void updateDriver(JsonObject j) throws SQLException {

        if (Drivers.getAmount() == 0)
            SelectAllDriversFromDB();

        Drivers.Update(j);

        DB_Drivers.UPDATE(j);

    }
    public static void updateTruck(JsonObject j) throws SQLException {

        if (Trucks.getAmount() == 0)
            SelectAllTrucksFromDB();

        Trucks.Update(j);

        DB_Trucks.UPDATE(j);
    }
    public static void updateTransport(JsonObject j) throws SQLException {

        if (Transports.getAmount() == 0)
            SelectAllTransportsFromDB();

        Transports.Update(j);

        DB_Transports.UPDATE(j);
    }
    public static void updateSite(JsonObject j) throws SQLException {

        if (Sites.getAmount() == 0)
            SelectAllSitesFromDB();

        Sites.Update(j);

        DB_Sites.UPDATE(j);
    }

    public static int getAmount(String type) throws SQLException {
        switch (type){
            case "Transport" -> {
                if (Transports.getAmount() == 0)
                    SelectAllTransportsFromDB();

                return Transports.getAmount();
            }
            case "Drivers" -> {
                if (Drivers.getAmount() == 0)
                    SelectAllDriversFromDB();

                return Drivers.getAmount();
            }
            case "Trucks" -> {
                if (Trucks.getAmount() == 0)
                    SelectAllTrucksFromDB();

                return Trucks.getAmount();
            }
            case "Sites" -> {
                if (Sites.getAmount() == 0 )
                    SelectAllSitesFromDB();

                return Sites.getAmount();
            }
        }
        return 0;
    }
    public static int getDriverId() throws SQLException {

        if ((DataController.getAmount("Drivers")) == 0)
            return 10;

        return (DataController.getAmount("Drivers")) + 10;
    }
    public static int getSiteId() throws SQLException {

        if ((DataController.getAmount("Sites")) == 0)
            return 10;

        return (DataController.getAmount("Sites")) + 10;
    }

}
