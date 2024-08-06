package model;

import java.util.List;

public class EmailSubscriber extends Subscriber {

    public EmailSubscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        super(id, name, contact, foodPreferences);
    }

    @Override
    protected void sendNotifcation(FoodInventory item) {
        System.out.println("Email: " + contact);
        System.out.println("Subject: Surplus Food " + item.getStrStatus() + " Alert: " + item.getName());
        System.out.println("Dear " + name + ",");
        System.out.println("We are pleased to inform you that a new surplus food item has been listed for " + item.getStrStatus() + " that matches your preferences.");
        System.out.println("Best Regards,");
        System.out.println("Food Waste Reduction");
    }
}
