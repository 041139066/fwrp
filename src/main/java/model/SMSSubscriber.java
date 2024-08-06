package model;

import java.util.List;

public class SMSSubscriber extends Subscriber {

    public SMSSubscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        super(id, name, contact, foodPreferences);
    }

    @Override
    protected void sendNotifcation(FoodInventory item) {
        System.out.println("Phone: " + contact);
        System.out.println("Hi " + name + ", a new surplus food item is available for " + item.getStrStatus() + " that matches your preferences.");
    }
}
