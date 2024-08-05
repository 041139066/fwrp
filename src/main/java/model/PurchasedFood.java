package model;

import java.time.LocalDateTime;

public class PurchasedFood {
    private int foodItemId;
    private int consumerId;
    private LocalDateTime purchaseDate;

    public PurchasedFood() {
    }

    public PurchasedFood(int foodItemId, int consumerId, LocalDateTime purchaseDate) {
        this.foodItemId = foodItemId;
        this.consumerId = consumerId;
        this.purchaseDate = purchaseDate;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "PurchasedFood{" +
                "foodItemId=" + foodItemId +
                ", consumerId=" + consumerId +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
