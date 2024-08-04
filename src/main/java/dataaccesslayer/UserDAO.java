package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.MethodType;
import model.User;
import utilities.PasswordHasher;

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

    public int updateSubscription(User user) {
        int affectedRows = 0;
        String contact = "";
        if (user.getMethod() == MethodType.email) {
            contact = "contact_email = ?, ";
        }
        if (user.getMethod() == MethodType.sms) {
            contact = "contact_phone = ?,";
        }
        String sql = "UPDATE Users SET city = ?, province = ?, method = ?, " + contact + " subscription = ? WHERE id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, user.getCity());
                stmt.setString(2, user.getProvince());
                stmt.setString(3, user.getMethodName());
                if (user.getMethod() == MethodType.email) {
                    stmt.setString(4, user.getContactEmail());
                }
                if (user.getMethod() == MethodType.sms) {
                    stmt.setString(4, user.getContactPhone());
                }
                stmt.setBoolean(5, user.getSubscription());
                stmt.setInt(6, user.getId());
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void register(String username, String email, String password, String type) throws SQLException {
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(PasswordHasher.hashPassword(password));
        user.setType(type);

        PreparedStatement statement = Database.getConnection().prepareStatement(
                "INSERT INTO users (name, email, password, type) VALUES (?, ?, ?, ?)"
        );

        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getTypeName());
        statement.executeUpdate();
    }

    public User auth (String email, String password){
        try {
            Connection connection = Database.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2,password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                       User user = new User();
                       user.setEmail(resultSet.getString("email"));
                       user.setName(resultSet.getString("name"));
                        user.setType(resultSet.getString("type"));
                        return user;
                    }
                    else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    };

    public User findByEmail(String email) {
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
}



