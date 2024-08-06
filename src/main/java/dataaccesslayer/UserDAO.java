package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.EmailSubscriber;
import model.FoodInventory;
import model.Subscriber;
import model.constants.MethodType;
import model.User;
import model.constants.UserType;

public class UserDAO {

    // Method to get a user by ID
    public User getUserById(int id) {
        User user = null;
        try {
            Connection connection = Database.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = makeUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int updateSubscription(User user) throws SQLException {
        int affectedRows = 0;
        String contact = "";
        if (user.getMethod() == MethodType.email) {
            contact = "contact_email = ?, ";
        }
        if (user.getMethod() == MethodType.sms) {
            contact = "contact_phone = ?,";
        }
        String sql = "UPDATE Users SET method = ?, " + contact + " subscription = ? WHERE id = ?";
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, user.getMethodName());
            if (user.getMethod() == MethodType.email) {
                stmt.setString(2, user.getContactEmail());
            }
            if (user.getMethod() == MethodType.sms) {
                stmt.setString(2, user.getContactPhone());
            }
            stmt.setBoolean(3, user.getSubscription());
            stmt.setInt(4, user.getId());
            affectedRows = stmt.executeUpdate();
        }
        return affectedRows;
    }

    public int updateStatus(boolean status, int userId) {
        int affectedRows = 0;
        String sql = "UPDATE Users SET subscription = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setBoolean(1, status);
                stmt.setInt(2, userId);
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private User makeUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setType(resultSet.getString("type"));
        user.setSubscription(resultSet.getBoolean("subscription"));
        user.setCity(resultSet.getString("city"));
        user.setProvince(resultSet.getString("province"));
        user.setMethod(resultSet.getString("method"));
        user.setContactEmail(resultSet.getString("contact_email"));
        user.setContactPhone(resultSet.getString("contact_phone"));
        return user;
    }

    public void register(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, type, city, province) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getTypeName());
            stmt.setString(5, user.getCity());
            stmt.setString(6, user.getProvince());
            stmt.executeUpdate();
        }
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {
            Connection connection = Database.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = makeUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getSubscribers(UserType type, int retailerId) {

        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users " +
                "WHERE city = (SELECT city FROM users WHERE id = ?) " +
                "AND province = (SELECT province FROM users WHERE id = ?) " +
                "AND subscription = TRUE " +
                "AND type = ? " +
                "AND id != ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, retailerId);
                stmt.setInt(2, retailerId);
                stmt.setString(3, type.name());
                stmt.setInt(4, retailerId);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(makeUser(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}



