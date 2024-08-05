package model;

import java.time.LocalDateTime;

public class ClaimedFood {
    private int foodItemId;
    private int charitableId;
    private int quantity;
    private LocalDateTime claimDate;

    public ClaimedFood() {
    }

    public ClaimedFood(int foodItemId, int charitableId, int quantity, LocalDateTime claimDate) {
        this.foodItemId = foodItemId;
        this.charitableId = charitableId;
        this.quantity = quantity;
        this.claimDate = claimDate;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public int getCharitableId() {
        return charitableId;
    }

    public void setCharitableId(int charitableId) {
        this.charitableId = charitableId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDateTime claimDate) {
        this.claimDate = claimDate;
    }

    @Override
    public String toString() {
        return "ClaimedFood{" +
                "foodItemId=" + foodItemId +
                ", charitableId=" + charitableId +
                ", quantity=" + quantity +
                ", claimDate=" + claimDate +
                '}';
    }
}
