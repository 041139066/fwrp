package dataaccesslayer;

import model.AvailableFood;
import model.ClaimedFood;
import model.PurchasedFood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseFoodDAO {

    public void purchaseFood(Integer userId, Integer foodInventoryId, Integer need, Double cost) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Database.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Get the food inventory quantity
            ps = con.prepareStatement("SELECT quantity FROM FoodInventory WHERE id = ?");
            ps.setInt(1, foodInventoryId);
            rs = ps.executeQuery();
            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                if (currentQuantity >= need) {

                    // update food inventory quantity
                    ps = con.prepareStatement("UPDATE foodinventory SET quantity = quantity - ? WHERE id = ?");
                    ps.setInt(1, need);
                    ps.setInt(2, foodInventoryId);
                    ps.executeUpdate();

                    // add purchase record
                    ps = con.prepareStatement("INSERT INTO PurchasedFood (consumer_id, food_inventory_id, quantity, cost) VALUES (?, ?, ?, ?)");
                    ps.setInt(1, userId);
                    ps.setInt(2, foodInventoryId);
                    ps.setInt(3, need);
                    ps.setDouble(4, cost);
                    ps.executeUpdate();

                    con.commit(); // Commit transaction
                } else {
                    throw new RuntimeException("Insufficient inventory quantity.");
                }
            } else {
                throw new RuntimeException("Food inventory item not found.");
            }
        } catch (SQLException e) {
            try {
                con.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Error processing food claim", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.setAutoCommit(true); // Reset autocommit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PurchasedFood> getAllPurchasedFoodByConsumerId(int consumerId) {
        List<PurchasedFood> list = new ArrayList<>();
        String sql = "SELECT pf.id, food_inventory_id, name, consumer_id, pf.quantity, pf.cost, pf.purchase_date " +
                "FROM PurchasedFood AS pf JOIN FoodInventory AS fi ON pf.food_inventory_id = fi.id " +
                "WHERE pf.consumer_id = ? " +
                "ORDER BY pf.purchase_date DESC";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, consumerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        PurchasedFood item = new PurchasedFood();
                        item.setId(rs.getInt("id"));
                        item.setConsumerId(rs.getInt("consumer_id"));
                        item.setFoodInventoryId(rs.getInt("food_inventory_id"));
                        item.setFoodInventoryName(rs.getString("name"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setCost(rs.getDouble("cost"));
                        item.setPurchaseDate(rs.getTimestamp("purchase_date").toLocalDateTime());
                        list.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
