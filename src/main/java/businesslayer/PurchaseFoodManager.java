package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.PurchaseFoodDAO;
import model.AvailableFood;
import model.FoodInventory;
import model.PurchasedFood;

import java.util.List;

public class PurchaseFoodManager {

    private final PurchaseFoodDAO purchaseFoodDAO;
    private final FoodInventoryDAO foodInventoryDAO;

    public PurchaseFoodManager() {
        purchaseFoodDAO = new PurchaseFoodDAO();
        foodInventoryDAO = new FoodInventoryDAO();
    }

    public List<FoodInventory> getAllFoodInventoryForSale() {
        return foodInventoryDAO.getAllFoodInventoryForSale();
    }

    public List<PurchasedFood> getAllPurchasedFoodByConsumerId(int userId) {

        return purchaseFoodDAO.getAllPurchasedFoodByConsumerId(userId);
    }

    public void purchaseFood(Integer id, Integer need, Integer userId) {
        purchaseFoodDAO.purchaseFood(id, need, userId);
    }


}
