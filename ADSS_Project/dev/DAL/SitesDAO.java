package DAL;

import Domain.Obejects.Site;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitesDAO implements IDAO<Site>{
    private Connection connection;

    /**
     * SitesDAO Constructor
     */
    public SitesDAO(){
        this.connection = DB_Connector.getConnection();
    }

    /**
     * Return List of JsonObject that SELECT all the Sites
     */
    @Override
    public List<JsonObject> SELECT_ALL()throws SQLException  {

        List<JsonObject> all_sites = new ArrayList<>();

//        String sql = "SELECT Name, Address, Phone_number, Contact, " +
//                     "Shipping_area, Type, Site_ID FROM Sites";

        String sql = "SELECT * FROM Sites";


        PreparedStatement site = connection.prepareStatement(sql);

        ResultSet rs = site.executeQuery();

        while (rs.next()) {
            JsonObject j = new JsonObject();

            j.addProperty("Name", rs.getString("Name"));
            j.addProperty("Address", rs.getString("Address"));
            j.addProperty("Phone number", rs.getString("Phone_number"));
            j.addProperty("Contact", rs.getString("Contact"));
            j.addProperty("Shipping area", rs.getString("Shipping_area"));
            j.addProperty("Type", rs.getString("Type"));
            j.addProperty("Site ID", rs.getString("Site_ID"));

            all_sites.add(j);
        }

        return all_sites;
    }

    /**
     * INSERT onto the DB new Site
     */
    @Override
    public void INSERT(JsonObject j) throws SQLException {

        String sql = "INSERT INTO Sites(Name, Address, Phone_number, Contact, " +
                     "Shipping_area, Type, Site_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement site = connection.prepareStatement(sql);

        site.setString(1, j.get("Name").getAsString());
        site.setString(2, j.get("Address").getAsString());
        site.setString(3, j.get("Phone number").getAsString());
        site.setString(4, j.get("Contact").getAsString());
        site.setString(5, j.get("Shipping area").getAsString());
        site.setString(6, j.get("Type").getAsString());
        site.setInt(7, j.get("Site ID").getAsInt());

        site.executeUpdate();
    }


    @Override
    public void UPDATE(JsonObject j)throws SQLException {
    }

    /**
     * DELETE a Site in the DB
     */
    @Override
    public void DELETE(JsonObject j)throws SQLException {

        String sql = "DELETE FROM Sites WHERE Site_ID = ?";

        PreparedStatement site = connection.prepareStatement(sql);

        site.setInt(1, j.get("Site ID").getAsInt());
        site.executeUpdate();
    }
}
