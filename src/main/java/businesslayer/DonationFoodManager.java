package businesslayer;

import java.util.List;

import dataaccesslayer.ClaimedFoodDAO;
import dataaccesslayer.FoodInventoryDAO;
import model.ClaimedFood;
import model.FoodInventory;

public class DonationFoodManager {

    private final ClaimedFoodDAO claimedFoodDAO;
    private final FoodInventoryDAO foodInventoryDAO;

    public DonationFoodManager() {
        claimedFoodDAO = new ClaimedFoodDAO();
        foodInventoryDAO = new FoodInventoryDAO();
    }

    public List<FoodInventory> getAllFoodInventoryForDonation() {
        return foodInventoryDAO.getAllFoodInventoryForDonation();
    }

    public List<ClaimedFood> getAllClaimedFoodByCharitableId(int userId) {
        return claimedFoodDAO.getAllClaimedFoodByCharitableId(userId);
    }

    public void claimFood(Integer id, Integer need, Integer userId) {
        claimedFoodDAO.claimFood(id, need, userId);
    }

}
