package model;

import java.time.LocalDateTime;

public class ClaimedFood {
    private int foodItemId;
    private int charitableId;
    private LocalDateTime claimDate;

    public ClaimedFood() {
    }

    public ClaimedFood(int foodItemId, int charitableId, LocalDateTime claimDate) {
        this.foodItemId = foodItemId;
        this.charitableId = charitableId;
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
                ", claimDate=" + claimDate +
                '}';
    }
}
