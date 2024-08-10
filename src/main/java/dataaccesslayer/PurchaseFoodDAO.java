package dataaccesslayer;

import model.AvailableFood;
import model.ClaimedFood;
import model.PurchasedFood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing food purchase operations in the database.
 * Provides methods to handle food purchases and retrieve purchased food records.
 */
public class PurchaseFoodDAO {

    /**
     * Processes the purchase of food by updating the inventory and adding a purchase record.
     *
     * @param userId The unique identifier of the user making the purchase.
     * @param foodInventoryId The unique identifier of the food inventory item being purchased.
     * @param need The quantity of food being purchased.
     * @param cost The total cost of the purchase.
     * @throws RuntimeException If there is an error processing the purchase or if inventory is insufficient.
     */
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

                    // Update food inventory quantity
                    ps = con.prepareStatement("UPDATE FoodInventory SET quantity = quantity - ? WHERE id = ?");
                    ps.setInt(1, need);
                    ps.setInt(2, foodInventoryId);
                    ps.executeUpdate();

                    // Add purchase record
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
                if (con != null) {
                    con.rollback(); // Rollback transaction on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Error processing food purchase", e);
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

    /**
     * Retrieves all purchased food records for a specific consumer.
     *
     * @param consumerId The unique identifier of the consumer.
     * @return A list of PurchasedFood objects representing all purchased food records for the specified consumer.
     */
    public List<PurchasedFood> getAllPurchasedFoodByConsumerId(int consumerId) {
        List<PurchasedFood> list = new ArrayList<>();
        String sql = "SELECT pf.id, pf.food_inventory_id, fi.name, pf.consumer_id, pf.quantity, pf.cost, pf.purchase_date " +
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
