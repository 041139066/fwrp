package businesslayer;

import java.sql.SQLException;
import java.util.List;

import dataaccesslayer.FoodInventoryDAO;
import model.FoodInventory;

/**
 * Manages operations related to food inventory, including retrieval, addition, update, and deletion of food items.
 */
public class FoodInventoryManager {

    private final FoodInventoryDAO dao;

    /**
     * Initializes a new instance of the FoodInventoryManager class.
     * Creates an instance of FoodInventoryDAO for data access operations.
     */
    public FoodInventoryManager() {
        this.dao = new FoodInventoryDAO();
    }

    /**
     * Retrieves a list of all food inventory items.
     *
     * @return A list of {@link FoodInventory} objects representing all food inventory items.
     */
    public List<FoodInventory> getAllFoodInventory() {
        return dao.getAllFoodInventory();
    }

    /**
     * Retrieves a list of food inventory items for a specific retailer.
     *
     * @param retailerId The ID of the retailer whose food inventory items are to be retrieved.
     * @return A list of {@link FoodInventory} objects representing the food inventory items for the specified retailer.
     */
    public List<FoodInventory> getAllFoodInventoryByRetailerId(int retailerId) {
        return dao.getAllFoodInventoryByRetailerId(retailerId);
    }

    /**
     * Retrieves a list of food inventory items based on location.
     *
     * @param city The city in which the food inventory items are located.
     * @param province The province in which the food inventory items are located.
     * @return A list of {@link FoodInventory} objects representing the food inventory items at the specified location.
     */
    public List<FoodInventory> getAllFoodInventoryByLocation(String city, String province) {
        return dao.getAllFoodInventoryByLocation(city, province);
    }

    /**
     * Retrieves a list of surplus food inventory items for a specific retailer.
     *
     * @param retailerId The ID of the retailer whose surplus food inventory items are to be retrieved.
     * @return A list of {@link FoodInventory} objects representing the surplus food inventory items for the specified retailer.
     */
    public List<FoodInventory> getSurplusFoodInventoryByRetailerId(int retailerId) {
        return dao.getSurplusFoodInventoryByRetailerId(retailerId);
    }

    /**
     * Retrieves a specific food inventory item by its ID.
     *
     * @param id The ID of the food inventory item to retrieve.
     * @return A {@link FoodInventory} object representing the food inventory item with the specified ID.
     */
    public FoodInventory getFoodInventoryById(int id) {
        return dao.getFoodInventoryById(id);
    }

    /**
     * Adds a new food inventory item.
     *
     * @param foodInventory The {@link FoodInventory} object representing the food inventory item to add.
     * @throws SQLException If there is an error while adding the food inventory item to the database.
     */
    public void addFoodInventory(FoodInventory foodInventory) throws SQLException {
        dao.addFoodInventory(foodInventory);
    }

    /**
     * Updates an existing food inventory item.
     *
     * @param foodInventory The {@link FoodInventory} object representing the updated food inventory item.
     */
    public void updateFoodInventory(FoodInventory foodInventory) {
        dao.updateFoodInventory(foodInventory);
    }

    /**
     * Deletes a food inventory item by its ID.
     *
     * @param id The ID of the food inventory item to delete.
     */
    public void deleteFoodInventory(int id) {
        dao.deleteFoodInventory(id);
    }

    /**
     * Updates the quantity of a specific food inventory item.
     *
     * @param id The ID of the food inventory item to update.
     * @param quantity The new quantity of the food inventory item.
     */
    public void updateFoodInventoryQuantity(int id, int quantity) {
        dao.updateFoodInventoryQuantity(id, quantity);
    }

    /**
     * Updates the average rating of a specific food inventory item.
     *
     * @param id The ID of the food inventory item to update.
     * @param price The new average rating of the food inventory item.
     */
    public void updateFoodInventoryAverageRating(int id, double price) {
        dao.updateFoodInventoryAverageRating(id, price);
    }

    /**
     * Updates the status of a specific food inventory item.
     *
     * @param id The ID of the food inventory item to update.
     * @param status The new status of the food inventory item.
     */
    public void updateFoodInventoryStatus(int id, String status) {
        dao.updateFoodInventoryStatus(id, status);
    }
}
