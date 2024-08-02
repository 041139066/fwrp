package businesslayer;

import dataaccesslayer.FoodInventoryDAO;
import dataaccesslayer.RatingDAO;
import model.Rating;

import java.util.List;

public class RatingService {
    private final RatingDAO rtDAO;
    private final FoodInventoryDAO fiDAO;

    public RatingService() {
        this.rtDAO = new RatingDAO();
        this.fiDAO = new FoodInventoryDAO();
    }

    public List<Rating> getAllRatings(){
        return rtDAO.getAllRatings();
    }

    public List<Rating> getAllRatingsByConsumerId(int consumerId){
        return rtDAO.getAllRatingsByConsumerId(consumerId);
    }

    public List<Rating> getAllRatingsByFoodInventoryId(int foodInventoryId){
        return rtDAO.getAllRatingsByFoodInventoryId(foodInventoryId);
    }

    public int createRating(Rating rating){
        int affectedRows = rtDAO.createRating(rating);
        updateAverageRating(rating.getFoodInventoryId());
        return affectedRows;
    }

    public int updateRating(Rating rating){
        int affectedRows = rtDAO.updateRating(rating);
        updateAverageRating(rating.getFoodInventoryId());
        return affectedRows;
    }

    public int deleteRating(int consumerId, int foodInventoryId){
        int affectedRows = rtDAO.deleteRating(consumerId, foodInventoryId);
        updateAverageRating(foodInventoryId);
        return affectedRows;
    }

    private void updateAverageRating(int foodInventoryId){
        Double averageRating = rtDAO.getAverageRating(foodInventoryId);
        fiDAO.updateAverageRating(foodInventoryId, averageRating);
    }
}
