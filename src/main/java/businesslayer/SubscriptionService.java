package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.FoodPreferencesDAO;
import dataaccesslayer.SubscriptionDAO;
import model.FoodInventory;
import model.FoodPreference;
import model.Subscription;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SubscriptionService {

    private final SubscriptionDAO subsDao;
    private final FoodPreferencesDAO prefDao;
    private final FoodInventoryDAO invDao;

    public SubscriptionService() {
        subsDao = new SubscriptionDAO();
        prefDao = new FoodPreferencesDAO();
        invDao = new FoodInventoryDAO();
    }

    public Subscription getSubscription(int userId) {
        return subsDao.getSubscriptionByConsumerId(userId);
    }

    public int createSubscription(Subscription subscription) {
        return subsDao.createSubscriptionByconsumerId(subscription);
    }
    public int updateSubscription(Subscription subscription) {
        return subsDao.updateSubscriptionByConsumerId(subscription);
    }

    public int unsubscribe(int consumerId) {
        return subsDao.updateSubscriptionStatusByConsumerId(consumerId, false);
    }

    public int reactivate(int consumerId) {
        return subsDao.updateSubscriptionStatusByConsumerId(consumerId, true);
    }

    public List<FoodInventory> getFoodPreferences(int userId) {
        List<Integer> ids = prefDao.getFoodPreferencesByUserId(userId);
        List<FoodInventory> foodInventory = invDao.getAllFoodInventory();
        return foodInventory.stream()
                .filter(item -> ids.contains(item.getId()))
                .toList();
    }

    public void updateFoodPreferences(int consumerId, String strIds) {
        List<Integer> oldIds = prefDao.getFoodPreferencesByUserId(consumerId);
        List<Integer> newIds = new ArrayList<>();
        for (String id : strIds.split(",")) {
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
                prefDao.addFoodPreference(new FoodPreference(consumerId, newId));
                newId = newIterator.next();
            } else {
                prefDao.deleteFoodPreference(new FoodPreference(consumerId, oldId));
                oldId = oldIterator.next();
            }
        }
        if (newId != oldId) {
           prefDao.addFoodPreference(new FoodPreference(consumerId, newId));
           prefDao.deleteFoodPreference(new FoodPreference(consumerId, oldId));
        }
        while (newIterator.hasNext()) {
            newId = newIterator.next();
            prefDao.addFoodPreference(new FoodPreference(consumerId, newId));
        }
        while (oldIterator.hasNext()) {
            oldId = oldIterator.next();
            prefDao.deleteFoodPreference(new FoodPreference(consumerId, oldId));
        }
    }

}
