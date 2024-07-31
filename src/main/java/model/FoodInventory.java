package model;

//import utilities.MyGson;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FoodInventory implements Serializable {

    private int id;
    private String description;
    private double standardPrice;
    private int quantity;
    private double averageRating;
    private LocalDateTime lastModified;

    public FoodInventory(int id,
                         String description,
                         double standardPrice,
                         int quantity,
                         double averageRating,
                         LocalDateTime lastModified){
        this.id=id;
        this.description = description;
        this.standardPrice=standardPrice;
        this.quantity=quantity;
        this.averageRating=averageRating;
        this.lastModified=lastModified;
    }

    public FoodInventory(String description, double standardPrice, int quantity, double averageRating, LocalDateTime lastModified) {
        this.description = description;
        this.standardPrice = standardPrice;
        this.quantity = quantity;
        this.averageRating = averageRating;
        this.lastModified = lastModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
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

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public String getStrLastModified() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return lastModified.format(formatter);
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "FoodInventory{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", standardPrice=" + standardPrice +
                ", quantity=" + quantity +
                ", lastModified=" + getStrLastModified() +
                ", averageRating=" + averageRating +
                '}';
    }
   // public String toJson() {
     //   return MyGson.getMyGson().toJson(this);
    //}
}

