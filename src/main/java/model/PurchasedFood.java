package model;

import java.time.LocalDateTime;

public class PurchasedFood {
    private int foodItemId;
    private int consumerId;
    private int quantity;
    private LocalDateTime purchaseDate;

    public PurchasedFood() {
    }

    public PurchasedFood(int foodItemId, int consumerId, int quantity, LocalDateTime purchaseDate) {
        this.foodItemId = foodItemId;
        this.consumerId = consumerId;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
                ", quantity=" + quantity +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
