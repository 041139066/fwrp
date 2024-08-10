package model;

/**
 * The AvailableFood class represents a food item available for purchase or donation.
 * It includes details such as the item's description, price, quantity, and status.
 */
public class AvailableFood {
    private int id;
    private String description;
    private Double price;
    private int quantity;
    private String status;

    /**
     * Default constructor for AvailableFood.
     */
    public AvailableFood() {
    }

    /**
     * Constructs an AvailableFood instance with the specified details.
     *
     * @param id          The unique identifier for the food item.
     * @param description The description of the food item.
     * @param price       The price of the food item.
     * @param quantity    The quantity of the food item available.
     * @param status      The status of the food item (e.g., available, out of stock).
     */
    public AvailableFood(int id, String description, Double price, int quantity, String status) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Gets the unique identifier of the food item.
     *
     * @return The unique identifier of the food item.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the food item.
     *
     * @param id The unique identifier of the food item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the description of the food item.
     *
     * @return The description of the food item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the food item.
     *
     * @param description The description of the food item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price of the food item.
     *
     * @return The price of the food item.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the food item.
     *
     * @param price The price of the food item.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the quantity of the food item available.
     *
     * @return The quantity of the food item available.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the food item available.
     *
     * @param quantity The quantity of the food item available.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the status of the food item.
     *
     * @return The status of the food item.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the food item.
     *
     * @param status The status of the food item.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the AvailableFood instance.
     *
     * @return A string representation of the AvailableFood instance.
     */
    @Override
    public String toString() {
        return "AvailableFood{id=" + id + ", description='" + description + "', price=" + price + ", quantity=" + quantity + ", status='" + status + "'}";
    }
}
