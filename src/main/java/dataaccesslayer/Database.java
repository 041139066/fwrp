package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 * Provides methods to retrieve and close database connections.
 */
public class Database {
    private static final String driverString = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    /**
     * Retrieves a database connection.
     *
     * @return A Connection object representing the database connection.
     */
    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName(driverString);

            // Establish connection if not already initialized
            if (connection == null || connection.isClosed()) {
                Properties props = new Properties();

                // Load database properties from configuration file
                try (InputStream input = Database.class.getClassLoader().getResourceAsStream("properties.txt")) {
                    if (input == null) {
                        throw new IOException("Properties file not found");
                    }
                    props.load(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Retrieve connection details from properties and establish connection
                try {
                    String url = props.getProperty("db.url");
                    String username = props.getProperty("db.username");
                    String password = props.getProperty("db.password");
                    connection = DriverManager.getConnection(url, username, password);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
