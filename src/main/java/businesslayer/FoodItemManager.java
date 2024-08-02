package businesslayer;

import dataaccesslayer.FoodItemDAO;
import java.sql.Date;
import java.util.List;
import model.FoodItem;

/**
 *
 * @author yuexia Jin
 */
public class FoodItemManager {

    private FoodItemDAO dao;
    private FoodItemValidator validator;

    public FoodItemManager(FoodItemDAO dao, FoodItemValidator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    public List<FoodItem> getAllFoodItems() {
        return dao.getAllFoodItems();
    }

    public List<FoodItem> getAllSurplusFood() {
        return dao.getAllSurplusFood();
    }

    public FoodItem getFoodItemById(int id) {
        return dao.getFoodItemById(id);
    }

    public void createFoodItem(FoodItem item) {
        validator.validate(item);
        dao.createFoodItem(item);
    }

    public void updateFoodItem(FoodItem item) {
        validator.validate(item);
        dao.updateFoodItem(item);
    }

    public void updateFoodItemExpirationDate(int id, Date date) {
        dao.updateFoodItemExpirationDate(id, date);
    }

    public void updateFoodItemPrice(int id, double price) {
        dao.updateFoodItemPrice(id, price);
    }

    public void updateFoodItemStatus(int id, String status) {
        dao.updateFoodItemStatus(id, status);
    }

    //public void notifyConsumers() {
    //  dao.notifyConsumers();
    //}

    public void notifyConsumers(){
        SurplusFoodAlert notification= new SurplusFoodAlert();
        notification.NotificationService();
    }

}


