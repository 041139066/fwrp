package dataaccesslayer;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import model.FoodInventory;
import model.FoodStatus;

public class FoodInventoryDAO {

    public List<FoodInventory> getAllFoodInventory() {
        return getFoodInventoryList(null, false);
    }

    public List<FoodInventory> getAllFoodInventoryByRetailerId(int retailerId) {
        return getFoodInventoryList(retailerId, false);
    }

    public List<FoodInventory> getSurplusFoodInventoryByRetailerId(int retailerId) {
        return getFoodInventoryList(retailerId, true);
    }

    private List<FoodInventory> getFoodInventoryList(Integer id, boolean isSurplus) {
        List<FoodInventory> list = new ArrayList<>();
        String sql = id == null ? "SELECT * FROM FoodInventory" : isSurplus ? "SELECT * FROM FoodInventory WHERE expiration_date < NOW() + INTERVAL 7 DAY AND retailer_id = ?" : "SELECT * FROM FoodInventory WHERE retailer_id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                if (id != null) {
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

    public void addFoodInventory(FoodInventory item) {
        String sql = "INSERT INTO FoodInventory (name, price, expiration_date, quantity, status, retailer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, item.getName());
                stmt.setDouble(2, item.getPrice());
                stmt.setTimestamp(3, Timestamp.valueOf(item.getExpirationDate()));
                stmt.setInt(4, item.getQuantity());
                if (item.getStatus() == null) {
                    stmt.setNull(5, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(5, item.getStrStatus());
                }
                stmt.setInt(6, item.getRetailerId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
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
        String sql = "UPDATE FoodInventory SET name = ?, price= ?, expiration_date = ?, quantity = ?,  status = ? WHERE id = ?";
        try {
            Connection conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, item.getName());
                stmt.setDouble(2, item.getPrice());
                stmt.setTimestamp(3, Timestamp.valueOf(item.getExpirationDate()));
                stmt.setInt(4, item.getQuantity());
                if (item.getStatus() == null) {
                    stmt.setNull(5, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(5, item.getStrStatus());
                }
                stmt.setInt(6, item.getId());
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

    public void updateFoodInventoryStatus(int id, FoodStatus status) {
        String sql = "UPDATE FoodInventory SET status = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                if (status == null) {
                    stmt.setNull(1, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(1, status.name());
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

