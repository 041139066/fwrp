package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Subscription;

public class SubscriptionDAO {

    public Subscription getSubscriptionByConsumerId(int userId) {
        Subscription subscription = null;
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM subscriptions WHERE consumer_id = ?")) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        subscription = new Subscription();
                        subscription.setConsumerId(rs.getInt("consumer_id"));
                        subscription.setCity(rs.getString("city"));
                        subscription.setProvince(rs.getString("province"));
                        subscription.setMethod(rs.getString("method"));
                        subscription.setStatus(rs.getBoolean("status"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscription;
    }

}
