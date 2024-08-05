package businesslayer;

import dataaccesslayer.PurchaseFoodDAO;
import model.AvailableFood;
import model.PurchasedFood;

import java.util.List;

public class PurchaseFoodManager {

    private final PurchaseFoodDAO purchaseFoodDAO;

    public PurchaseFoodManager() {
        purchaseFoodDAO = new PurchaseFoodDAO();
    }

    public List<AvailableFood> getAllAvailableFood() {
        return purchaseFoodDAO.getAllAvailableFood();
    }

    public void purchaseFood(Integer id, Integer need, Integer userId) {
        purchaseFoodDAO.purchaseFood(id, need, userId);
    }

    public List<PurchasedFood> getAllPurchasedFood() {
        return purchaseFoodDAO.getAllPurchasedFood();
    }
}
