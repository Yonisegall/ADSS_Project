package Domain.Repositories;

import Domain.Obejects.Truck;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TrucksRepository implements IRepository<Truck> {

    private static ArrayList<Truck> AllTrucks = new ArrayList<>();


    @Override
    public void Add(Truck truck) {
        AllTrucks.add(truck);
    }
    @Override
    public void Delete(Truck truck) {
        AllTrucks.remove(truck);
    }
    @Override
    public Truck FindByID(String ID) {
        for (Truck t: AllTrucks){
            if (t.to_String().equals(ID))
                return t;
        }
        return null;
    }
    @Override
    public JsonObject ChooseAll(String s, String v) {
        JsonObject j = new JsonObject();
        int count = 1;
        for (Truck t: AllTrucks){
            if (t.getStatus().equals("available")){
                j.addProperty(String.valueOf(count), t.to_String());
                count++;
            }
        }
        return j;
    }
    @Override
    public void Update(JsonObject j){

        String LicenceNumber = j.get("Licence number").getAsString();
        String newStatus = j.get("Status").getAsString();

        for (Truck t : AllTrucks){
            if (t.getLicence_number().equals(LicenceNumber))
                t.setStatus(newStatus);
        }
    }
    @Override
    public ArrayList<Truck> FindAll() {
        return AllTrucks;
    }
    @Override
    public int getAmount() {
        return AllTrucks.size();
    }
}
