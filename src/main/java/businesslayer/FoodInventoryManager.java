package businesslayer;

import java.util.List;

import dataaccesslayer.FoodInventoryDAO;
import model.FoodInventory;

public class FoodInventoryManager {

    private final FoodInventoryDAO dao;
    //private FoodInventoryValidator validator;

    public FoodInventoryManager() {
        dao = new FoodInventoryDAO();
    }

    public List<FoodInventory> getAllFoodInventory() {
        return dao.getAllFoodInventory();
    }

    public FoodInventory getFoodInventoryById(int foodInventoryID) {
        return dao.getFoodInventoryById(foodInventoryID);
    }

}
