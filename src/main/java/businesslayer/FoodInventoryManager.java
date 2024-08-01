
package businesslayer;

import java.util.List;
import java.sql.SQLException;

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
    public FoodInventory getFoodInventory(int id){
        return dao.getFoodInventoryById(id);
    }

    public void addFoodInventory(FoodInventory newFood) {

        dao.addFoodInventory(newFood);
    }

    public void updateFoodInventory(FoodInventory updatedFood) {
        dao.updateFoodInventory(updatedFood);
    }

    public void deleteFoodInventory(int id) {

        dao.deleteFoodInventory(id);

    }

    //public FoodInventory getFoodInventory(int id) throws SQLException {
      //  return dao.getAllFoodInventory(id);
   // }

    public List<FoodInventory> getSurplusFoodInventory() throws SQLException {
        return dao.getSurplusFoodInventory();
    }

}
