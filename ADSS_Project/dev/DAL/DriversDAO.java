package DAL;

import Domain.Obejects.Driver;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriversDAO implements IDAO<Driver>{
    private Connection connection;

    /**
     * DriversDAO Constructor
     */
    public DriversDAO(){
        this.connection = DB_Connector.getConnection();
    }

    /**
     * Return List of JsonObject that SELECT all the Drivers
     */
    @Override
    public List<JsonObject> SELECT_ALL()throws SQLException {

        List<JsonObject> all_drivers = new ArrayList<>();

        String sql = "SELECT * FROM Drivers";

        PreparedStatement driver = connection.prepareStatement(sql);

        ResultSet rs = driver.executeQuery();

        while (rs.next()) {
            JsonObject j = new JsonObject();

            j.addProperty("Name", rs.getString("Name"));
            j.addProperty("Licence", rs.getString("Licence"));
            j.addProperty("Password", rs.getString("Password"));
            j.addProperty("Status", rs.getString("Status"));
            j.addProperty("Route", rs.getString("Route"));
            j.addProperty("Transport ID", rs.getString("Transport_ID"));
            j.addProperty("Truck Licence Number", rs.getString("Truck_Licence_Number"));
            j.addProperty("Driver ID", rs.getString("Driver_ID"));

            all_drivers.add(j);
        }

        return all_drivers;
    }

    /**
     * INSERT onto the DB new Driver
     */
    @Override
    public void INSERT(JsonObject j) throws SQLException {
        String sql = "INSERT INTO Drivers(Name, Licence, Password, Status, Route, " +
                     "Transport_ID, Truck_Licence_Number, Driver_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement driver = connection.prepareStatement(sql);

        driver.setString(1, j.get("Name").getAsString());
        driver.setString(2, j.get("Licence").getAsString());
        driver.setString(3, j.get("Password").getAsString());
        driver.setString(4, j.get("Status").getAsString());
        driver.setString(5, j.get("Route").getAsString());
        driver.setInt(6, j.get("Transport ID").getAsInt());
        driver.setString(7, j.get("Truck Licence Number").getAsString());
        driver.setInt(8, j.get("Driver ID").getAsInt());

        driver.executeUpdate();
    }

    /**
     * Update a Driver in the DB
     */
    @Override
    public void UPDATE(JsonObject j)throws SQLException {

        String sql = "UPDATE Drivers SET Status = ?, Route = ?, Transport_ID = ?, " +
                     "Truck_Licence_Number = ? WHERE Driver_ID = ?";

        PreparedStatement driver = connection.prepareStatement(sql);

        driver.setString(1, j.get("Status").getAsString());
        driver.setString(2, j.get("Route").getAsString());
        driver.setString(3, j.get("Transport ID").getAsString());
        driver.setString(4, j.get("Truck Licence Number").getAsString());
        driver.setString(5, j.get("Driver ID").getAsString());

        driver.executeUpdate();
    }

    /**
     * DELETE a Driver in the DB
     */
    @Override
    public void DELETE(JsonObject j)throws SQLException {

        String sql = "DELETE FROM Drivers WHERE Driver_ID = ?";

        PreparedStatement driver = connection.prepareStatement(sql);

        driver.setInt(1, j.get("Driver ID").getAsInt());
        driver.executeUpdate();
    }
}
