package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.PurchaseFoodDAO;
import model.FoodInventory;
import model.PurchasedFood;

import java.util.List;

/**
 * Manages the purchase of food items and retrieval of purchased food details.
 */
public class PurchaseFoodManager {

    private final PurchaseFoodDAO purchaseFoodDAO;
    private final FoodInventoryDAO foodInventoryDAO;

    /**
     * Constructs a PurchaseFoodManager with necessary DAO instances.
     */
    public PurchaseFoodManager() {
        purchaseFoodDAO = new PurchaseFoodDAO();
        foodInventoryDAO = new FoodInventoryDAO();
    }

    /**
     * Retrieves all food inventory items available for sale.
     *
     * @return a List of FoodInventory objects available for sale.
     */
    public List<FoodInventory> getAllFoodInventoryForSale() {
        return foodInventoryDAO.getAllFoodInventoryForSale();
    }

    /**
     * Retrieves all purchased food records for a specific consumer.
     *
     * @param userId the ID of the consumer whose purchased food records are to be retrieved.
     * @return a List of PurchasedFood objects associated with the given consumer ID.
     */
    public List<PurchasedFood> getAllPurchasedFoodByConsumerId(int userId) {
        return purchaseFoodDAO.getAllPurchasedFoodByConsumerId(userId);
    }

    /**
     * Processes the purchase of food by a consumer.
     * Applies a 10% discount to the standard price.
     *
     * @param userId the ID of the consumer making the purchase.
     * @param id the ID of the food item being purchased.
     * @param need the quantity of the food item being purchased.
     * @param standardPrice the standard price of the food item.
     */
    public void purchaseFood(Integer userId, Integer id, Integer need, Double standardPrice) {
        Double price = need * standardPrice * 0.9; // Applying a 10% discount
        purchaseFoodDAO.purchaseFood(userId, id, need, price);
    }
}
