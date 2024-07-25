package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.City;
import model.User;
import model.UserType;

public class CityDAO {
    public List<City> getAllCities() {
        List<City> list = new ArrayList<City>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM cities")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        City city = new City();
                        city.setCity(rs.getString("city"));
                        city.setProvinceId(rs.getString("province_id"));
                        list.add(city);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<City> getCitiesByProvinceId(String provinceId) {
        List<City> list = new ArrayList<City>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM cities WHERE province_id = ?")) {
                stmt.setString(1, provinceId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapRowToCity(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private City mapRowToCity(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setCity(resultSet.getString("city"));
        city.setProvinceId(resultSet.getString("province_id"));
        return city;
    }
}
