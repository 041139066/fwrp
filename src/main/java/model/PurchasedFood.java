package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PurchasedFood {
    private int id;
    private int consumerId;
    private int foodInventoryId;
    private String foodInventoryName;
    private int quantity;
    private LocalDateTime purchaseDate;

    public PurchasedFood() {}

    public PurchasedFood(int id, int consumerId, int foodInventoryId, String foodInventoryName, int quantity, LocalDateTime purchaseDate) {
        this.id = id;
        this.consumerId = consumerId;
        this.foodInventoryId = foodInventoryId;
        this.foodInventoryName = foodInventoryName;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public String getStrPurchaseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return purchaseDate.format(formatter);
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPurchaseDate(String claimDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        if (claimDate != null && !claimDate.isEmpty()) {
            this.purchaseDate = LocalDateTime.parse(claimDate, formatter);
        } else {
            this.purchaseDate = null;
        }
    }
}
