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
                        subscription = makeSubscription(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscription;
    }

    public int createSubscriptionByconsumerId(Subscription subscription) {
        int affectedRows = 0;
        String sql = "INSERT INTO subscriptions (consumer_id, city, province, method, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
        if (subscription.getMethod().equalsIgnoreCase("email")) {
            sql = "INSERT INTO subscriptions (consumer_id, city, province, method, email) VALUES (?, ?, ?, ?, ?)";
        }
        if (subscription.getMethod().equalsIgnoreCase("sms")) {
            sql = "INSERT INTO subscriptions (consumer_id, city, province, method, phone) VALUES (?, ?, ?, ?, ?)";
        }
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, subscription.getConsumerId());
                stmt.setString(2, subscription.getCity());
                stmt.setString(3, subscription.getProvince());
                stmt.setString(4, subscription.getMethod());
                if (subscription.getMethod().equalsIgnoreCase("email")) {
                    stmt.setString(5, subscription.getEmail());
                }
                if (subscription.getMethod().equalsIgnoreCase("sms")) {
                    stmt.setString(5, subscription.getPhone());
                }
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;

    }

    public int updateSubscriptionByConsumerId(Subscription subscription) {
        int affectedRows = 0;
        String sql = getString(subscription);
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, subscription.getCity());
                stmt.setString(2, subscription.getProvince());
                stmt.setString(3, subscription.getMethod());
                if (subscription.getMethod().equalsIgnoreCase("email")) {
                    stmt.setString(4, subscription.getEmail());
                }
                if (subscription.getMethod().equalsIgnoreCase("sms")) {
                    stmt.setString(4, subscription.getPhone());
                }
                stmt.setInt(5, subscription.getConsumerId());
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private static String getString(Subscription subscription) {
        String sql = "UPDATE subscriptions SET " +
                "city = ?, " +
                "province = ?, " +
                "method = ?, " +
                "email = ?, " +
                "phone = ? " +
                "WHERE consumer_id = ?";
        if (subscription.getMethod().equalsIgnoreCase("email")) {
            sql = "UPDATE subscriptions SET " +
                    "city = ?, " +
                    "province = ?, " +
                    "method = ?, " +
                    "email = ? " +
                    "WHERE consumer_id = ?";
        }
        if (subscription.getMethod().equalsIgnoreCase("sms")) {
            sql = "UPDATE subscriptions SET " +
                    "city = ?, " +
                    "province = ?, " +
                    "method = ?, " +
                    "phone = ? " +
                    "WHERE consumer_id = ?";
        }
        return sql;
    }

    public int updateSubscriptionStatusByConsumerId(int consumerId, boolean status) {
        int affectedRows = 0;
        String sql = "UPDATE subscriptions SET status = ? WHERE consumer_id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setBoolean(1, status);
                stmt.setInt(2, consumerId);
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private Subscription makeSubscription(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setConsumerId(rs.getInt("consumer_id"));
        subscription.setCity(rs.getString("city"));
        subscription.setProvince(rs.getString("province"));
        subscription.setMethod(rs.getString("method"));
        subscription.setEmail(rs.getString("email"));
        subscription.setPhone(rs.getString("phone"));
        subscription.setStatus(rs.getBoolean("status"));
        return subscription;
    }

}
