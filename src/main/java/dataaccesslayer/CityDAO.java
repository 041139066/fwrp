package dataaccesslayer;

import model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing city records.
 * Provides methods to retrieve city records from the database.
 */
public class CityDAO {

    /**
     * Retrieves all city records from the database.
     *
     * @return A list of City objects representing all cities.
     */
    public List<City> getAllCities() {
        List<City> list = new ArrayList<>();
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

    /**
     * Retrieves city records by the province ID.
     *
     * @param provinceId The ID of the province to filter cities by.
     * @return A list of City objects representing cities in the specified province.
     */
    public List<City> getCitiesByProvinceId(String provinceId) {
        List<City> list = new ArrayList<>();
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

    /**
     * Retrieves a city record by city and province IDs.
     *
     * @param cityId The ID of the city to retrieve.
     * @param provinceId The ID of the province the city belongs to.
     * @return A City object representing the specified city, or null if not found.
     */
    public City getCityByIds(String cityId, String provinceId) {
        City city = null;
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM cities WHERE city = ? AND province_id = ? ")) {
                stmt.setString(1, cityId);
                stmt.setString(2, provinceId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        city = mapRowToCity(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * Maps a ResultSet row to a City object.
     *
     * @param resultSet The ResultSet containing city data.
     * @return A City object mapped from the ResultSet.
     * @throws SQLException If an SQL error occurs while accessing the ResultSet.
     */
    private City mapRowToCity(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setCity(resultSet.getString("city"));
        city.setProvinceId(resultSet.getString("province_id"));
        return city;
    }
}
