package dataaccesslayer;


import model.ClaimedFood;
import model.DonationFoodVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClaimedFoodDAO {

    public List<DonationFoodVO> getAllDonationFood() {
        List<DonationFoodVO> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT fooditems.id,description,price,quantity,status FROM fooditems,foodinventory " +
                    "where fooditems.food_inventory_id = foodinventory.id and fooditems.status='donation'")) {
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
            PreparedStatement ps = con.prepareStatement("select quantity from foodinventory " +
                    "where foodinventory.id=(select food_inventory_id from fooditems where fooditems.id = ?)");
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int quantity = rs.getInt("quantity");
            if (need <= quantity) {
                PreparedStatement ps1 = con.prepareStatement("update foodinventory set quantity = quantity - ? " +
                        "where id = (select food_inventory_id from fooditems where fooditems.id = ?)");
                ps1.setInt(1, need);
                ps1.setInt(2, id);
                ps1.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement("INSERT INTO claimedfood(charitable_id,food_item_id,claim_date)" +
                        "values (?,?,?)");
                ps2.setInt(1, id);
                ps2.setInt(2, userId);
                ps2.setObject(3, new Date(new java.util.Date().getTime()));
                ps2.execute();
            } else {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ClaimedFood> myClaimFood(Integer userId) {
        List<ClaimedFood> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT fooditems.id,description,price,claim_date FROM fooditems,foodinventory,claimedfood " +
                    "where fooditems.food_inventory_id = foodinventory.id and claimedfood.food_item_id=fooditems.id and fooditems.status='donation' and charitable_id = ?");
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClaimedFood item = new ClaimedFood();
                    item.setId(rs.getInt("id"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(rs.getDouble("price"));
                    item.setClaimDate(rs.getTimestamp("claim_date").toLocalDateTime());
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

