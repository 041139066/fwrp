package model;

/**
 * The FoodPreference class represents a consumer's preference for a specific food item.
 * It includes methods to get and set the consumer's ID and the food inventory item's ID.
 */
public class FoodPreference {
    private int consumerId;
    private int foodInventoryId;

    /**
     * Constructs a FoodPreference object with the specified consumer ID and food inventory ID.
     *
     * @param consumerId The ID of the consumer who has the food preference.
     * @param foodInventoryId The ID of the food inventory item that is preferred.
     */
    public FoodPreference(int consumerId, int foodInventoryId) {
        this.consumerId = consumerId;
        this.foodInventoryId = foodInventoryId;
    }

    /**
     * Gets the ID of the consumer who has the food preference.
     *
     * @return The consumer's ID.
     */
    public int getConsumerId() {
        return consumerId;
    }

    /**
     * Sets the ID of the consumer who has the food preference.
     *
     * @param consumerId The consumer's ID.
     */
    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    /**
     * Gets the ID of the food inventory item that is preferred.
     *
     * @return The food inventory item's ID.
     */
    public int getFoodInventoryId() {
        return foodInventoryId;
    }

    /**
     * Sets the ID of the food inventory item that is preferred.
     *
     * @param foodInventoryId The food inventory item's ID.
     */
    public void setFoodInventoryId(int foodInventoryId) {
        this.foodInventoryId = foodInventoryId;
    }

    /**
     * Provides a string representation of the FoodPreference object.
     *
     * @return A string representation of the FoodPreference object, including the consumer ID and food inventory ID.
     */
    @Override
    public String toString() {
        return "FoodPreferences{" +
                "consumerId=" + consumerId +
                ", foodInventoryId=" + foodInventoryId +
                '}';
    }
}
