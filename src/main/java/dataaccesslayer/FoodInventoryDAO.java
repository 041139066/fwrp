package dataaccesslayer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.FoodInventory;
import model.constants.FoodStatus;

public class FoodInventoryDAO {

    public List<FoodInventory> getAllFoodInventory() {
        return getFoodInventoryList(0, 0);
    }

    public List<FoodInventory> getAllFoodInventoryByRetailerId(int retailerId) {
        return getFoodInventoryList(1, retailerId);
    }

    public List<FoodInventory> getSurplusFoodInventoryByRetailerId(int retailerId) {
        return getFoodInventoryList(2, retailerId);
    }

    public List<FoodInventory> getAllFoodInventoryForDonation() {
        return getFoodInventoryList(3, 0);
    }

    public List<FoodInventory> getAllFoodInventoryForSale() {
        return getFoodInventoryList(4, 0);
    }

    private List<FoodInventory> getFoodInventoryList(int type, int id) {
        String[] sqls = {
                "SELECT * FROM FoodInventory", // all food inventory records
                "SELECT * FROM FoodInventory WHERE retailer_id = ?", // all food inventory records of the retailer
                "SELECT * FROM FoodInventory WHERE expiration_date < NOW() + INTERVAL 7 DAY AND retailer_id = ?", // all food inventory records of the retailer that are surplus, expiration date within 7 days form now
                "SELECT * FROM FoodInventory WHERE status = 'donation'",
                "SELECT * FROM FoodInventory WHERE status IS NULL || status != 'donation'",
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

    public List<FoodInventory> getAllFoodInventoryByLocation(String city, String province) {
        String sql =
                "SELECT * FROM FoodInventory AS fi JOIN Users AS u ON fi.retailer_id = u.id WHERE u.city = ? AND u.province = ?";
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

    public void addFoodInventory(FoodInventory item) throws SQLException {
        String sql = "INSERT INTO FoodInventory (name, price, expiration_date, quantity, retailer_id) VALUES (?, ?, ?, ?, ?)";

        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setTimestamp(3, Timestamp.valueOf(item.getExpirationDate()));
            stmt.setInt(4, item.getQuantity());
//                if (item.getStatus() == null) {
//                    stmt.setNull(5, java.sql.Types.VARCHAR);
//                } else {
//                    stmt.setString(5, item.getStrStatus());
//                }
            stmt.setInt(5, item.getRetailerId());
            stmt.executeUpdate();
        }

    }

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

    public void updateFoodInventory(FoodInventory item) {
        String sql = "UPDATE FoodInventory SET name = ?, price= ?, expiration_date = ?, quantity = ? WHERE id = ?";
        try {
            Connection conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, item.getName());
                stmt.setDouble(2, item.getPrice());
                stmt.setTimestamp(3, Timestamp.valueOf(item.getExpirationDate()));
                stmt.setInt(4, item.getQuantity());
//                if (item.getStatus() == null) {
//                    stmt.setNull(5, java.sql.Types.VARCHAR);
//                } else {
//                    stmt.setString(5, item.getStrStatus());
//                }
                stmt.setInt(5, item.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

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

