package Tests;

import java.sql.SQLException;
import static Presentation.CreateTransportation.createDocument;

import Domain.Controllers.DataController;
import Domain.Controllers.TransportationController;
import Presentation.DataConnector;

import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class TransportationManagerTests {

    @Test
    public void AddDriverTest() throws SQLException {

        JsonObject j = new JsonObject();

        j.addProperty("Name","Yosi Ben Haiim");
        j.addProperty("Licence","C");
        j.addProperty("Password","16161616");

        DataController.AddDriver(j);

        String d = "Name: Yosi Ben Haiim, Licence Level: C.";

        Domain.Obejects.Driver driver = DataController.getDriver(d);

        assertNotNull(driver,  "Failed to add driver");

        assertEquals("Yosi Ben Haiim", driver.getName(), "Incorrect driver name!");
        assertEquals("C", driver.getLicense(), "Incorrect driver licence!");
        assertEquals("16161616", driver.getPassword(), "Incorrect driver name!");
        assertEquals(24, driver.getDriverID(), "Incorrect driver ID!");
        assertEquals("Not Have", driver.getRoute(), "Incorrect driver route!");
        assertEquals("available", driver.getStatus(), "Incorrect driver status!");
    }
    @Test
    public void AddTruckTest() throws SQLException {

        JsonObject j = new JsonObject();

        j.addProperty("Licence number", "161-61-616");
        j.addProperty("Licence level", "B");
        j.addProperty("Net weight", 4000);
        j.addProperty("Max weight", 15000);

        DataController.AddTruck(j);

        String t = "Licence Number: 161-61-616, Licence Level: B, Max weight: 15000.0.";

        Domain.Obejects.Truck truck = DataController.getTruck(t);

        assertNotNull(truck,  "Failed to add truck");

        assertEquals("161-61-616", truck.getLicence_number(), "Incorrect truck licence number!");
        assertEquals("B", truck.getLicence_level(), "Incorrect truck licence level!");
        assertEquals(4000, truck.getNet_weight(), "Incorrect truck net weight!");
        assertEquals(15000, truck.getMax_weight(), "Incorrect truck max weight!");
        assertEquals("available", truck.getStatus(), "Incorrect truck status!");
    }
    @Test
    public void AddStoreTest() throws SQLException {

        JsonObject j = new JsonObject();

        j.addProperty("Type", "Store");
        j.addProperty("Name", "Mevaseret-Zion");
        j.addProperty("Address", "Mevaseret-Zion 13");
        j.addProperty("Phone number", "02-9480322");
        j.addProperty("Contact", "Yonatan Dadon");
        j.addProperty("Shipping area", "East");

        DataController.AddSite(j);

        String s = "Address: Mevaseret-Zion 13, Name: Mevaseret-Zion, Shipping area: East, Type: Store.";

        Domain.Obejects.Site store = DataController.getSite(s);

        assertNotNull(store,  "Failed to add store");

        assertEquals("Store", store.getType(), "Incorrect site type!");
        assertEquals("Mevaseret-Zion", store.getName(), "Incorrect site name!");
        assertEquals("Mevaseret-Zion 13", store.getAddress(), "Incorrect site address!");
        assertEquals("02-9480322", store.getPhone(), "Incorrect site phone number!");
        assertEquals("Yonatan Dadon", store.getContact(), "Incorrect site contact!");
        assertEquals("East", store.getShipping_area(), "Incorrect site shipping area!");
    }
    @Test
    public void AddSupplierTest() throws SQLException {

        JsonObject j = new JsonObject();

        j.addProperty("Type", "Supplier");
        j.addProperty("Name", "Tnuva");
        j.addProperty("Address", "Maale-Edumim 33");
        j.addProperty("Phone number", "02-9813477");
        j.addProperty("Contact", "Gadi Varshevski");
        j.addProperty("Shipping area", "East");

        DataController.AddSite(j);

        String s = "Address: Maale-Edumim 33, Name: Tnuva, Shipping area: East, Type: Supplier.";

        Domain.Obejects.Site store = DataController.getSite(s);

        assertNotNull(store,  "Failed to add supplier");

        assertEquals("Supplier", store.getType(), "Incorrect site type!");
        assertEquals("Tnuva", store.getName(), "Incorrect site name!");
        assertEquals("Maale-Edumim 33", store.getAddress(), "Incorrect site address!");
        assertEquals("02-9813477", store.getPhone(), "Incorrect site phone number!");
        assertEquals("Gadi Varshevski", store.getContact(), "Incorrect site contact!");
        assertEquals("East", store.getShipping_area(), "Incorrect site shipping area!");
    }
    @Test
    public void CreateTransportTest() throws SQLException {

        JsonObject Transportation = new JsonObject();

        String truck = "Licence Number: 111-11-111, Licence Level: A, Max weight: 10000.0.";
        String driver = "Name: Daniel Levi, Licence Level: C.";
        String supplier = "Address: Tel-Aviv 32, Name: Tnuva, Shipping area: Center, Type: Supplier.";
        String store = "Address: Hertseliya 40, Name: Hertseliya, Shipping area: Center, Type: Store.";

        Transportation.addProperty("Source", "Tel Aviv");
        Transportation.addProperty("Date", "24/07");
        Transportation.addProperty("Leaving time", "09:00");
        Transportation.addProperty("Truck", truck);
        Transportation.addProperty("Driver", driver);

        TransportationController.createTransport(Transportation);

        JsonObject item1 = new JsonObject();
        JsonObject item2 = new JsonObject();

        item1.addProperty("Name", "milk");
        item1.addProperty("Weight", "1");
        item1.addProperty("Amount", "4000");

        TransportationController.AddItem(item1, "Supplier");

        item2.addProperty("Name", "eggs");
        item2.addProperty("Weight", "1");
        item2.addProperty("Amount", "2500");

        TransportationController.AddItem(item2, "Supplier");

        createDocument(supplier, "Supplier", "Center");

        JsonObject Items1 = new JsonObject();
        JsonObject Items2 = new JsonObject();

        String items1 = "Name: milk, Weight: 1.0";
        String items2 = "Name: eggs, Weight: 1.0";

        String amount1 = "4000";
        String amount2 = "2500";

        Items1.addProperty("Item", items1);
        Items1.addProperty("Amount", amount1);

        TransportationController.AddItem(Items1, "Store");

        Items2.addProperty("Item", items2);
        Items2.addProperty("Amount", amount2);

        TransportationController.AddItem(Items2, "Store");

        createDocument(store, "Store", "Center");

        TransportationController.checkTransport();

        Domain.Obejects.TransportDocument transport = DataController.getTransportDoc("1001");

        String d = "Date: 24/07\n" +
                "Number of Truck: 111-11-111\n" +
                "Maximum possible loading weight: 8000.0\n" +
                "Leaving time: 09:00\n" +
                "Driver name: Daniel Levi\n" +
                "Address of the Source: Tel Aviv\n" +
                "Targets: \n" +
                "        1. Collect products at Supplier: Tnuva\n" +
                "           - Address: Tel-Aviv 32\n" +
                "           - Contact name: Pinhas\n" +
                "           - Phone: 03-2938393\n" +
                "           - Shipping weight: 6500.0\n" +
                "\n" +
                "           - Items: \n" +
                "                 Name: milk, Amount: 4000\n" +
                "                 Name: eggs, Amount: 2500\n" +
                "\n" +
                "\n" +
                "        2. Dropped products at Store: Hertseliya\n" +
                "           - Address: Hertseliya 40\n" +
                "           - Contact name: Avi\n" +
                "           - Phone: 03-3333333\n" +
                "           - Shipping weight: 6500.0\n" +
                "\n" +
                "           - Items: \n" +
                "                 Name: milk, Amount: 4000\n" +
                "                 Name: eggs, Amount: 2500\n" +
                "\n" +
                "\n";

        assertNotNull(transport, "Failed to create transportation!");

        assertEquals(1001, transport.getID(), "Incorrect transportation ID");
        assertEquals("Pending", transport.getStatus(), "Incorrect transportation status");
        assertEquals(d, transport.getDetails(), "Incorrect transportation details");


    }
    @Test
    public void ShowAllTransportsTest() throws SQLException {

        String Transport = "Transport number: 1001\nStatus: Pending\nDate: 24/07\n" +
                "Number of Truck: 111-11-111\n" +
                "Maximum possible loading weight: 8000.0\n" +
                "Leaving time: 09:00\n" +
                "Driver name: Daniel Levi\n" +
                "Address of the Source: Tel Aviv\n" +
                "Targets: \n" +
                "        1. Collect products at Supplier: Tnuva\n" +
                "           - Address: Tel-Aviv 32\n" +
                "           - Contact name: Pinhas\n" +
                "           - Phone: 03-2938393\n" +
                "           - Shipping weight: 6500.0\n" +
                "\n" +
                "           - Items: \n" +
                "                 Name: eggs, Amount: 2500\n" +
                "                 Name: milk, Amount: 4000\n" +
                "\n" +
                "\n" +
                "        2. Dropped products at Store: Hertseliya\n" +
                "           - Address: Hertseliya 40\n" +
                "           - Contact name: Avi\n" +
                "           - Phone: 03-3333333\n" +
                "           - Shipping weight: 6500.0\n" +
                "\n" +
                "           - Items: \n" +
                "                 Name: eggs, Amount: 2500\n" +
                "                 Name: milk, Amount: 4000\n" +
                "\n" +
                "\n";

        JsonObject j = DataConnector.printAllTransports();

        assertEquals(j.get("1").getAsString(), Transport, "There is problem in the printing");

    }
}
