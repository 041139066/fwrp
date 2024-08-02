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

public class FoodInventoryManager {

    private final FoodInventoryDAO dao;
    //private FoodInventoryValidator validator;

    public FoodInventoryManager() {
        dao = new FoodInventoryDAO();
    }

    public List<FoodInventory> getAllFoodInventory() throws SQLException {
        return dao.getAllFoodInventory();
    }

}
