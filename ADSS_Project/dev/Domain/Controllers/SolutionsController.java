package Domain.Controllers;

import Domain.Obejects.Document;
import Domain.Obejects.Driver;
import Domain.Obejects.Item;
import Domain.Obejects.Truck;

import com.google.gson.JsonObject;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Map;

import static Domain.Controllers.TransportationController.*;

public class SolutionsController {

    /**
     * @return JsonObject of all trucks that can make the Transport
     */
    public static JsonObject getAvailableTruck() throws SQLException {

        JsonObject new_json = new JsonObject();

        ArrayList<Truck> trucks = DataController.getAllTrucks();

        int count = 1;
        for (Truck tr : trucks) {
            if (tr.getStatus().equals("available") && (tr.getMax_weight() -
                tr.getNet_weight())>= Transport.get_transport_Max_weight()) {
                new_json.addProperty(String.valueOf(count), tr.to_String());
                count++;
            }
        }
        return new_json;
    }
    /**
     * @param a - String argument represent the Site to dropped items from
     * @return JsonObject of the Items to dropped
     */
    public static JsonObject getItem(String a) {

        JsonObject new_json = new JsonObject();
        int count = 1;
        for (Document d : Transport.getTargets()) {
            if (d.to_string().equals(a)) {
                for (Map.Entry<Item, Integer> iter : d.getAllItems().entrySet()) {
                    new_json.addProperty(String.valueOf(count++), (iter.getKey().to_string()));
                }
                break;
            }
        }
        return new_json;
    }
    /**
     * @return JsonObject represent the available Sites to dropped
     */
    public static JsonObject ChooseSupplierToDrop() {

        JsonObject j = new JsonObject();

        int count = 1;
        for (Document d : Transport.getTargets()) {
            if (d.getTarget().getType().equals("Supplier"))
                j.addProperty(String.valueOf(count++), d.to_string());
        }
        return j;
    }
    public static void replace_truck_and_driver(JsonObject t) throws SQLException {

        Truck truck = DataController.getTruck(t.get("Truck").getAsString());
        Driver driver = DataController.getDriver(t.get("Driver").getAsString());

        Transport.setTruck(truck);
        Transport.setDriver(driver);

    }
    /**
     * Replace Documents
     *
     * @param a - String represent of the Document to replace
     */
    public static void replace_documents(String a) {

        int count = 0;
        for (Document d : Transport.getTargets()) {
            if (d.to_string().equals(a)) {
                break;
            }
            count++;
        }
        drop_Site(a);

        Transport.getTargets().add(count, Transport.getTargets().
                  remove(Transport.getTargets().size() - 1));
    }
    /**
     * Dropped Items
     *
     * @param a - String represent of the Item to dropped
     */
    public static void drop_Items(String a, String b) {

        int count = 0;
        for (Document d : Transport.getTargets()) {
            if (d.to_string().equals(b)) {
                for (Map.Entry<Item, Integer> entry : d.getAllItems().entrySet()) {
                    if (entry.getKey().to_string().equals(a)) {
                        d.dropItem(entry.getKey());
                        Transport.getAll_transport_items().remove(entry.getKey());
                        if(d.getAllItems().isEmpty()){
                            Transport.getTargets().remove(d);
                        }
                        break;
                    }

                }

                for (int i = count; i < Transport.getTargets().size(); i++) {
                    if (Transport.getTargets().get(i).getTarget().getType().equals("Store")) {
                        for (Map.Entry<Item, Integer> entry : Transport.getTargets().get(i).getAllItems().entrySet()) {
                            if (entry.getKey().to_string().equals(a)) {
                                Transport.getTargets().get(i).dropItem(entry.getKey());
                                if(Transport.getTargets().get(i).getAllItems().isEmpty()){
                                    Transport.getTargets().remove(Transport.getTargets().get(i));
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            }
            count++;
        }

    }
    /**
     * Dropped Site from Database
     *
     * @param a - String argument represent the Site to dropped
     */
    public static void drop_Site(String a) {
        Document document = Transport.getTargets().get(0);
        int count = 0;
        for (Document d : Transport.getTargets()) {
            if (d.to_string().equals(a)) {
                document = d;
                Transport.getTargets().remove(d);
                break;
            }
            count++;
        }
        ArrayList<Integer> arr = new ArrayList<>();
        for (Map.Entry<Item, Integer> iter : document.getAllItems().entrySet()) {
            Transport.getAll_transport_items().remove(iter.getKey());
            for (int i = count; i < Transport.getTargets().size(); i++) {
                if (Transport.getTargets().get(i).getTarget().getType().equals("Store")) {

                    if(Transport.getTargets().get(i).getAllItems().containsKey(iter.getKey())){
                        Transport.getTargets().get(i).dropItem(iter.getKey());
                    }
                    if(Transport.getTargets().get(i).getAllItems().isEmpty()) {
                        arr.add(i);
                    }
                }
            }
        }

        if(arr.size() > 0) {
            for (int j = arr.size() - 1; j >= 0; j--) {
//                DataStructManager.remove_doc(arr.get(j));
                Transport.remove_Document(arr.get(j));

            }
        }

    }

}



