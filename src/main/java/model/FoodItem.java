package model;

import java.time.LocalDate;

/**
 *
 * @author yuexia Jin
 */
public class FoodItem {

    private int id;
    private String name;
    private int quantity;
    private LocalDate expirationDate;
    private String status;
    private double price;
    private int retailerId;
    private int foodInventoryId;

    public FoodItem(int id, String name, int quantity, LocalDate expirationDate, double price, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.status=status;
        this.price=price;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getRetailerId() {
        return retailerId;
    }

    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    public String toString(){
        return null;
    }
}


