package model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The PurchasedFood class represents a record of a food item purchased by a consumer.
 * It includes information about the purchase such as the consumer's ID, the food inventory item ID,
 * the quantity purchased, the total cost, and the purchase date.
 */
public class PurchasedFood {
    private int id;
    private int consumerId;
    private int foodInventoryId;
    private String foodInventoryName;
    private int quantity;
    private double cost;
    private LocalDateTime purchaseDate;

    /**
     * Default constructor for PurchasedFood.
     */
    public PurchasedFood() {}

    /**
     * Constructs a PurchasedFood object with the specified details.
     *
     * @param id The ID of the purchased food record.
     * @param consumerId The ID of the consumer who purchased the food.
     * @param foodInventoryId The ID of the food inventory item.
     * @param foodInventoryName The name of the food inventory item.
     * @param quantity The quantity of the food item purchased.
     * @param cost The total cost of the purchase.
     * @param purchaseDate The date and time when the food item was purchased.
     */
    public PurchasedFood(int id, int consumerId, int foodInventoryId, String foodInventoryName, int quantity, double cost, LocalDateTime purchaseDate) {
        this.id = id;
        this.consumerId = consumerId;
        this.foodInventoryId = foodInventoryId;
        this.foodInventoryName = foodInventoryName;
        this.quantity = quantity;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets the ID of the purchased food record.
     *
     * @return The ID of the purchased food record.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the purchased food record.
     *
     * @param id The ID of the purchased food record.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the consumer who purchased the food.
     *
     * @return The consumer's ID.
     */
    public int getConsumerId() {
        return consumerId;
    }

    /**
     * Sets the ID of the consumer who purchased the food.
     *
     * @param consumerId The consumer's ID.
     */
    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    /**
     * Gets the ID of the food inventory item.
     *
     * @return The food inventory item's ID.
     */
    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    /**
     * Sets the ID of the food inventory item.
     *
     * @param foodInventoryId The food inventory item's ID.
     */
    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    /**
     * Gets the name of the food inventory item.
     *
     * @return The food inventory item's name.
     */
    public String getFoodInventoryName() {
        return foodInventoryName;
    }

    /**
     * Sets the name of the food inventory item.
     *
     * @param foodInventoryName The food inventory item's name.
     */
    public void setFoodInventoryName(String foodInventoryName) {
        this.foodInventoryName = foodInventoryName;
    }

    /**
     * Gets the quantity of the food item purchased.
     *
     * @return The quantity of the food item purchased.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the food item purchased.
     *
     * @param quantity The quantity of the food item purchased.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the total cost of the purchase.
     *
     * @return The total cost of the purchase.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the total cost of the purchase formatted as a currency string.
     *
     * @return The formatted cost as a string.
     */
    public String getFormattedCost(){
        DecimalFormat df = new DecimalFormat("$#.00");
        return df.format(cost);
    }

    /**
     * Sets the total cost of the purchase.
     *
     * @param cost The total cost of the purchase.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the date and time when the food item was purchased.
     *
     * @return The purchase date and time as a LocalDateTime object.
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Gets the date and time when the food item was purchased as a formatted string.
     *
     * @return The purchase date and time as a formatted string.
     */
    public String getStrPurchaseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return purchaseDate.format(formatter);
    }

    /**
     * Sets the date and time when the food item was purchased.
     *
     * @param purchaseDate The purchase date and time as a LocalDateTime object.
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Sets the date and time when the food item was purchased from a formatted string.
     *
     * @param claimDate The purchase date and time as a string.
     */
    public void setPurchaseDate(String claimDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        if (claimDate != null && !claimDate.isEmpty()) {
            this.purchaseDate = LocalDateTime.parse(claimDate, formatter);
        } else {
            this.purchaseDate = null;
        }
    }
}
