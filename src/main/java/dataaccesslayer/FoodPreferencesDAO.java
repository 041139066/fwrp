package dataaccesslayer;

import model.FoodPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing food preferences in the database.
 * Provides methods to add, delete, and retrieve food preferences for users.
 */
public class FoodPreferencesDAO {

    /**
     * Retrieves a list of food preferences for a given user ID.
     *
     * @param userId The ID of the user whose food preferences are to be retrieved.
     * @return A list of food inventory IDs representing the user's food preferences.
     */
    public List<Integer> getFoodPreferencesByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM foodpreferences WHERE consumer_id = ?")) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("food_inventory_id");
                        list.add(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Adds a new food preference for a user.
     *
     * @param foodPreference The FoodPreference object containing the user ID and food inventory ID.
     * @return The number of affected rows in the database.
     */
    public int addFoodPreference(FoodPreference foodPreference) {
        int affectedRows = 0;
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO FoodPreferences(consumer_id, food_inventory_id) VALUES (?, ?)")) {
                stmt.setInt(1, foodPreference.getConsumerId());
                stmt.setInt(2, foodPreference.getFoodInventoryId());
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /**
     * Deletes a food preference for a user.
     *
     * @param foodPreference The FoodPreference object containing the user ID and food inventory ID to be deleted.
     * @return The number of affected rows in the database.
     */
    public int deleteFoodPreference(FoodPreference foodPreference) {
        int affectedRows = 0;
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("DELETE FROM foodpreferences WHERE consumer_id = ? AND food_inventory_id = ?")) {
                stmt.setInt(1, foodPreference.getConsumerId());
                stmt.setInt(2, foodPreference.getFoodInventoryId());
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
}
