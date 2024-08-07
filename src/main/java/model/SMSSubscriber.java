package model;

import java.util.List;

public class SMSSubscriber extends Subscriber {

    public SMSSubscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        super(id, name, contact, foodPreferences);
    }

    @Override
    protected void sendNotifcation(FoodInventory item) {
        System.out.println("\n============ SMS Notification ============");
        System.out.println("To: " + name);
        System.out.println("Phone Number: " + contact);
        System.out.println("Surplus Food : " + item.getName());
        System.out.println("==========================================\n");
    }
}
