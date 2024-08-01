package businesslayer;

import dataaccesslayer.ClaimedFoodDAO;
import model.ClaimedFood;
import model.DonationFoodVO;

import java.util.List;

public class DonationFoodManager {

    private final ClaimedFoodDAO claimedFoodDAO;

    public DonationFoodManager() {
        claimedFoodDAO = new ClaimedFoodDAO();
    }

    /**
     * 获取所有捐赠的物品
     * @return
     */
    public List<DonationFoodVO> getAllDonationFood(){
        return claimedFoodDAO.getAllDonationFood();
    }

    public void claimFood(Integer id, Integer need, Integer userId){
        claimedFoodDAO.claimFood(id,need,userId);
    }

    public List<ClaimedFood> myClaimFood(Integer userId){
        return claimedFoodDAO.myClaimFood(userId);
    }
}
