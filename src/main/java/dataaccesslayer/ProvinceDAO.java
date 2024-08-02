package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Province;

public class ProvinceDAO {
    public List<Province> getAllProvinces() {
        List<Province> list = new ArrayList<Province>();
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

