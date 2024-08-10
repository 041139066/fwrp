package dataaccesslayer;

import model.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing rating-related operations in the database.
 * Provides methods to retrieve, create, update, delete, and calculate average ratings.
 */
public class RatingDAO {

    /**
     * Retrieves all ratings from the database.
     *
     * @return A list of all Rating objects.
     */
    public List<Rating> getAllRatings() {
        return getRatingList(0, 0);
    }

    /**
     * Retrieves all ratings for a specific consumer from the database.
     *
     * @param consumerId The unique identifier of the consumer.
     * @return A list of Rating objects for the specified consumer.
     */
    public List<Rating> getAllRatingsByConsumerId(int consumerId) {
        return getRatingList(1, consumerId);
    }

    /**
     * Retrieves all ratings for a specific food inventory item from the database.
     *
     * @param foodInventoryId The unique identifier of the food inventory item.
     * @return A list of Rating objects for the specified food inventory item.
     */
    public List<Rating> getAllRatingsByFoodInventoryId(int foodInventoryId) {
        return getRatingList(2, foodInventoryId);
    }

    /**
     * Retrieves a list of ratings from the database based on type and ID.
     *
     * @param type The type of query (0 for all ratings, 1 for consumer ratings, 2 for food inventory ratings).
     * @param id The ID used for filtering the ratings (consumer ID or food inventory ID).
     * @return A list of Rating objects matching the query criteria.
     */
    private List<Rating> getRatingList(int type, int id) {
        List<Rating> list = new ArrayList<>();
        String sql = "SELECT u.id AS consumer_id, u.name AS consumerName, f.id AS food_inventory_id, f.name AS food_inventory_name, r.rating, r.comment, r.last_modified " +
                "FROM Ratings AS r " +
                "JOIN Users AS u ON u.id = r.consumer_id " +
                "JOIN FoodInventory AS f ON f.id = r.food_inventory_id" +
                (type == 1 ? " WHERE u.id = ?" : type == 2 ? " WHERE f.id = ?" : "") +
                " ORDER BY last_modified DESC";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                if (type > 0) {
                    stmt.setInt(1, id);
                }
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Rating rating = makeRating(rs);
                        list.add(rating);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Constructs a Rating object from a ResultSet.
     *
     * @param rs The ResultSet containing the rating data.
     * @return The Rating object constructed from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private Rating makeRating(ResultSet rs) throws SQLException {
        Rating rating = new Rating();
        rating.setConsumerId(rs.getInt("consumer_id"));
        rating.setConsumerName(rs.getString("consumerName"));
        rating.setFoodInventoryId(rs.getInt("food_inventory_id"));
        rating.setFoodInventoryName(rs.getString("food_inventory_name"));
        rating.setRating(rs.getDouble("rating"));
        rating.setComment(rs.getString("comment"));
        rating.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
        return rating;
    }

    /**
     * Creates a new rating in the database.
     *
     * @param rating The Rating object containing the rating data to be created.
     * @return The number of affected rows.
     * @throws SQLException If a database access error occurs.
     */
    public int createRating(Rating rating) throws SQLException{
        return processRating(rating, true);
    }

    /**
     * Updates an existing rating in the database.
     *
     * @param rating The Rating object containing the updated rating data.
     * @return The number of affected rows.
     * @throws SQLException If a database access error occurs.
     */
    public int updateRating(Rating rating) throws SQLException{
        return processRating(rating, false);
    }

    /**
     * Processes a rating by either creating a new rating or updating an existing one.
     *
     * @param rating The Rating object containing the rating data.
     * @param isCreate True if creating a new rating, false if updating an existing rating.
     * @return The number of affected rows.
     * @throws SQLException If a database access error occurs.
     */
    private int processRating(Rating rating, boolean isCreate) throws SQLException{
        int affectedRows = 0;
        String sql = isCreate ? "INSERT INTO Ratings(rating, comment, consumer_id, food_inventory_id) VALUES (?, ?, ?, ?)" : "UPDATE Ratings SET rating = ?, comment = ? WHERE consumer_id = ? AND food_inventory_id = ?";
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, rating.getRating());
            stmt.setString(2, rating.getComment());
            stmt.setInt(3, rating.getConsumerId());
            stmt.setInt(4, rating.getFoodInventoryId());
            affectedRows = stmt.executeUpdate();
        }
        return affectedRows;
    }

    /**
     * Deletes a rating from the database based on consumer ID and food inventory ID.
     *
     * @param consumerId The unique identifier of the consumer.
     * @param foodInventoryId The unique identifier of the food inventory item.
     * @return The number of affected rows.
     */
    public int deleteRating(int consumerId, int foodInventoryId) {
        int affectedRows = 0;
        String sql = "DELETE FROM Ratings WHERE consumer_id = ? AND food_inventory_id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, consumerId);
                stmt.setInt(2, foodInventoryId);
                affectedRows = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /**
     * Retrieves the average rating for a specific food inventory item.
     *
     * @param foodInventoryId The unique identifier of the food inventory item.
     * @return The average rating of the food inventory item, or null if no ratings are found.
     */
    public Double getAverageRating(int foodInventoryId) {
        Double averageRating = null;
        String sql = "SELECT AVG(rating) AS average_rating FROM Ratings WHERE food_inventory_id = ?";
        try {
            Connection con = Database.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, foodInventoryId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        averageRating = rs.getDouble("average_rating");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageRating;
    }
}
