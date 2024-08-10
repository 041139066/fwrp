package dataaccesslayer;

import model.ClaimedFood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing claimed food records.
 * Provides methods to claim food and retrieve claimed food records.
 */
public class ClaimedFoodDAO {

    /**
     * Claims a specified amount of food from the inventory for a user.
     * Updates the inventory quantity and records the claim in the database.
     *
     * @param foodInventoryId The ID of the food inventory item being claimed.
     * @param need The quantity of food being claimed.
     * @param userId The ID of the user claiming the food.
     * @throws RuntimeException if there is an error processing the claim or if the inventory is insufficient.
     */
    public void claimFood(Integer foodInventoryId, Integer need, Integer userId) {
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

                    // Add claim record
                    ps = con.prepareStatement("INSERT INTO ClaimedFood (food_inventory_id, charitable_id, quantity) VALUES (?, ?, ?)");
                    ps.setInt(1, foodInventoryId);
                    ps.setInt(2, userId);
                    ps.setInt(3, need);
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

    /**
     * Retrieves all claimed food records for a specified charitable organization.
     *
     * @param charitableId The ID of the charitable organization whose claimed food records are to be retrieved.
     * @return A list of ClaimedFood objects representing the claimed food records.
     */
    public List<ClaimedFood> getAllClaimedFoodByCharitableId(int charitableId) {
        List<ClaimedFood> list = new ArrayList<>();
        String sql = "SELECT cf.id, food_inventory_id, name, charitable_id, cf.quantity, claim_date " +
                "FROM ClaimedFood AS cf JOIN FoodInventory AS fi ON cf.food_inventory_id = fi.id " +
                "WHERE cf.charitable_id = ? " +
                "ORDER BY cf.claim_date DESC";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, charitableId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ClaimedFood item = new ClaimedFood();
                        item.setId(rs.getInt("id"));
                        item.setCharitableId(rs.getInt("charitable_id"));
                        item.setFoodInventoryId(rs.getInt("food_inventory_id"));
                        item.setFoodInventoryName(rs.getString("name"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setClaimDate(rs.getTimestamp("claim_date").toLocalDateTime());
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
