package Domain.Repositories;

import Domain.Obejects.TransportDocument;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TransportsRepository implements IRepository<TransportDocument> {

    private static ArrayList<TransportDocument> AllTransportations =  new ArrayList<>();


    @Override
    public void Add(TransportDocument s) {
        AllTransportations.add(s);
    }
    @Override
    public void Delete(TransportDocument s) {
        AllTransportations.remove(s);
    }
    @Override
    public TransportDocument FindByID(String ID) {
        for (TransportDocument tran: AllTransportations){
            if (tran.getID() == Integer.parseInt(ID))
                return tran;
        }
        return null;
    }
    @Override
    public JsonObject ChooseAll(String s, String v) {
        JsonObject j = new JsonObject();
        int count = 1;
        for (TransportDocument t : AllTransportations){
            j.addProperty(String.valueOf(count++), t.to_String());
        }
        return j;
    }
    @Override
    public void Update(JsonObject j){

        int id = j.get("Transportation ID").getAsInt();
        String newStatus = j.get("Status").getAsString();

        for (TransportDocument t : AllTransportations){
            if (t.getID() == id)
                t.setStatus(newStatus);
        }
    }
    @Override
    public ArrayList<TransportDocument> FindAll() {
        return AllTransportations;
    }
    @Override
    public int getAmount() {
        return AllTransportations.size();
    }
}
