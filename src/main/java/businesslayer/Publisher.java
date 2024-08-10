package businesslayer;

import model.FoodInventory;
import model.Subscriber;
import model.constants.FoodStatus;

/**
 * Interface for a publisher that manages subscriptions and notifications
 * related to food inventory events.
 */
public interface Publisher {

    /**
     * Subscribes a subscriber to a specific food status event.
     *
     * @param event the food status event to subscribe to.
     * @param subscriber the subscriber to be added to the event.
     */
    void subscribe(FoodStatus event, Subscriber subscriber);

    /**
     * Unsubscribes a subscriber from a specific food status event.
     *
     * @param event the food status event to unsubscribe from.
     * @param subscriber the subscriber to be removed from the event.
     */
    void unsubscribe(FoodStatus event, Subscriber subscriber);

    /**
     * Notifies all subscribers of a food inventory item related to a food status event.
     *
     * @param item the food inventory item to notify subscribers about.
     */
    void notifySubscribers(FoodInventory item);
}
