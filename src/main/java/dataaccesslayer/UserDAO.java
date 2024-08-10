package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import model.User;
import model.constants.UserType;
import model.constants.MethodType;

/**
 * Data Access Object (DAO) for managing user-related operations in the database.
 * Provides methods to retrieve, update, and register users.
 */
public class UserDAO {

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id The unique identifier of the user.
     * @return The User object corresponding to the given ID, or null if not found.
     */
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

    /**
     * Updates the subscription details of a user in the database.
     *
     * @param user The User object containing the updated subscription information.
     * @return The number of affected rows.
     * @throws SQLException If a database access error occurs.
     */
    public int updateSubscription(User user) throws SQLException {
        int affectedRows;
        String contact = "";
        // if the communication method is "email", only update the email address
        if (user.getMethod() == MethodType.email) {
            contact = "contact_email = ?, ";
        }
        // if the communication method is "sma", only update the phone number
        if (user.getMethod() == MethodType.sms) {
            contact = "contact_phone = ?,";
        }
        String sql = "UPDATE Users SET method = ?, " + contact + " subscription = ? WHERE id = ?";
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getMethodName());
            // if the communication method is "email", only update the email address
            if (user.getMethod() == MethodType.email) {
                stmt.setString(2, user.getContactEmail());
            }
            // if the communication method is "sma", only update the phone number
            if (user.getMethod() == MethodType.sms) {
                stmt.setString(2, user.getContactPhone());
            }
            stmt.setBoolean(3, user.getSubscription());
            stmt.setInt(4, user.getId());
            affectedRows = stmt.executeUpdate();
        }
        return affectedRows;
    }

    /**
     * Updates the subscription status of a user in the database.
     *
     * @param status The new subscription status (true for subscribed, false for unsubscribed).
     * @param userId The unique identifier of the user.
     * @return The number of affected rows.
     */
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

    /**
     * Constructs a User object from a ResultSet.
     *
     * @param resultSet The ResultSet containing the user data.
     * @return The User object constructed from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Registers a new user in the database.
     *
     * @param user The User object containing the user data to be registered.
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Retrieves a user from the database by their email.
     *
     * @param email The email address of the user.
     * @return The User object corresponding to the given email, or null if not found.
     */
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

    /**
     * Retrieves a list of subscribers based on user type and retailer ID.
     *
     * @param type       The type of users to retrieve (e.g., consumers, charitable).
     * @param retailerId The unique identifier of the retailer.
     * @return A list of User objects representing the subscribers.
     */
    public List<User> getSubscribers(UserType type, int retailerId) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users " +
                "WHERE city = (SELECT city FROM users WHERE id = ?) " +
                "AND province = (SELECT province FROM users WHERE id = ?) " + // subscribers from the same location of the retailer
                "AND subscription = TRUE " +
                "AND type = ? " + // the user type, "consumer" or "charitable"
                "AND id != ?"; //
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
