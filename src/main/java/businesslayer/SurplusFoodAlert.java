package businesslayer;



import java.util.HashMap;
import java.util.Map;
import java.util.List;

import dataaccesslayer.UserDAO;
import model.FoodInventory;
import model.Subscriber;
import model.constants.FoodStatus;
import model.constants.UserType;

public class SurplusFoodAlert implements Publisher{
    private final Map<FoodStatus, List<Subscriber>> subscribers;

    public SurplusFoodAlert(int retailerId) {
        UserManager manager = new UserManager();
        subscribers = new HashMap<>();
        subscribers.put(FoodStatus.donation, manager.getSubscribers(UserType.charitable, retailerId));
        subscribers.put(FoodStatus.sale, manager.getSubscribers(UserType.consumer, retailerId));
    }

    public void subscribe(FoodStatus event, Subscriber subscriber) {
        subscribers.get(event).add(subscriber);
    }

    public void unsubscribe(FoodStatus event, Subscriber subscriber) {
        subscribers.get(event).remove(subscriber);
    }

    @Override
    public void notifySubscribers(FoodInventory item) {
        subscribers.get(item.getStatus()).forEach(subscriber -> subscriber.update(item));
    }

}
