package DAL;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.*;

public interface IDAO<T> {

    List<JsonObject> SELECT_ALL()throws SQLException;
    void INSERT(JsonObject j) throws SQLException;
    void UPDATE(JsonObject j) throws SQLException;
    void DELETE(JsonObject j) throws SQLException;
}

