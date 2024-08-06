package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utilities.MyGson;

public class FoodInventory implements Serializable {

    private int id;
    private String name;
    private double price;
    private LocalDateTime expirationDate;
    private int quantity;
    private double averageRating;
    private FoodStatus status;
    private int retailerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getLocalExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return expirationDate.format(formatter);
    }
    public String getStrExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return expirationDate.format(formatter);
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        // Correct the pattern to use 'HH' for 24-hour format instead of 'hh' for 12-hour format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        if (expirationDate != null && !expirationDate.isEmpty()) {
            this.expirationDate = LocalDateTime.parse(expirationDate, formatter);
        } else {
            this.expirationDate = null;
        }
    }

    public void setLocalExpirationDate(String expirationDate) {
        // Correct the pattern to use 'HH' for 24-hour format instead of 'hh' for 12-hour format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (expirationDate != null && !expirationDate.isEmpty()) {
            this.expirationDate = LocalDateTime.parse(expirationDate, formatter);
        } else {
            this.expirationDate = null;
        }
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isSurplus() {
        LocalDateTime oneWeekFromNow = LocalDateTime.now().plusWeeks(1);
        return expirationDate.isBefore(oneWeekFromNow);
    }

    public FoodStatus getStatus() {
        return status;
    }

    public String getStrStatus() {
        return (status != null) ? status.name() : "";
    }

    public void setStatus(FoodStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        try {
            if (status != null && !status.isEmpty()) {
                // Match the status string with enum constants
                this.status = FoodStatus.valueOf(status.toUpperCase());
            } else {
                this.status = null;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Invalid status value: " + status);
            this.status = null;
        }

    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

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

    public String toJson() {
        return MyGson.getMyGson().toJson(this);
    }
}

