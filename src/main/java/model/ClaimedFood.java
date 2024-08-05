package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClaimedFood {
    private int id;
    private int charitableId;
    private int foodInventoryId;
    private String foodInventoryName;
    private int quantity;
    private LocalDateTime claimDate;

    public ClaimedFood() {}

    public ClaimedFood(int id, int charitableId, int foodInventoryId, String foodInventoryName, int quantity, LocalDateTime claimDate) {
        this.id = id;
        this.charitableId = charitableId;
        this.foodInventoryId = foodInventoryId;
        this.foodInventoryName = foodInventoryName;
        this.quantity = quantity;
        this.claimDate = claimDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharitableId() {
        return charitableId;
    }

    public void setCharitableId(int charitableId) {
        this.charitableId = charitableId;
    }

    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    public String getFoodInventoryName() {
        return foodInventoryName;
    }

    public void setFoodInventoryName(String foodInventoryName) {
        this.foodInventoryName = foodInventoryName;
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

    public String getStrClaimDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return claimDate.format(formatter);
    }

    public void setClaimDate(LocalDateTime claimDate) {
        this.claimDate = claimDate;
    }

    public void setClaimDate(String claimDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        if (claimDate != null && !claimDate.isEmpty()) {
            this.claimDate = LocalDateTime.parse(claimDate, formatter);
        } else {
            this.claimDate = null;
        }
    }

    @Override
    public String toString() {
        return "ClaimedFood{" +
                "charitableId=" + charitableId +
                ", foodInventoryId=" + foodInventoryId +
                ", foodInventoryName='" + foodInventoryName + '\'' +
                ", quantity=" + quantity +
                ", claimDate=" + getStrClaimDate() +
                '}';
    }
}
