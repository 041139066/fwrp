package businesslayer;
import model.FoodInventory;

/**
 *
 * @author yuexia Jin
 */
public class FoodInventoryValidator {

    public void validate(FoodInventory foodInventory){
        if (foodInventory == null) {
            throw new IllegalArgumentException("Food inventory cannot be null");
        }
        if (foodInventory.getDescription() == null || foodInventory.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Food inventory name cannot be null or empty");
        }
        if (foodInventory.getQuantity() < 0) {
            throw new IllegalArgumentException("Food inventory quantity cannot be negative");
        }
        if (foodInventory.getAverageRating() < 0.0 || foodInventory.getAverageRating() > 5.0) {
            throw new IllegalArgumentException("Food inventory rating must be between 0.0 and 5.0");
        }
    }

}


