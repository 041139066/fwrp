package dataaccesslayer;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import model.FoodInventory;

public class FoodInventoryDAO {

    public List<FoodInventory> getAllFoodInventory() {
        List<FoodInventory> list = new ArrayList<>();
        String sql="SELECT * FROM FoodInventory";
        try {
            Connection con = Database.getConnection();
            System.out.println("Database connection established.");
            try (PreparedStatement stmt = con.prepareStatement(sql))
                    //("SELECT * FROM FoodInventory"))
            {System.out.println("Executing query: " + sql);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        FoodInventory item = new FoodInventory(rs.getInt("id"),  // Assuming your constructor includes id
                                rs.getString("description"),
                                rs.getDouble("standard_price"),
                                rs.getInt("quantity"),
                                rs.getDouble("average_rating"),
                                rs.getTimestamp("expirationDate").toLocalDateTime(),
                                rs.getBoolean("is_surplus"),
                                rs.getBoolean("isForDonation"),
                                rs.getBoolean("isForSale"));

                       list.add(item);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public FoodInventory getFoodInventoryById(int id) {
        FoodInventory inventory = null;
        String sql = "SELECT * FROM FoodInventory WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inventory = new FoodInventory(rs.getInt("id"),  // Assuming your constructor includes id
                            rs.getString("description"),
                            rs.getDouble("standard_price"),
                            rs.getInt("quantity"),
                            rs.getDouble("average_rating"),
                            rs.getTimestamp("expirationDate").toLocalDateTime(),
                            rs.getBoolean("is_surplus"),
                            rs.getBoolean("isForDonation"),
                            rs.getBoolean("isForSale"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void addFoodInventory(FoodInventory item) {
        String sql = "INSERT INTO FoodInventory (description, standard_price, quantity, average_rating, expirationDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, item.getDescription());
            stmt.setDouble(2, item.getStandardPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getAverageRating());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(item.getExpirationDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFoodInventory(FoodInventory inventory) {
        String sql = "INSERT INTO FoodInventory (description, quantity, average_rating, standard_price) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getDescription());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDouble(3, inventory.getAverageRating());
            pstmt.setDouble(4, inventory.getStandardPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventory(FoodInventory inventory) {

        LocalDateTime now = LocalDateTime.now();
        boolean isSurplus = inventory.getExpirationDate().isBefore(now.plus(1, ChronoUnit.WEEKS));


        String sql = "UPDATE FoodInventory SET description = ?, standard_price= ?,quantity = ?, average_rating = ?, expirationDate = ?, isForDonation = ?, isForSale = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inventory.getDescription());
            pstmt.setDouble(2, inventory.getStandardPrice());
            pstmt.setInt(3, inventory.getQuantity());
            pstmt.setDouble(4, inventory.getAverageRating());
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(inventory.getExpirationDate()));
            //pstmt.setBoolean(6, isSurplus);
            pstmt.setBoolean(6, inventory.getIsForDonation());
            pstmt.setBoolean(7, inventory.getIsForSale());
            pstmt.setInt(8, inventory.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventoryQuantity(int id, int qty) {
        String sql = "UPDATE FoodInventory SET quantity = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, qty);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodInventoryRating(int id, double rating) {
        String sql = "UPDATE FoodInventory SET average_rating = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, rating);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteFoodInventory(int id) {
        String sql = "DELETE FROM FoodInventory WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSurplusStatus() throws SQLException {
        List<FoodInventory> allItems = getAllFoodInventory();
        LocalDateTime now = LocalDateTime.now();
        for (FoodInventory item : allItems) {
            //boolean isSurplus = item.getExpirationDate().isBefore(now.plusWeeks(1));
            boolean isSurplus = item.getExpirationDate() != null && item.getExpirationDate().isBefore(now.plusWeeks(1));
            if (item.getIsSurplus() != isSurplus) {

                item.setIsSurplus(isSurplus);
                updateSurplusStatusInDatabase(item.getId(), isSurplus);
            }
        }
    }

    // Update surplus status in the database
    void updateSurplusStatusInDatabase(int id, boolean isSurplus) {
        try (Connection connection = Database.getConnection()) {
            String updateQuery = "UPDATE FoodInventory SET is_surplus = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setBoolean(1, isSurplus);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<FoodInventory> getSurplusFoodInventory() throws SQLException {
        List<FoodInventory> surplusFoodItems = new ArrayList<>();
        //LocalDateTime oneWeekFromNow = LocalDateTime.now().plusWeeks(1);
        String sql = "SELECT * FROM FoodInventory WHERE is_surplus = ?";

        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                //preparedStatement.setTimestamp(1, Timestamp.valueOf(oneWeekFromNow));
            preparedStatement.setBoolean(1,true);
                    try (ResultSet rs = preparedStatement.executeQuery()) {

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String description = rs.getString("description");
                            double standardPrice = rs.getDouble("standard_price");
                            int quantity = rs.getInt("quantity");
                            LocalDateTime expirationDate = rs.getTimestamp("expirationDate").toLocalDateTime();
                            double averageRating = rs.getDouble("average_rating");
                            boolean isSurplus = rs.getBoolean("is_surplus");
                            boolean isForDonation = rs.getBoolean("isForDonation");
                            boolean isForSale = rs.getBoolean("isForSale");

                            FoodInventory foodInventory = new FoodInventory(id, description, standardPrice, quantity, averageRating, expirationDate, isSurplus, isForDonation, isForSale);
                            surplusFoodItems.add(foodInventory);
                        }
            }
        }
        return surplusFoodItems;
    }

    public int updateAverageRating(int id, double averageRating) {
        int affectedRows = 0;
        String sql =  "UPDATE Foodinventory SET average_rating = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setDouble(1, averageRating);
                stmt.setInt(2, id);
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
}

