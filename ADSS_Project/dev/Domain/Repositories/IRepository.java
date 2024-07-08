package Domain.Repositories;


import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRepository <T> {
    public void Add(T t);
    public void Delete(T t);
    public T FindByID(String ID);
    public JsonObject ChooseAll(String s, String v);
    public void Update (JsonObject j) throws SQLException;
    public ArrayList<T> FindAll();
    public int getAmount();
}
