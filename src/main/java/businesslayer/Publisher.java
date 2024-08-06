package businesslayer;

import model.FoodInventory;
import model.Subscriber;
import model.constants.FoodStatus;

public interface Publisher {
    void subscribe(FoodStatus event, Subscriber subscriber);
    void unsubscribe(FoodStatus event, Subscriber subscriber);
    void notifySubscribers(FoodInventory item);
}
