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
        String sql="SELECT * FROM FoodInventory";
        try {
            Connection con = Database.getConnection();
            System.out.println("Database connection established.");
            try (PreparedStatement stmt = con.prepareStatement(sql))
                    //("SELECT * FROM FoodInventory"))
            {System.out.println("Executing query: " + sql);

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
            System.out.println("Query executed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public FoodInventory getFoodInventoryById(int id) {
        FoodInventory inventory = null;
        String sql = "SELECT * FROM FoodInventory WHERE id = ?";
       // String sql = "SELECT * FROM fwrp.foodinventory WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inventory = new FoodInventory();
                    inventory.setId(rs.getInt("id"));
                    inventory.setDescription(rs.getString("description"));
                    inventory.setQuantity(rs.getInt("quantity"));
                    inventory.setAverageRating(rs.getDouble("averageRating"));
                    inventory.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void addFoodInventory(FoodInventory item) {
        String sql = "INSERT INTO FoodInventory (description, standard_price, quantity, average_rating, last_modified) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, item.getDescription());
            stmt.setDouble(2, item.getStandardPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getAverageRating());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(item.getLastModified()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFoodInventory(FoodInventory inventory) {
        String sql = "INSERT INTO FoodInventory (description, quantity, foodinventory.average_rating, standard_price) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getDescription());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDouble(3, inventory.getAverageRating());
            pstmt.setDouble(4, inventory.getStandardPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventory(FoodInventory inventory) {
        String sql = "UPDATE FoodInventory SET description = ?, quantity = ?, average_rating = ? WHERE id = ?";
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
        String sql = "UPDATE FoodInventory SET quantity = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, qty);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventoryRating(int id, double rating) {
        String sql = "UPDATE FoodInventory SET average_rating = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, rating);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteFoodInventory(int id) {
        String sql = "DELETE FROM FoodInventory WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

