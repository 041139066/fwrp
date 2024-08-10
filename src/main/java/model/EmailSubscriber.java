package model;

import java.util.List;

/**
 * The EmailSubscriber class represents a subscriber who receives notifications via email.
 * It extends the Subscriber class and provides a specific implementation for sending email notifications.
 */
public class EmailSubscriber extends Subscriber {

    /**
     * Constructs an EmailSubscriber with the specified parameters.
     *
     * @param id The unique identifier of the subscriber.
     * @param name The name of the subscriber.
     * @param contact The email address of the subscriber.
     * @param foodPreferences A list of food inventory IDs that the subscriber prefers.
     */
    public EmailSubscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        super(id, name, contact, foodPreferences);
    }

    /**
     * Sends an email notification about surplus food to the subscriber.
     * This implementation prints out the notification details to the console.
     *
     * @param item The FoodInventory item that triggers the notification.
     */
    @Override
    protected void sendNotifcation(FoodInventory item) {
        System.out.println("\n============ Email Notification ==========");
        System.out.println("To: " + name);
        System.out.println("Email Address: " + contact);
        System.out.println("Surplus Food : " + item.getName());
        System.out.println("==========================================\n");
    }
}
