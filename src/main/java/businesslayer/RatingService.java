package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.RatingDAO;
import model.Rating;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides services for managing ratings of food inventory items.
 */
public class RatingService {

    private final RatingDAO rtDAO;
    private final FoodInventoryDAO fiDAO;

    /**
     * Constructs a RatingService with necessary DAO instances.
     */
    public RatingService() {
        this.rtDAO = new RatingDAO();
        this.fiDAO = new FoodInventoryDAO();
    }

    /**
     * Retrieves all ratings from the database.
     *
     * @return a List of all Rating objects.
     */
    public List<Rating> getAllRatings() {
        return rtDAO.getAllRatings();
    }

    /**
     * Retrieves all ratings given by a specific consumer.
     *
     * @param consumerId the ID of the consumer whose ratings are to be retrieved.
     * @return a List of Rating objects associated with the given consumer ID.
     */
    public List<Rating> getAllRatingsByConsumerId(int consumerId) {
        return rtDAO.getAllRatingsByConsumerId(consumerId);
    }

    /**
     * Retrieves all ratings associated with a specific food inventory item.
     *
     * @param foodInventoryId the ID of the food inventory item whose ratings are to be retrieved.
     * @return a List of Rating objects associated with the given food inventory ID.
     */
    public List<Rating> getAllRatingsByFoodInventoryId(int foodInventoryId) {
        return rtDAO.getAllRatingsByFoodInventoryId(foodInventoryId);
    }

    /**
     * Creates a new rating and updates the average rating for the associated food inventory item.
     *
     * @param rating the Rating object to be created.
     * @return the number of rows affected in the database.
     * @throws SQLException if a database access error occurs.
     */
    public int createRating(Rating rating) throws SQLException {
        int affectedRows = rtDAO.createRating(rating);
        updateAverageRating(rating.getFoodInventoryId());
        return affectedRows;
    }

    /**
     * Updates an existing rating and updates the average rating for the associated food inventory item.
     *
     * @param rating the Rating object with updated details.
     * @return the number of rows affected in the database.
     * @throws SQLException if a database access error occurs.
     */
    public int updateRating(Rating rating) throws SQLException {
        int affectedRows = rtDAO.updateRating(rating);
        updateAverageRating(rating.getFoodInventoryId());
        return affectedRows;
    }

    /**
     * Deletes a rating for a specific consumer and food inventory item, and updates the average rating.
     *
     * @param consumerId the ID of the consumer whose rating is to be deleted.
     * @param foodInventoryId the ID of the food inventory item associated with the rating.
     * @return the number of rows affected in the database.
     */
    public int deleteRating(int consumerId, int foodInventoryId) {
        int affectedRows = rtDAO.deleteRating(consumerId, foodInventoryId);
        updateAverageRating(foodInventoryId);
        return affectedRows;
    }

    /**
     * Updates the average rating for a specific food inventory item.
     *
     * @param foodInventoryId the ID of the food inventory item whose average rating is to be updated.
     */
    private void updateAverageRating(int foodInventoryId) {
        Double averageRating = rtDAO.getAverageRating(foodInventoryId);
        fiDAO.updateFoodInventoryAverageRating(foodInventoryId, averageRating);
    }
}
