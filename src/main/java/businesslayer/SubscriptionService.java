package businesslayer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import dataaccesslayer.FoodPreferencesDAO;
import model.FoodPreference;
import dataaccesslayer.UserDAO;
import model.User;

/**
 * Manages user subscriptions and food preferences.
 */
public class SubscriptionService {

    private final UserDAO userDAO;
    private final FoodPreferencesDAO prefDAO;

    /**
     * Constructs a SubscriptionService with necessary DAO instances.
     */
    public SubscriptionService() {
        userDAO = new UserDAO();
        prefDAO = new FoodPreferencesDAO();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve.
     * @return the User object associated with the given ID.
     */
    public User getSubscription(int userId) {
        return userDAO.getUserById(userId);
    }

    /**
     * Updates the subscription details of a user.
     *
     * @param user the User object containing updated subscription details.
     * @return the number of rows affected in the database.
     * @throws SQLException if a database access error occurs.
     */
    public int updateSubscription(User user) throws SQLException {
        return userDAO.updateSubscription(user);
    }

    /**
     * Updates the status of a user.
     *
     * @param status the new status to set.
     * @param userId the ID of the user whose status is to be updated.
     * @return the number of rows affected in the database.
     */
    public int updateStatus(boolean status, int userId) {
        return userDAO.updateStatus(status, userId);
    }

    /**
     * Retrieves a list of food preferences for a specific user.
     *
     * @param userId the ID of the user whose food preferences are to be retrieved.
     * @return a List of food preference IDs associated with the given user.
     */
    public List<Integer> getFoodPreferencesByUserId(int userId) {
        return prefDAO.getFoodPreferencesByUserId(userId);
    }

    /**
     * Updates the food preferences for a user based on the provided IDs.
     *
     * @param userId the ID of the user whose food preferences are to be updated.
     * @param ids a comma-separated string of food preference IDs to update.
     */
    public void updateFoodPreferences(int userId, String ids) {
        List<Integer> oldIds = prefDAO.getFoodPreferencesByUserId(userId);
        List<Integer> newIds = parseIds(ids);

        List<Integer> toAdd = new ArrayList<>(newIds);
        toAdd.removeAll(oldIds);

        List<Integer> toRemove = new ArrayList<>(oldIds);
        toRemove.removeAll(newIds);

        // Add new preferences
        for (Integer id : toAdd) {
            prefDAO.addFoodPreference(new FoodPreference(userId, id));
        }

        // Remove old preferences
        for (Integer id : toRemove) {
            prefDAO.deleteFoodPreference(new FoodPreference(userId, id));
        }
    }

    /**
     * Parses a comma-separated string of IDs into a list of integers.
     *
     * @param ids a comma-separated string of IDs.
     * @return a List of integers parsed from the provided string.
     */
    private List<Integer> parseIds(String ids) {
        List<Integer> parsedIds = new ArrayList<>();
        for (String id : ids.split(",")) {
            try {
                parsedIds.add(Integer.parseInt(id.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + id);
            }
        }
        Collections.sort(parsedIds);
        return parsedIds;
    }
}
