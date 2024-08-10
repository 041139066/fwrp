package businesslayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.FoodInventory;
import model.Subscriber;
import model.constants.FoodStatus;
import model.constants.UserType;

/**
 * Manages notifications to subscribers about surplus food based on its status.
 */
public class SurplusFoodAlert implements Publisher {
    private final Map<FoodStatus, List<Subscriber>> subscribers;

    private UserManager userManager;

    /**
     * Constructs a SurplusFoodAlert with the given retailer ID.
     * @param retailerId The ID of the retailer.
     */
    public SurplusFoodAlert(int retailerId) {
        this(new UserManager(), retailerId);
    }

    /**
     * Constructs a SurplusFoodAlert with a specified UserManager for testing purposes.
     * @param userManager The UserManager to use for retrieving subscribers.
     * @param retailerId The ID of the retailer.
     */
    public SurplusFoodAlert(UserManager userManager, int retailerId) {
        this.userManager = userManager;
        this.subscribers = new HashMap<>();
        initializeSubscribers(retailerId);
    }

    /**
     * Initializes the subscribers map based on the retailer ID.
     * @param retailerId The ID of the retailer.
     */
    private void initializeSubscribers(int retailerId) {
        subscribers.put(FoodStatus.donation, userManager.getSubscribers(UserType.charitable, retailerId));
        subscribers.put(FoodStatus.sale, userManager.getSubscribers(UserType.consumer, retailerId));
    }

    /**
     * Subscribes a subscriber to a specific food status.
     * @param event The food status to subscribe to.
     * @param subscriber The subscriber to add.
     */
    public void subscribe(FoodStatus event, Subscriber subscriber) {
        subscribers.computeIfAbsent(event, k -> new ArrayList<>()).add(subscriber);
    }

    /**
     * Unsubscribes a subscriber from a specific food status.
     * @param event The food status to unsubscribe from.
     * @param subscriber The subscriber to remove.
     */
    public void unsubscribe(FoodStatus event, Subscriber subscriber) {
        List<Subscriber> list = subscribers.get(event);
        if (list != null) {
            list.remove(subscriber);
        }
    }

    /**
     * Notifies all subscribers of a food inventory update based on its status.
     * @param item The food inventory item that has been updated.
     */
    @Override
    public void notifySubscribers(FoodInventory item) {
        List<Subscriber> list = subscribers.get(item.getStatus());
        if (list != null) {
            list.forEach(subscriber -> subscriber.update(item));
        }
    }
}
