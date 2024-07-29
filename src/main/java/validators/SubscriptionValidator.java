package validators;

import java.util.List;
import java.util.ArrayList;

import dataaccesslayer.CityDAO;
import dataaccesslayer.UserDAO;
import model.City;
import model.Subscription;
import model.User;
import model.UserType;

public class SubscriptionValidator {

    public void validate(Subscription subscription) throws ValidationException {
        List<String> list = new ArrayList<>();
        list.add(validateConsumer(subscription.getConsumerId()));
        list.add(validateLocation(subscription.getCity(), subscription.getProvince()));
        if (subscription.getMethod() == null || subscription.getMethod().isEmpty()) {
            list.add("Communication method should not be empty");
        } else {
            if (subscription.getMethod().equalsIgnoreCase("email")) {
                list.add(validateEmail(subscription.getEmail()));
            }
            if (subscription.getMethod().equalsIgnoreCase("sms")) {
                list.add(validatePhone(subscription.getPhone()));
            }
        }

        String message = String.join(" ", list);
        if (!message.trim().isEmpty()) {
            throw new ValidationException(message);
        }
    }

    private String validateConsumer(int consumerId) throws ValidationException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(consumerId);
        return user == null ? "Cannot find consumer: '" + consumerId + "'." : user.getType() != UserType.consumer ? "User: '" + consumerId + "' is not a consumer." : "";
    }

    private String validateLocation(String cityId, String provinceId) {
        CityDAO cityDAO = new CityDAO();
        City city = cityDAO.getCityByIds(cityId, provinceId);
        return city == null ? "Invalid Canada location: '" + cityId + ", " + provinceId + "'." : "";
    }

    private String validateEmail(String email) {
        return email == null || !email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$") ? "Invalid email: '" + email + "'." : "";
    }

    private String validatePhone(String phone) {

        return phone == null || !phone.matches("^(?:\\(\\d{3}\\)\\s?\\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}|\\d{10})$") ? "Invalid phone number: '" + phone + "'." : "";
    }


}
