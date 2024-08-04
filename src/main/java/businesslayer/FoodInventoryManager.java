/* File: AuthorsBusinessLogic.java
 * AuthorDTO: Stanley Pieda
 * Date: 2015
 * Description: Demonstration of DAO Design Pattern with Servlet website
 */
package businesslayer;

import java.util.List;
import java.sql.SQLException;

import dataaccesslayer.FoodInventoryDAO;
import model.FoodInventory;
import model.FoodStatus;

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

    public List<FoodInventory> getSurplusFoodInventoryByRetailerId(int retailerId) {
        return dao.getSurplusFoodInventoryByRetailerId(retailerId);
    }

    public FoodInventory getFoodInventoryById(int id) {
        return dao.getFoodInventoryById(id);
    }

    public void addFoodInventory(FoodInventory foodInventory) {
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

    public void updateFoodInventoryStatus(int id, FoodStatus status) {
        dao.updateFoodInventoryStatus(id, status);
    }
}
