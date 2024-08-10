package model;

import java.util.List;

/**
 * The SMSSubscriber class extends the Subscriber class and represents a subscriber
 * who receives notifications via SMS about surplus food items in a food inventory system.
 */
public class SMSSubscriber extends Subscriber {

    /**
     * Constructs an SMSSubscriber with the specified ID, name, contact information, and food preferences.
     *
     * @param id The subscriber's ID.
     * @param name The subscriber's name.
     * @param contact The subscriber's contact information, typically a phone number.
     * @param foodPreferences A list of food item IDs that the subscriber is interested in.
     */
    public SMSSubscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        super(id, name, contact, foodPreferences);
    }

    /**
     * Sends an SMS notification to the subscriber about a specific surplus food item.
     * This method overrides the abstract method in the Subscriber class to define
     * how SMS notifications are sent.
     *
     * @param item The food inventory item to notify the subscriber about.
     */
    @Override
    protected void sendNotifcation(FoodInventory item) {
        System.out.println("\n============ SMS Notification ============");
        System.out.println("To: " + name);
        System.out.println("Phone Number: " + contact);
        System.out.println("Surplus Food : " + item.getName());
        System.out.println("==========================================\n");
    }
}
