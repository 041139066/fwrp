package businesslayer;

import model.FoodItem;

/**
 *
 * @author yuexia Jin
 */
public class FoodItemValidator {
    public void validate(FoodItem item) {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new IllegalArgumentException("Food item name cannot be null or empty");
        }
        if (item.getQuantity() < 0) {
            throw new IllegalArgumentException("Food item quantity cannot be negative");
        }
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("Food item price cannot be negative");
        }
        if (!item.getStatus().matches("^(Available|Surplus|Expired)$")) {
            throw new IllegalArgumentException("Food item status must be 'Available', 'Surplus', or 'Expired'");
        }
    }
}

