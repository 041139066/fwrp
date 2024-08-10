package model;

import java.util.List;

/**
 * The Subscriber class represents an abstract base class for subscribers (consumers or charitable organizations)
 * who receive notifications about surplus food (for donation or for sale) in food inventory.
 * Each subscriber has an ID, name, contact information, and a list of food preferences.
 */
public abstract class Subscriber {
    protected int id;
    protected String name;
    protected String contact;
    protected List<Integer> foodPreferences;

    /**
     * Constructs a Subscriber with the specified ID, name, contact information, and food preferences.
     *
     * @param id The subscriber's ID.
     * @param name The subscriber's name.
     * @param contact The subscriber's contact information.
     * @param foodPreferences A list of food item IDs that the subscriber is interested in.
     */
    public Subscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.foodPreferences = foodPreferences;
    }

    /**
     * Updates the subscriber with information about a food inventory item.
     * If the food item matches the subscriber's preferences, a notification is sent.
     *
     * @param item The food inventory item that has been updated.
     */
    public void update(FoodInventory item) {
        if (foodPreferences.contains(item.getId())) {
            sendNotifcation(item);
        }
    }

    /**
     * Sends a notification to the subscriber about a specific food inventory item.
     * This method must be implemented by subclasses to define how the notification is sent.
     *
     * @param item The food inventory item to notify the subscriber about.
     */
    abstract protected void sendNotifcation(FoodInventory item);
}
