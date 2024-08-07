package businesslayer;

import java.sql.SQLException;

import java.util.List;

import dataaccesslayer.FoodInventoryDAO;
import model.FoodInventory;

public class FoodInventoryManager {

    private final FoodInventoryDAO dao;
    //private FoodInventoryValidator validator;

    public FoodInventoryManager() {
        this.dao = new FoodInventoryDAO();
    }

    public List<FoodInventory> getAllFoodInventory() {
        return dao.getAllFoodInventory();
    }

    public List<FoodInventory> getAllFoodInventoryByRetailerId(int retailerId) {
        return dao.getAllFoodInventoryByRetailerId(retailerId);
    }
    public List<FoodInventory> getAllFoodInventoryByLocation(String city, String province) {
        return dao.getAllFoodInventoryByLocation(city, province);
    }
    
    public List<FoodInventory> getSurplusFoodInventoryByRetailerId(int retailerId) {
        return dao.getSurplusFoodInventoryByRetailerId(retailerId);
    }

    public FoodInventory getFoodInventoryById(int id) {
        return dao.getFoodInventoryById(id);
    }

    public void addFoodInventory(FoodInventory foodInventory) throws SQLException {
        dao.addFoodInventory(foodInventory);
    }

    public void updateFoodInventory(FoodInventory foodInventory) {
        dao.updateFoodInventory(foodInventory);
    }

    public void deleteFoodInventory(int id) {
        dao.deleteFoodInventory(id);
    }

    public void updateFoodInventoryQuantity(int id, int quantity) {
        dao.updateFoodInventoryQuantity(id, quantity);
    }

    public void updateFoodInventoryAverageRating(int id, double price) {
        dao.updateFoodInventoryAverageRating(id, price);
    }

    public void updateFoodInventoryStatus(int id, String status) {
        dao.updateFoodInventoryStatus(id, status);
    }
}
