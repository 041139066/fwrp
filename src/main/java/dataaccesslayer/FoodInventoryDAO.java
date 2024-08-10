package dataaccesslayer;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.FoodInventory;

/**
 * Data Access Object (DAO) for managing food inventory in the database.
 * Provides methods to retrieve, add, update, and delete food inventory records.
 */
public class FoodInventoryDAO {

    /**
     * Retrieves all food inventory records from the database.
     *
     * @return A list of all food inventory records.
     */
    public List<FoodInventory> getAllFoodInventory() {
        return getFoodInventoryList(0, 0);
    }

    /**
     * Retrieves all food inventory records for a specific retailer.
     *
     * @param retailerId The ID of the retailer whose food inventory is to be retrieved.
     * @return A list of food inventory records for the specified retailer.
     */
    public List<FoodInventory> getAllFoodInventoryByRetailerId(int retailerId) {
        return getFoodInventoryList(1, retailerId);
    }

    /**
     * Retrieves surplus food inventory records for a specific retailer.
     * Surplus items are those with expiration dates within the next 7 days.
     *
     * @param retailerId The ID of the retailer whose surplus food inventory is to be retrieved.
     * @return A list of surplus food inventory records for the specified retailer.
     */
    public List<FoodInventory> getSurplusFoodInventoryByRetailerId(int retailerId) {
        return getFoodInventoryList(2, retailerId);
    }

    /**
     * Retrieves all food inventory records available for donation.
     *
     * @return A list of food inventory records available for donation.
     */
    public List<FoodInventory> getAllFoodInventoryForDonation() {
        return getFoodInventoryList(3, 0);
    }

    /**
     * Retrieves all food inventory records available for sale.
     *
     * @return A list of food inventory records available for sale.
     */
    public List<FoodInventory> getAllFoodInventoryForSale() {
        return getFoodInventoryList(4, 0);
    }

    /**
     * Retrieves food inventory records based on the specified type and ID.
     *
     * @param type The type of query to execute.
     * @param id The ID to be used in the query (if applicable).
     * @return A list of food inventory records based on the query type and ID.
     */
    private List<FoodInventory> getFoodInventoryList(int type, int id) {
        String[] sqls = {
                "SELECT * FROM FoodInventory ORDER BY name", // all food inventory records
                "SELECT * FROM FoodInventory WHERE retailer_id = ? ORDER BY name", // all food inventory records of the retailer
                "SELECT * FROM FoodInventory WHERE expiration_date < NOW() + INTERVAL 7 DAY AND retailer_id = ? ORDER BY name", // all food inventory records of the retailer that are surplus, expiration date within 7 days from now
                "SELECT * FROM FoodInventory WHERE status = 'donation' ORDER BY name",
                "SELECT * FROM FoodInventory WHERE status IS NULL OR status != 'donation' ORDER BY name",
        };
        List<FoodInventory> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sqls[type])) {
                if (id != 0) {
                    stmt.setInt(1, id);
                }
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(makeFoodInventory(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves all food inventory records based on the location (city and province) of the retailer.
     *
     * @param city The city of the retailer.
     * @param province The province of the retailer.
     * @return A list of food inventory records based on the retailer's location.
     */
    public List<FoodInventory> getAllFoodInventoryByLocation(String city, String province) {
        String sql =
                "SELECT * FROM FoodInventory AS fi JOIN Users AS u ON fi.retailer_id = u.id WHERE u.city = ? AND u.province = ? ORDER BY fi.name";
        List<FoodInventory> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, city);
                stmt.setString(2, province);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(makeFoodInventory(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves a specific food inventory record by its ID.
     *
     * @param id The ID of the food inventory record to retrieve.
     * @return The food inventory record with the specified ID, or null if not found.
     */
    public FoodInventory getFoodInventoryById(int id) {
        FoodInventory foodInventory = null;
        String sql = "SELECT * FROM FoodInventory WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        foodInventory = makeFoodInventory(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return foodInventory;
    }

    /**
     * Creates a FoodInventory object from a ResultSet.
     *
     * @param rs The ResultSet containing the food inventory data.
     * @return A FoodInventory object populated with data from the ResultSet.
     * @throws SQLException If an SQL error occurs.
     */
    private FoodInventory makeFoodInventory(ResultSet rs) throws SQLException {
        FoodInventory foodInventory = new FoodInventory();
        foodInventory.setId(rs.getInt("id"));
        foodInventory.setName(rs.getString("name"));
        foodInventory.setPrice(rs.getDouble("price"));
        foodInventory.setExpirationDate(rs.getTimestamp("expiration_date").toLocalDateTime());
        foodInventory.setQuantity(rs.getInt("quantity"));
        foodInventory.setAverageRating(rs.getDouble("average_rating"));
        foodInventory.setStatus(rs.getString("status"));
        foodInventory.setRetailerId(rs.getInt("retailer_id"));
        return foodInventory;
    }

    /**
     * Adds a new food inventory record to the database.
     *
     * @param item The FoodInventory object containing the details of the food inventory to add.
     * @throws SQLException If an SQL error occurs.
     */
    public void addFoodInventory(FoodInventory item) throws SQLException {
        String sql = "INSERT INTO FoodInventory (name, price, expiration_date, quantity, retailer_id) VALUES (?, ?, ?, ?, ?)";
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setTimestamp(3, Timestamp.valueOf(item.getExpirationDate()));
            stmt.setInt(4, item.getQuantity());
            stmt.setInt(5, item.getRetailerId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a food inventory record from the database by its ID.
     *
     * @param id The ID of the food inventory record to delete.
     */
    public void deleteFoodInventory(int id) {
        String sql = "DELETE FROM FoodInventory WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing food inventory record in the database.
     *
     * @param item The FoodInventory object containing the updated details.
     */
    public void updateFoodInventory(FoodInventory item) {
        String sql = "UPDATE FoodInventory SET name = ?, price = ?, expiration_date = ?, quantity = ? WHERE id = ?";
        try {
            Connection conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, item.getName());
                stmt.setDouble(2, item.getPrice());
                stmt.setTimestamp(3, Timestamp.valueOf(item.getExpirationDate()));
                stmt.setInt(4, item.getQuantity());
                stmt.setInt(5, item.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the quantity of a specific food inventory record.
     *
     * @param id The ID of the food inventory record to update.
     * @param qty The new quantity to set.
     */
    public void updateFoodInventoryQuantity(int id, int qty) {
        String sql = "UPDATE FoodInventory SET quantity = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, qty);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the average rating of a specific food inventory record.
     *
     * @param id The ID of the food inventory record to update.
     * @param rating The new average rating to set.
     */
    public void updateFoodInventoryAverageRating(int id, double rating) {
        String sql = "UPDATE FoodInventory SET average_rating = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setDouble(1, rating);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the status of a specific food inventory record.
     *
     * @param id The ID of the food inventory record to update.
     * @param status The new status to set.
     */
    public void updateFoodInventoryStatus(int id, String status) {
        String sql = "UPDATE FoodInventory SET status = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                if (status == null || status.isEmpty()) {
                    stmt.setNull(1, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(1, status);
                }
                stmt.setInt(2, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
