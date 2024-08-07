package model;

import java.util.List;

public class EmailSubscriber extends Subscriber {

    public EmailSubscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        super(id, name, contact, foodPreferences);
    }

    @Override
    protected void sendNotifcation(FoodInventory item) {
        System.out.println("\n============ Email Notification ==========");
        System.out.println("To: " + name);
        System.out.println("Email Address: " + contact);
        System.out.println("Surplus Food : " + item.getName());
        System.out.println("==========================================\n");
    }
}
