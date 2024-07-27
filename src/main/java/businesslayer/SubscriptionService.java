package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.FoodPreferencesDAO;
import dataaccesslayer.SubscriptionDAO;
import model.FoodInventory;
import model.Subscription;

import java.util.List;

public class SubscriptionService {

    private final SubscriptionDAO subsDao;
    private final FoodPreferencesDAO prefsDao;
    private final FoodInventoryDAO invDao;

    public SubscriptionService() {
        subsDao = new SubscriptionDAO();
        prefsDao = new FoodPreferencesDAO();
        invDao = new FoodInventoryDAO();
    }

    public Subscription getSubscription(int userId) {
        return subsDao.getSubscriptionByConsumerId(userId);
    }

    public List<FoodInventory> getFoodPreferences(int userId) {
        List<Integer> ids = prefsDao.getFoodPreferencesByUserId(userId);
        List<FoodInventory> foodInventory = invDao.getAllFoodInventory();
        return foodInventory.stream()
                .filter(item -> ids.contains(item.getId()))
                .toList();
    }


}
