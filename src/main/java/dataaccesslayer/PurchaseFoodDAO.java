package dataaccesslayer;

import model.AvailableFood;
import model.PurchasedFood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseFoodDAO {

    public List<AvailableFood> getAllAvailableFood() {
        List<AvailableFood> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(
                "SELECT fooditems.id, description, price, quantity, status FROM fooditems " +
                "JOIN foodinventory ON fooditems.food_inventory_id = foodinventory.id " +
                "WHERE fooditems.status = 'stock' OR fooditems.status = 'sale'")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        AvailableFood item = new AvailableFood();
                        item.setId(rs.getInt("id"));
                        item.setDescription(rs.getString("description"));
                        item.setPrice(rs.getDouble("price"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setStatus(rs.getString("status"));
                        list.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void purchaseFood(Integer id, Integer need, Integer userId) {
        Connection con = Database.getConnection();
        try {
            con.setAutoCommit(false); // Start transaction

            // 获取库存数量
            PreparedStatement ps = con.prepareStatement(
                "SELECT quantity FROM foodinventory " +
                "WHERE id = (SELECT food_inventory_id FROM fooditems WHERE fooditems.id = ?)");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                if (currentQuantity >= need) {
                    // 更新库存数量
                    PreparedStatement ps1 = con.prepareStatement(
                        "UPDATE foodinventory SET quantity = quantity - ? " +
                        "WHERE id = (SELECT food_inventory_id FROM fooditems WHERE fooditems.id = ?)");
                    ps1.setInt(1, need);
                    ps1.setInt(2, id);
                    ps1.executeUpdate();
                    
                    // 使用传入的用户ID
                    int newConId = userId;

                    // 添加记录到claimedfood表
                    PreparedStatement ps3 = con.prepareStatement(
                        "INSERT INTO purchasedFood (food_item_id, consumer_id, purchase_date) VALUES (?, ?, ?)");
                    ps3.setInt(1, id);
                    ps3.setInt(2, newConId);
                    ps3.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    ps3.executeUpdate();

                    con.commit(); // Commit transaction
                } else {
                    throw new RuntimeException("Insufficient inventory quantity.");
                }
            }
        } catch (SQLException e) {
            try {
                con.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true); // Reset autocommit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<PurchasedFood> getAllPurchasedFood() {
        List<PurchasedFood> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(
                "SELECT food_item_id, consumer_id, purchase_date FROM purchasedfood ORDER BY purchase_date")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        PurchasedFood item = new PurchasedFood();
                        item.setFoodItemId(rs.getInt("food_item_id"));
                        item.setConsumerId(rs.getInt("consumer_id"));
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
