package dataaccesslayer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.FoodInventory;

public class FoodInventoryDAO {

    public List<FoodInventory> getAllFoodInventory() {
        List<FoodInventory> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM foodinventory")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        FoodInventory item = new FoodInventory();
                        item.setId(rs.getInt("id"));
                        item.setDescription(rs.getString("description"));
                        item.setStandardPrice(rs.getDouble("standard_price"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setAverageRating(rs.getDouble("average_rating"));
                        item.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
                        list.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public FoodInventory getFoodInventoryById(int id) {
        FoodInventory inventory = null;
        String sql = "SELECT * FROM FoodInventories WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inventory = new FoodInventory();
                    inventory.setId(rs.getInt("id"));
                    inventory.setDescription(rs.getString("description"));
                    inventory.setQuantity(rs.getInt("quantity"));
                    inventory.setAverageRating(rs.getDouble("averageRating"));


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void createFoodInventory(FoodInventory inventory) {
        String sql = "INSERT INTO FoodInventories (name, quantity, rating) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getDescription());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDouble(3, inventory.getAverageRating());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventory(FoodInventory inventory) {
        String sql = "UPDATE FoodInventories SET name = ?, quantity = ?, rating = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getDescription());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDouble(3, inventory.getAverageRating());
            pstmt.setInt(4, inventory.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventoryQuantity(int id, int qty) {
        String sql = "UPDATE FoodInventories SET quantity = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, qty);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventoryRating(int id, double rating) {
        String sql = "UPDATE FoodInventories SET rating = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, rating);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

