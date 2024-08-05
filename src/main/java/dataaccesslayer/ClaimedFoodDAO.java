package dataaccesslayer;

import model.ClaimedFood;
import model.DonationFoodVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimedFoodDAO {

    public List<DonationFoodVO> getAllDonationFood() {
        List<DonationFoodVO> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(
                "SELECT foodinventory.id, name AS description, price, quantity, status FROM foodinventory " +
                "WHERE status = 'donation'")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        DonationFoodVO item = new DonationFoodVO();
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

    public void claimFood(Integer id, Integer need, Integer userId) {
        Connection con = Database.getConnection();
        try {
            con.setAutoCommit(false); // Start transaction

            // 获取库存数量
            PreparedStatement ps = con.prepareStatement(
                "SELECT quantity FROM foodinventory WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                if (currentQuantity >= need) {
                    // 更新库存数量
                    PreparedStatement ps1 = con.prepareStatement(
                        "UPDATE foodinventory SET quantity = quantity - ? WHERE id = ?");
                    ps1.setInt(1, need);
                    ps1.setInt(2, id);
                    ps1.executeUpdate();

                    // 添加记录到claimedfood表
                    PreparedStatement ps3 = con.prepareStatement(
                        "INSERT INTO claimedfood (food_inventory_id, charitable_id, quantity, claim_date) VALUES (?, ?, ?, ?)");
                    ps3.setInt(1, id);
                    ps3.setInt(2, userId);
                    ps3.setInt(3, need);
                    ps3.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
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

    public List<ClaimedFood> getAllClaimedFood() {
        List<ClaimedFood> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(
                "SELECT food_inventory_id AS foodItemId, charitable_id AS charitableId, quantity, claim_date AS claimDate FROM claimedfood ORDER BY charitable_id")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ClaimedFood item = new ClaimedFood();
                        item.setFoodItemId(rs.getInt("foodItemId"));
                        item.setCharitableId(rs.getInt("charitableId"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setClaimDate(rs.getTimestamp("claimDate").toLocalDateTime());
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
