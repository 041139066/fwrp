package dataaccesslayer;

import model.FoodInventory;
import model.FoodPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodPreferencesDAO {
    public List<Integer> getFoodPreferencesByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM foodpreferences WHERE consumer_id = ?")) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        var id = rs.getInt("food_inventory_id");
                        list.add(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

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
