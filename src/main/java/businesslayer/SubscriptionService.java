package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.FoodPreferencesDAO;
import dataaccesslayer.SubscriptionDAO;
import model.FoodInventory;
import model.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionService {

    private final SubscriptionDAO subsDao;
    private final FoodPreferencesDAO prefsDao;

    public SubscriptionService(){
        subsDao = new SubscriptionDAO();
        prefsDao = new FoodPreferencesDAO();
    }
    public Subscription getSubscription(int userId) {
        return subsDao.getSubscriptionByConsumerId(userId);
    }

    public List<Integer> getFoodPreferences(int userId) {
        return prefsDao.getFoodPreferencesByUserId(userId);
    }


}
