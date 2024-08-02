package model;

import utilities.MyGson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rating {
    private int consumerId;
    private String consumerName;
    private int foodInventoryId;
    private String foodInventoryDescription;
    private double rating;
    private String comment;
    private LocalDateTime lastModified;

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    public String getFoodInventoryDescription() {
        return foodInventoryDescription;
    }

    public void setFoodInventoryDescription(String foodInventoryDescription) {
        this.foodInventoryDescription = foodInventoryDescription;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        return "Rating{" +
                "consumerId=" + consumerId +
                ", consumerName='" + consumerName + '\'' +
                ", foodInventoryId=" + foodInventoryId +
                ", foodInventoryDescription='" + foodInventoryDescription + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", lastModified=" + getStrLastModified() +
                '}';
    }

    public String toJson() {
        return MyGson.getMyGson().toJson(this);
    }
}
