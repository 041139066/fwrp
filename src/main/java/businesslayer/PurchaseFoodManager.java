package businesslayer;

import java.util.List;

import dataaccesslayer.PurchaseFoodDao;
import dataaccesslayer.PurchaseFoodListDao;
import model.AvailableSaleFood;

public class PurchaseFoodManager {

    private PurchaseFoodListDao listDao;
    private PurchaseFoodDao foodDao;

    public PurchaseFoodManager() {
        listDao = new PurchaseFoodListDao();
        foodDao = new PurchaseFoodDao();
    }

    public List<AvailableSaleFood> getAllSaleFood(){
        return listDao.getAllSaleFood();
    }

    public boolean purchaseFood(Integer foodItemId, Integer customerId, Integer need){
        return foodDao.purchaseFood(foodItemId,customerId,need);
    }

    public AvailableSaleFood getFoodById(Integer foodItemId){
        return foodDao.getAvailableSaleFoodById(foodItemId);
    }
}
