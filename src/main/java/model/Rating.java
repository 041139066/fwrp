package model;

import utilities.MyGson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Rating class represents a consumer's rating and review of a food inventory item.
 * It includes information such as the consumer's ID and name, the food inventory ID and name,
 * the rating value, a comment, and the last modification timestamp.
 */
public class Rating {
    private int consumerId;
    private String consumerName;
    private int foodInventoryId;
    private String foodInventoryName;
    private double rating;
    private String comment;
    private LocalDateTime lastModified;

    /**
     * Gets the ID of the consumer who provided the rating.
     *
     * @return The consumer's ID.
     */
    public int getConsumerId() {
        return consumerId;
    }

    /**
     * Sets the ID of the consumer who provided the rating.
     *
     * @param consumerId The consumer's ID.
     */
    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    /**
     * Gets the name of the consumer who provided the rating.
     *
     * @return The consumer's name.
     */
    public String getConsumerName() {
        return consumerName;
    }

    /**
     * Sets the name of the consumer who provided the rating.
     *
     * @param consumerName The consumer's name.
     */
    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    /**
     * Gets the ID of the food inventory item being rated.
     *
     * @return The food inventory item's ID.
     */
    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    /**
     * Sets the ID of the food inventory item being rated.
     *
     * @param foodInventoryId The food inventory item's ID.
     */
    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    /**
     * Gets the name of the food inventory item being rated.
     *
     * @return The food inventory item's name.
     */
    public String getFoodInventoryName() {
        return foodInventoryName;
    }

    /**
     * Sets the name of the food inventory item being rated.
     *
     * @param foodInventoryName The food inventory item's name.
     */
    public void setFoodInventoryName(String foodInventoryName) {
        this.foodInventoryName = foodInventoryName;
    }

    /**
     * Gets the rating value provided by the consumer.
     *
     * @return The rating value.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating value provided by the consumer.
     *
     * @param rating The rating value.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gets the comment provided by the consumer about the food inventory item.
     *
     * @return The consumer's comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment provided by the consumer about the food inventory item.
     *
     * @param comment The consumer's comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the timestamp of when the rating was last modified.
     *
     * @return The last modification timestamp as a LocalDateTime object.
     */
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    /**
     * Gets the timestamp of when the rating was last modified as a formatted string.
     *
     * @return The last modification timestamp as a formatted string.
     */
    public String getStrLastModified() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        return lastModified.format(formatter);
    }

    /**
     * Sets the timestamp of when the rating was last modified.
     *
     * @param lastModified The last modification timestamp as a LocalDateTime object.
     */
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Returns a string representation of the Rating object.
     *
     * @return A string representation of the Rating object.
     */
    @Override
    public String toString() {
        return "Rating{" +
                "consumerId=" + consumerId +
                ", consumerName='" + consumerName + '\'' +
                ", foodInventoryId=" + foodInventoryId +
                ", foodInventoryName='" + foodInventoryName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", lastModified=" + getStrLastModified() +
                '}';
    }

    /**
     * Converts the Rating object to a JSON string representation.
     *
     * @return A JSON string representation of the Rating object.
     */
    public String toJson() {
        return MyGson.getMyGson().toJson(this);
    }
}
