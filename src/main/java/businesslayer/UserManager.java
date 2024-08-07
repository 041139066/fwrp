package businesslayer;

import dataaccesslayer.FoodPreferencesDAO;
import dataaccesslayer.UserDAO;
import model.EmailSubscriber;
import model.SMSSubscriber;
import model.Subscriber;
import model.constants.MethodType;
import model.constants.UserType;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    UserDAO userDAO;
    FoodPreferencesDAO prefDAO;

    public UserManager(){
        userDAO = new UserDAO();
        prefDAO = new FoodPreferencesDAO();
    }
    public List<Subscriber> getSubscribers(UserType type, int retailerId){
        List<User> users = userDAO.getSubscribers(type, retailerId);
        List<Subscriber> subscribers = new ArrayList<>();
        for(User user : users){
            List<Integer> foodPreferences = prefDAO.getFoodPreferencesByUserId(user.getId());
            if(user.getMethod() == MethodType.email){
                subscribers.add(new EmailSubscriber(user.getId(), user.getName(), user.getContactEmail(), foodPreferences));
            }
            if(user.getMethod() == MethodType.sms){
                subscribers.add(new SMSSubscriber(user.getId(), user.getName(), user.getContactPhone(), foodPreferences));
            }
        }
        return subscribers;
    }
}
