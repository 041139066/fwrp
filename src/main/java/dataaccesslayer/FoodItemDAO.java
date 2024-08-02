package dataaccesslayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.FoodItem;

/**
 *
 * @author yuexia Jin
 */
public class FoodItemDAO {

    Database db=new Database();


    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM FoodItems";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodItem item = new FoodItem(rs.getInt("item_id"), rs.getString("name"), rs.getInt("quantity"), rs.getDate("expiration_date").toLocalDate(), rs.getDouble("price"), rs.getString("status"));
                foodItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodItems;
    }

    public List<FoodItem> getAllSurplusFood() {
        List<FoodItem> surplusFoodItems = new ArrayList<>();
        String sql = "SELECT * FROM Items WHERE expiration_date <= CURRENT_DATE + INTERVAL '7' DAY";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodItem item = new FoodItem(rs.getInt("item_id"), rs.getString("name"), rs.getInt("quantity"), rs.getDate("expiration_date").toLocalDate(), rs.getDouble("price"), rs.getString("status"));
                surplusFoodItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surplusFoodItems;
    }

    public List<FoodItem> getAllFoodItemsByStatus(String status) {
        List<FoodItem> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM Items WHERE status = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FoodItem item = new FoodItem(rs.getInt("item_id"), rs.getString("name"), rs.getInt("quantity"), rs.getDate("expiration_date").toLocalDate(), rs.getDouble("price"), rs.getString("status"));
                    foodItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodItems;
    }

    public FoodItem getFoodItemById(int id) {
        FoodItem item = null;
        String sql = "SELECT * FROM Items WHERE item_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = new FoodItem(rs.getInt("item_id"), rs.getString("name"), rs.getInt("quantity"), rs.getDate("expiration_date").toLocalDate(), rs.getDouble("price"), rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public int createFoodItem(FoodItem inventory) {
        String sql = "INSERT INTO Items (name, quantity, expiration_date, price, status, added_date) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, inventory.getName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3, Date.valueOf(inventory.getExpirationDate()));
            pstmt.setDouble(4, inventory.getPrice());
            pstmt.setString(5, inventory.getStatus());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateFoodItem(FoodItem inventory) {
        String sql = "UPDATE Items SET name = ?, quantity = ?, expiration_date = ?, price = ?, status = ? WHERE item_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3,  Date.valueOf(inventory.getExpirationDate()));;
            pstmt.setDouble(4, inventory.getPrice());
            pstmt.setString(5, inventory.getStatus());
            pstmt.setInt(6, inventory.getId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateFoodItemExpirationDate(int id, Date date) {
        String sql = "UPDATE Items SET expiration_date = ? WHERE item_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            pstmt.setDate(1, sqlDate);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateFoodItemPrice(int id, double price) {
        String sql = "UPDATE Items SET price = ? WHERE item_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateFoodItemStatus(int id, String status) {
        String sql = "UPDATE Items SET status = ? WHERE item_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteFoodItem(int id) {
        String sql = "DELETE FROM Items WHERE item_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void notifyConsumers() {
        // Implementation for notifying consumers
        // This might involve sending emails or notifications to the consumers
    }
}

