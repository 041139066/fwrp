package model;

import java.time.LocalDateTime;

public class PurchasedFood {
    private int foodItemId;
    private int customerId;
    private LocalDateTime purchaseDate;

    public PurchasedFood() {
    }

    public PurchasedFood(int foodItemId, int customerId, LocalDateTime purchaseDate) {
        this.foodItemId = foodItemId;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
    }

    /**
     * 获取
     * @return foodItemId
     */
    public int getFoodItemId() {
        return foodItemId;
    }

    /**
     * 设置
     * @param foodItemId
     */
    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    /**
     * 获取
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * 设置
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取
     * @return purchaseDate
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * 设置
     * @param purchaseDate
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String toString() {
        return "PurchasedFood{foodItemId = " + foodItemId + ", customerId = " + customerId + ", purchaseDate = " + purchaseDate + "}";
    }
}
