package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.FoodPreferencesDAO;
import dataaccesslayer.UserDAO;
import model.FoodInventory;
import model.FoodPreference;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SubscriptionService {

    private final UserDAO userDAO;
    private final FoodPreferencesDAO prefDAO;

    public SubscriptionService() {
        userDAO = new UserDAO();
        prefDAO = new FoodPreferencesDAO();
    }

    public User getSubscription(int userId) {
        return userDAO.getUserById(userId);
    }

    public int updateSubscription(User user) throws SQLException {
        return userDAO.updateSubscription(user);
    }

    public int updateStatus(boolean status, int userId) {
        return userDAO.updateStatus(status, userId);
    }

    public List<Integer> getFoodPreferencesByUserId(int userId) {
        return prefDAO.getFoodPreferencesByUserId(userId);
    }

    public void updateFoodPreferences(int userId, String ids) {
        List<Integer> oldIds = prefDAO.getFoodPreferencesByUserId(userId);
        List<Integer> newIds = new ArrayList<>();
        for (String id : ids.split(",")) {
            try {
                newIds.add(Integer.parseInt(id.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + id);
            }
        }
        Collections.sort(oldIds);
        Collections.sort(newIds);
        Iterator<Integer> oldIterator = oldIds.iterator();
        Iterator<Integer> newIterator = newIds.iterator();
        int newId = 0;
        int oldId = 0;
        while (newIterator.hasNext() && oldIterator.hasNext()) {
            if (newId == oldId) {
                newId = newIterator.next();
                oldId = oldIterator.next();
            } else if (newId < oldId) {
                prefDAO.addFoodPreference(new FoodPreference(userId, newId));
                newId = newIterator.next();
            } else {
                prefDAO.deleteFoodPreference(new FoodPreference(userId, oldId));
                oldId = oldIterator.next();
            }
        }
        if (newId != oldId) {
            prefDAO.addFoodPreference(new FoodPreference(userId, newId));
            prefDAO.deleteFoodPreference(new FoodPreference(userId, oldId));
        }
        while (newIterator.hasNext()) {
            newId = newIterator.next();
            prefDAO.addFoodPreference(new FoodPreference(userId, newId));
        }
        while (oldIterator.hasNext()) {
            oldId = oldIterator.next();
            prefDAO.deleteFoodPreference(new FoodPreference(userId, oldId));
        }
    }








}
