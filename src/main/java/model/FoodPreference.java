package model;

public class FoodPreference {
    private int consumerId;
    private int foodInventoryId;

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    @Override
    public String toString() {
        return "FoodPreferences{" +
                "consumerId=" + consumerId +
                ", foodInventoryId=" + foodInventoryId +
                '}';
    }
}
