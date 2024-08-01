package dataaccesslayer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.FoodInventory;

public class FoodInventoryDAO {

    public List<FoodInventory> getAllFoodInventory() {
        List<FoodInventory> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM foodinventory")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        FoodInventory item = new FoodInventory();
                        item.setId(rs.getInt("id"));
                        item.setDescription(rs.getString("description"));
                        item.setStandardPrice(rs.getDouble("standard_price"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setAverageRating(rs.getDouble("average_rating"));
                        item.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
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

