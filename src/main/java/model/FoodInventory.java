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
    private LocalDateTime expirationDate;
    private boolean is_surplus;
    private boolean isForDonation;
    private boolean isForSale;

    public FoodInventory(int id,
                         String description,
                         double standardPrice,
                         int quantity,
                         double averageRating,
                         LocalDateTime expirationDate,
                         boolean is_surplus,
                         boolean isForDonation, boolean isForSale){
        this.id=id;
        this.description = description;
        this.standardPrice=standardPrice;
        this.quantity=quantity;
        this.averageRating=averageRating;
        this.expirationDate=expirationDate;
        this.is_surplus=is_surplus;
        this.isForDonation = isForDonation;
        this.isForSale = isForSale;
    }

    public FoodInventory(int id,
                         String description,
                         double standardPrice,
                         int quantity,
                         double averageRating,
                         LocalDateTime expirationDate){
        this.id=id;
        this.description = description;
        this.standardPrice=standardPrice;
        this.quantity=quantity;
        this.averageRating=averageRating;
        this.expirationDate=expirationDate;
    }

    public FoodInventory(String description,
                         double standardPrice,
                         int quantity,
                         double averageRating,
                         LocalDateTime expirationDate,
                         boolean is_surplus,
                         boolean isForDonation, boolean isForSale){
        this.description = description;
        this.standardPrice=standardPrice;
        this.quantity=quantity;
        this.averageRating=averageRating;
        this.expirationDate=expirationDate;
        this.is_surplus=is_surplus;
        this.isForDonation = isForDonation;
        this.isForSale = isForSale;
    }


    public FoodInventory(String description, double standardPrice, int quantity, double averageRating, LocalDateTime expirationDate,
                         boolean isForSale, boolean isForDonation) {
        this.description = description;
        this.standardPrice = standardPrice;
        this.quantity = quantity;
        this.averageRating = averageRating;
        this.expirationDate = expirationDate;
        this.isForSale = isForSale;
        this.isForDonation = isForDonation;
    }


    public FoodInventory(int id, String description, double standardPrice, int quantity, double averageRating, LocalDateTime expirationDate, boolean isForDonation, boolean isForSale) {
        this.id = id;
        this.description = description;
        this.standardPrice = standardPrice;
        this.quantity = quantity;
        this.averageRating = averageRating;
        this.expirationDate = expirationDate;
        this.isForDonation = isForDonation;
        this.isForSale = isForSale;
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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getStrExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return expirationDate.format(formatter);
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
    public Boolean getIsSurplus() {
        return is_surplus;
    }
   public void setIsSurplus(boolean is_surplus) {
        this.is_surplus = is_surplus;
   }
   public boolean getIsForDonation() {
        return isForDonation;
   }
   public void setIsForDonation(boolean isForDonation) {
        this.isForDonation = isForDonation;
   }
   public boolean getIsForSale() {
        return isForSale;
   }
   public void setIsForSale(boolean isForSale) {
        this.isForSale = isForSale;
   }
    @Override
    public String toString() {
        return "FoodInventory{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", standardPrice=" + standardPrice +
                ", quantity=" + quantity +
                ", lastModified=" + getStrExpirationDate() +
                ", averageRating=" + averageRating +
                '}';
    }
   // public String toJson() {
     //   return MyGson.getMyGson().toJson(this);
    //}
}

