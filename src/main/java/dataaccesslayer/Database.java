package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
            // Establish connection if not already initialized
            if (connection == null) {
                // Load the MySQL JDBC driver
                Class.forName(driverString);
                Properties props = new Properties();
                // Load database properties from configuration file
                try (InputStream input = Database.class.getClassLoader().getResourceAsStream("properties.txt")) {
                    props.load(input);
                }
                // Retrieve connection details from properties and establish connection
                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
        } catch (IOException e) {
            System.err.println("Failed to load properties file.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to database.");
        }
        return connection;
    }
}
