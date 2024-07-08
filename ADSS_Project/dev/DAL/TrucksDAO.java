package DAL;

import Domain.Obejects.Truck;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;


public class TrucksDAO implements IDAO<Truck>{

    private Connection connection;

    /**
     * TrucksDAO Constructor
     */
    public TrucksDAO(){
        this.connection = DB_Connector.getConnection();
    }

    /**
     * Return List of JsonObject that SELECT all the Trucks
     */
    @Override
    public List<JsonObject> SELECT_ALL()throws SQLException  {

        List<JsonObject> all_trucks = new ArrayList<>();

//        String sql = "SELECT Licence_Number, Licence_Level, Net_Weight, Max_Weight, Status FROM Trucks";

        String sql = "SELECT * FROM Trucks";

        PreparedStatement truck = connection.prepareStatement(sql);

        ResultSet rs = truck.executeQuery();

        while (rs.next()) {
            JsonObject j = new JsonObject();

            j.addProperty("Licence number", rs.getString("Licence_Number"));
            j.addProperty("Licence level", rs.getString("Licence_Level"));
            j.addProperty("Net weight", rs.getString("Net_Weight"));
            j.addProperty("Max weight", rs.getString("Max_Weight"));
            j.addProperty("Status", rs.getString("Status"));

            all_trucks.add(j);
        }

        return all_trucks;
    }

    /**
     * INSERT onto the DB new Truck
     */
    @Override
    public void INSERT(JsonObject j) throws SQLException {

        String sql = "INSERT INTO Trucks(Licence_number, Licence_Level, Net_Weight" +
                     ", Max_Weight, Status) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement truck = connection.prepareStatement(sql);

        truck.setString(1, j.get("Licence number").getAsString());
        truck.setString(2, j.get("Licence level").getAsString());
        truck.setString(3, j.get("Net weight").getAsString());
        truck.setString(4, j.get("Max weight").getAsString());
        truck.setString(5, j.get("Status").getAsString());

        truck.executeUpdate();
    }

    /**
     * Update a Truck in the DB
     */
    @Override
    public void UPDATE(JsonObject j)throws SQLException {

        String sql = "UPDATE Trucks SET Status = ? WHERE Licence_number = ?";

        PreparedStatement truck = connection.prepareStatement(sql);

        truck.setString(1, j.get("Status").getAsString());
        truck.setString(2, j.get("Licence number").getAsString());

        truck.executeUpdate();
    }

    /**
     * DELETE a Truck in the DB
     */
    @Override
    public void DELETE(JsonObject j)throws SQLException {

        String sql = "DELETE FROM Trucks WHERE Licence_number = ?";

        PreparedStatement truck = connection.prepareStatement(sql);

        truck.setString(1, j.get("Licence number").getAsString());
        truck.executeUpdate();
    }
}
