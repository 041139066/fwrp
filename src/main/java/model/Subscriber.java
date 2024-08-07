package model;

import java.util.List;

public abstract class Subscriber {
    protected int id;
    protected String name;
    protected String contact;
    protected List<Integer> foodPreferences;

    public Subscriber(int id, String name, String contact, List<Integer> foodPreferences) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.foodPreferences = foodPreferences;
    }


    public void update(FoodInventory item){
        boolean isloved = foodPreferences.contains(item.getId());
        if(foodPreferences.contains(item.getId())){
            sendNotifcation(item);
        }
    }

    abstract protected void sendNotifcation(FoodInventory item);
}
