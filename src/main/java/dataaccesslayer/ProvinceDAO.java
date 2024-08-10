package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Province;

/**
 * Data Access Object (DAO) for managing province operations in the database.
 * Provides methods to retrieve information about provinces.
 */
public class ProvinceDAO {

    /**
     * Retrieves all provinces from the database.
     *
     * @return A list of Province objects representing all provinces in the database.
     */
    public List<Province> getAllProvinces() {
        List<Province> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM provinces")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Province province = new Province();
                        province.setId(rs.getString("id"));
                        province.setProvince(rs.getString("province"));
                        list.add(province);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
