package dataaccesslayer;

import model.AvailableSaleFood;

import java.sql.*;

public class PurchaseFoodDao {

    public boolean purchaseFood(Integer foodItemId,Integer customerId,Integer need) {
        try {
            Connection con = Database.getConnection();

            PreparedStatement stmt1 = con.prepareStatement("update foodinventory set quantity = quantity - ? where id = ?");
            stmt1.setInt(1, need);
            stmt1.setInt(2, foodItemId);
            int i = stmt1.executeUpdate();

            PreparedStatement stmt = con.prepareStatement("INSERT INTO purchasedfood(food_item_id,consumer_id,purchase_date) VALUES(?,?,?) ");
            stmt.setInt(1, foodItemId);
            stmt.setInt(2, customerId);
            stmt.setObject(3, new Date(new java.util.Date().getTime()));
            boolean execute = stmt.execute();

            if (execute){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public AvailableSaleFood getAvailableSaleFoodById(Integer foodItemId) {
        AvailableSaleFood item = null;
        try {
            Connection con = Database.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT fooditems.id,description,price,quantity,status FROM fooditems,foodinventory " +
                    "where fooditems.food_inventory_id = foodinventory.id and fooditems.status='sale' and foodinventory.quantity>0 and fooditems.id=?");
            stmt.setInt(1, foodItemId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            item = new AvailableSaleFood();
            item.setId(rs.getInt("id"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));
            item.setStatus(rs.getString("status"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
