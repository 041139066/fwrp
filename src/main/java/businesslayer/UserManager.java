package businesslayer;

import dataaccesslayer.FoodPreferencesDAO;
import dataaccesslayer.UserDAO;
import model.EmailSubscriber;
import model.SMSSubscriber;
import model.Subscriber;
import model.constants.MethodType;
import model.constants.UserType;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages user-related operations, including retrieving subscribers based on user type and retailer ID.
 */
public class UserManager {

    private UserDAO userDAO;
    private FoodPreferencesDAO prefDAO;

    /**
     * Constructs a UserManager with default DAOs.
     */
    public UserManager() {
        this.userDAO = new UserDAO();
        this.prefDAO = new FoodPreferencesDAO();
    }

    /**
     * Constructs a UserManager with specified DAOs for testing purposes.
     *
     * @param userDAO Data Access Object for user-related operations.
     * @param prefDAO Data Access Object for food preferences-related operations.
     */
    public UserManager(UserDAO userDAO, FoodPreferencesDAO prefDAO) {
        this.userDAO = userDAO;
        this.prefDAO = prefDAO;
    }

    /**
     * Retrieves a list of subscribers based on the user type and retailer ID.
     *
     * @param type       The type of user (e.g., consumer, retailer).
     * @param retailerId The ID of the retailer for which to retrieve subscribers.
     * @return A list of subscribers with their food preferences.
     */
    public List<Subscriber> getSubscribers(UserType type, int retailerId) {
        List<Subscriber> subscribers = new ArrayList<>();
        try {
            List<User> users = userDAO.getSubscribers(type, retailerId);
            for (User user : users) {
                List<Integer> foodPreferences = prefDAO.getFoodPreferencesByUserId(user.getId());
                if (user.getMethod() == MethodType.email) {
                    subscribers.add(new EmailSubscriber(user.getId(), user.getName(), user.getContactEmail(), foodPreferences));
                } else if (user.getMethod() == MethodType.sms) {
                    subscribers.add(new SMSSubscriber(user.getId(), user.getName(), user.getContactPhone(), foodPreferences));
                }
            }
        } catch (Exception e) {
            // Log the exception and handle it appropriately
            System.err.println("Error retrieving subscribers: " + e.getMessage());
            e.printStackTrace();
        }
        return subscribers;
    }
}
