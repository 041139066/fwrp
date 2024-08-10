package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The ClaimedFood class represents food that has been claimed by a charitable organization.
 * It contains information about the food item, the quantity claimed, and the date of the claim.
 */
public class ClaimedFood {
    private int id;
    private int charitableId;
    private int foodInventoryId;
    private String foodInventoryName;
    private int quantity;
    private LocalDateTime claimDate;

    /**
     * Default constructor for ClaimedFood.
     */
    public ClaimedFood() {}

    /**
     * Constructs a ClaimedFood instance with the specified parameters.
     *
     * @param id The unique identifier for the claimed food record.
     * @param charitableId The identifier of the charitable organization that claimed the food.
     * @param foodInventoryId The identifier of the food inventory item.
     * @param foodInventoryName The name of the food inventory item.
     * @param quantity The quantity of the food claimed.
     * @param claimDate The date and time when the food was claimed.
     */
    public ClaimedFood(int id, int charitableId, int foodInventoryId, String foodInventoryName, int quantity, LocalDateTime claimDate) {
        this.id = id;
        this.charitableId = charitableId;
        this.foodInventoryId = foodInventoryId;
        this.foodInventoryName = foodInventoryName;
        this.quantity = quantity;
        this.claimDate = claimDate;
    }

    /**
     * Gets the unique identifier for the claimed food record.
     *
     * @return The unique identifier for the claimed food record.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the claimed food record.
     *
     * @param id The unique identifier for the claimed food record.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the identifier of the charitable organization that claimed the food.
     *
     * @return The identifier of the charitable organization.
     */
    public int getCharitableId() {
        return charitableId;
    }

    /**
     * Sets the identifier of the charitable organization that claimed the food.
     *
     * @param charitableId The identifier of the charitable organization.
     */
    public void setCharitableId(int charitableId) {
        this.charitableId = charitableId;
    }

    /**
     * Gets the identifier of the food inventory item.
     *
     * @return The identifier of the food inventory item.
     */
    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    /**
     * Sets the identifier of the food inventory item.
     *
     * @param foodInventoryId The identifier of the food inventory item.
     */
    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    /**
     * Gets the name of the food inventory item.
     *
     * @return The name of the food inventory item.
     */
    public String getFoodInventoryName() {
        return foodInventoryName;
    }

    /**
     * Sets the name of the food inventory item.
     *
     * @param foodInventoryName The name of the food inventory item.
     */
    public void setFoodInventoryName(String foodInventoryName) {
        this.foodInventoryName = foodInventoryName;
    }

    /**
     * Gets the quantity of the food claimed.
     *
     * @return The quantity of the food claimed.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the food claimed.
     *
     * @param quantity The quantity of the food claimed.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the date and time when the food was claimed.
     *
     * @return The date and time when the food was claimed.
     */
    public LocalDateTime getClaimDate() {
        return claimDate;
    }

    /**
     * Gets the date and time when the food was claimed as a formatted string.
     *
     * @return The formatted date and time of the claim.
     */
    public String getStrClaimDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return claimDate.format(formatter);
    }

    /**
     * Sets the date and time when the food was claimed.
     *
     * @param claimDate The date and time when the food was claimed.
     */
    public void setClaimDate(LocalDateTime claimDate) {
        this.claimDate = claimDate;
    }

    /**
     * Sets the date and time when the food was claimed from a formatted string.
     *
     * @param claimDate The formatted date and time when the food was claimed.
     */
    public void setClaimDate(String claimDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        if (claimDate != null && !claimDate.isEmpty()) {
            this.claimDate = LocalDateTime.parse(claimDate, formatter);
        } else {
            this.claimDate = null;
        }
    }

    /**
     * Returns a string representation of the ClaimedFood instance.
     *
     * @return A string representation of the ClaimedFood instance.
     */
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
