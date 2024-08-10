package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.constants.FoodStatus;
import utilities.MyGson;

/**
 * The FoodInventory class represents an inventory item in the food management system.
 * It includes information about the food item's ID, name, price, expiration date, quantity,
 * average rating, status, and retailer ID. It provides methods to get and set these attributes,
 * check if the item is surplus, and convert the item to JSON.
 */
public class FoodInventory implements Serializable {

    private int id;
    private String name;
    private double price;
    private LocalDateTime expirationDate;
    private int quantity;
    private double averageRating;
    private FoodStatus status;
    private int retailerId;

    /**
     * Gets the ID of the food inventory item.
     *
     * @return The ID of the food inventory item.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the food inventory item.
     *
     * @param id The ID of the food inventory item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the food inventory item.
     *
     * @return The name of the food inventory item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the food inventory item.
     *
     * @param name The name of the food inventory item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the food inventory item.
     *
     * @return The price of the food inventory item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the formatted price of the food inventory item.
     *
     * @return The formatted price of the food inventory item.
     */
    public String getFormattedPrice(){
        DecimalFormat df = new DecimalFormat("$#.00");
        return df.format(price);
    }

    /**
     * Sets the price of the food inventory item.
     *
     * @param price The price of the food inventory item.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the expiration date of the food inventory item.
     *
     * @return The expiration date of the food inventory item.
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Gets the expiration date as a string in ISO local date-time format.
     *
     * @return The expiration date as a string in ISO local date-time format.
     */
    public String getLocalExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return expirationDate.format(formatter);
    }

    /**
     * Gets the expiration date as a string in the format "yyyy/MM/dd hh:mm:ss".
     *
     * @return The expiration date as a string in the format "yyyy/MM/dd hh:mm:ss".
     */
    public String getStrExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return expirationDate.format(formatter);
    }

    /**
     * Sets the expiration date of the food inventory item.
     *
     * @param expirationDate The expiration date of the food inventory item.
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Sets the expiration date of the food inventory item from a string in the format "yyyy/MM/dd hh:mm:ss".
     *
     * @param expirationDate The expiration date of the food inventory item as a string.
     */
    public void setExpirationDate(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        if (expirationDate != null && !expirationDate.isEmpty()) {
            this.expirationDate = LocalDateTime.parse(expirationDate, formatter);
        } else {
            this.expirationDate = null;
        }
    }

    /**
     * Sets the expiration date of the food inventory item from a string in ISO local date-time format.
     *
     * @param expirationDate The expiration date of the food inventory item as a string.
     */
    public void setLocalExpirationDate(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (expirationDate != null && !expirationDate.isEmpty()) {
            this.expirationDate = LocalDateTime.parse(expirationDate, formatter);
        } else {
            this.expirationDate = null;
        }
    }

    /**
     * Gets the quantity of the food inventory item.
     *
     * @return The quantity of the food inventory item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the food inventory item.
     *
     * @param quantity The quantity of the food inventory item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the average rating of the food inventory item.
     *
     * @return The average rating of the food inventory item.
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Sets the average rating of the food inventory item.
     *
     * @param averageRating The average rating of the food inventory item.
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Determines if the food inventory item is considered surplus.
     * An item is considered surplus if its expiration date is within one week from now.
     *
     * @return True if the item is surplus, false otherwise.
     */
    public boolean isSurplus() {
        LocalDateTime oneWeekFromNow = LocalDateTime.now().plusWeeks(1);
        return expirationDate.isBefore(oneWeekFromNow);
    }

    /**
     * Gets the status of the food inventory item.
     *
     * @return The status of the food inventory item.
     */
    public FoodStatus getStatus() {
        return status;
    }

    /**
     * Gets the status of the food inventory item as a string.
     *
     * @return The status of the food inventory item as a string.
     */
    public String getStrStatus() {
        return (status != null) ? status.name() : "";
    }

    /**
     * Sets the status of the food inventory item.
     *
     * @param status The status of the food inventory item.
     */
    public void setStatus(FoodStatus status) {
        this.status = status;
    }

    /**
     * Sets the status of the food inventory item from a string.
     *
     * @param status The status of the food inventory item as a string.
     */
    public void setStatus(String status) {
        try {
            this.status = FoodStatus.valueOf(status);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.status = null;
        }
    }

    /**
     * Gets the retailer ID associated with the food inventory item.
     *
     * @return The retailer ID.
     */
    public int getRetailerId() {
        return retailerId;
    }

    /**
     * Sets the retailer ID associated with the food inventory item.
     *
     * @param retailerId The retailer ID.
     */
    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    /**
     * Provides a string representation of the FoodInventory object.
     *
     * @return A string representation of the FoodInventory object, including all its attributes.
     */
    @Override
    public String toString() {
        return "FoodInventory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", expirationDate=" + getStrExpirationDate() +
                ", quantity=" + quantity +
                ", averageRating=" + averageRating +
                ", status=" + status +
                ", retailerId=" + retailerId +
                '}';
    }

    /**
     * Converts the FoodInventory object to its JSON representation.
     *
     * @return A JSON string representation of the FoodInventory object.
     */
    public String toJson() {
        return MyGson.getMyGson().toJson(this);
    }
}
