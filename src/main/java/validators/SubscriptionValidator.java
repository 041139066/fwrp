package validators;

import java.util.List;
import java.util.ArrayList;
import model.User;
import model.constants.MethodType;

public class SubscriptionValidator {

    public void validate(User user) throws ValidationException {
        List<String> list = new ArrayList<>();
//        try{
//            validateLocation(user.getCity(), user.getProvince());
//        } catch (ValidationException e) {
//            list.add(e.getMessage());
//        }
        try {
            MethodType method = user.getMethod();
            if(method == MethodType.email){
                validateEmail(user.getContactEmail());
            }
            if(method == MethodType.sms){
                validatePhone(user.getContactPhone());
            }
        } catch (ValidationException e) {
            list.add(e.getMessage());
        }

        String message = String.join(" ", list);
        if (!message.trim().isEmpty()) {
            throw new ValidationException(message);
        }
    }

//    private void validateLocation(String cityId, String provinceId) throws ValidationException {
//        CityDAO cityDAO = new CityDAO();
//        City city = cityDAO.getCityByIds(cityId, provinceId);
//        if(city == null) {
//            throw new ValidationException("Invalid Canada location: '" + cityId + ", " + provinceId + "'.");
//        }
//    }

    private void validateEmail(String email) throws ValidationException {
        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        if(!email.matches(pattern)) {
            throw new ValidationException("Invalid email: '" + email + "'.");
        }
    }

    private void validatePhone(String phone) throws ValidationException {
        String pattern = "^(?:\\(\\d{3}\\)\\s?\\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}|\\d{10})$";
        if(!phone.matches(pattern)) {
            throw new ValidationException("Invalid phone number: '" + phone + "'.");
        }
    }


}
