package businesslayer;

import java.util.List;
import java.sql.SQLException;

import dataaccesslayer.FoodInventoryDAO;
import model.FoodInventory;

public class FoodInventoryManager {

    private final FoodInventoryDAO dao;

    public FoodInventoryManager() {
        dao = new FoodInventoryDAO();
    }

    public List<FoodInventory> getAllFoodInventory() {
        return dao.getAllFoodInventory();
    }

}
