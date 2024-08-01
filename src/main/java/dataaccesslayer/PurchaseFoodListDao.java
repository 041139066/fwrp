package dataaccesslayer;

import model.AvailableSaleFood;
import model.DonationFoodVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseFoodListDao {

    public List<AvailableSaleFood> getAllSaleFood() {
        List<AvailableSaleFood> list = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT fooditems.id,description,price,quantity,status FROM fooditems,foodinventory " +
                    "where fooditems.food_inventory_id = foodinventory.id and fooditems.status!='donation' and foodinventory.quantity>0")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        AvailableSaleFood item = new AvailableSaleFood();
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
}
