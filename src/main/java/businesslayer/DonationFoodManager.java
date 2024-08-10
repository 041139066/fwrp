package businesslayer;

import java.util.List;

import dataaccesslayer.ClaimedFoodDAO;
import dataaccesslayer.FoodInventoryDAO;
import model.ClaimedFood;
import model.FoodInventory;

/**
 * Manages operations related to food donations, including retrieving available food inventory for donation,
 * retrieving claimed food items, and claiming food.
 */
public class DonationFoodManager {

    private final ClaimedFoodDAO claimedFoodDAO;
    private final FoodInventoryDAO foodInventoryDAO;

    /**
     * Initializes a new instance of the DonationFoodManager class.
     * Creates instances of ClaimedFoodDAO and FoodInventoryDAO for data access operations.
     */
    public DonationFoodManager() {
        claimedFoodDAO = new ClaimedFoodDAO();
        foodInventoryDAO = new FoodInventoryDAO();
    }

    /**
     * Retrieves a list of all food inventory items available for donation.
     *
     * @return A list of {@link FoodInventory} objects representing all food inventory items available for donation.
     */
    public List<FoodInventory> getAllFoodInventoryForDonation() {
        return foodInventoryDAO.getAllFoodInventoryForDonation();
    }

    /**
     * Retrieves a list of claimed food items for a specific charitable user.
     *
     * @param userId The ID of the charitable user whose claimed food items are to be retrieved.
     * @return A list of {@link ClaimedFood} objects representing the claimed food items for the specified user.
     */
    public List<ClaimedFood> getAllClaimedFoodByCharitableId(int userId) {
        return claimedFoodDAO.getAllClaimedFoodByCharitableId(userId);
    }

    /**
     * Allows a user to claim food from the available food inventory.
     *
     * @param id The ID of the food inventory item to claim.
     * @param need The amount of food needed.
     * @param userId The ID of the user claiming the food.
     */
    public void claimFood(Integer id, Integer need, Integer userId) {
        claimedFoodDAO.claimFood(id, need, userId);
    }
}
