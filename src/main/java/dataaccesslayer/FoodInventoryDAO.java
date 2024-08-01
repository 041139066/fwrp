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
                        FoodInventory inventory = makeFoodInventory(rs);
                        list.add(inventory);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public FoodInventory getFoodInventoryById(int foodInventoryId) {
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM FoodInventory WHERE id = ?")) {
                stmt.setInt(1, foodInventoryId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return makeFoodInventory(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FoodInventory makeFoodInventory(ResultSet rs) throws SQLException{
        FoodInventory inventory = new FoodInventory();
        inventory.setId(rs.getInt("id"));
        inventory.setDescription(rs.getString("description"));
        inventory.setStandardPrice(rs.getDouble("standard_price"));
        inventory.setQuantity(rs.getInt("quantity"));
        inventory.setAverageRating(rs.getDouble("average_rating"));
        inventory.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
        return inventory;
    }

    public int updateAverageRating(int id, double averageRating) {
        int affectedRows = 0;
        String sql =  "UPDATE Foodinventory SET average_rating = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setDouble(1, averageRating);
                stmt.setInt(2, id);
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
}

