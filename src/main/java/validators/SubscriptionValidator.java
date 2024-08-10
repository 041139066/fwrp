package validators;

import java.util.List;
import java.util.ArrayList;
import model.User;
import model.constants.MethodType;

/**
 * The SubscriptionValidator class is responsible for validating user subscription information.
 * It checks whether the contact method chosen by the user (email or SMS) is valid
 * and raises an exception if the provided email or phone number is invalid.
 */
public class SubscriptionValidator {

    /**
     * Validates the user subscription information based on the selected communication method.
     * If the communication method is email, it validates the provided email address.
     * If the communication method is SMS, it validates the provided phone number.
     *
     * @param user The User object containing the subscription information to validate.
     * @throws ValidationException if the provided email or phone number is invalid.
     */
    public void validate(User user) throws ValidationException {
        List<String> list = new ArrayList<>();

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

    /**
     * Validates the provided email address.
     * The email must match a standard email pattern.
     *
     * @param email The email address to validate.
     * @throws ValidationException if the email address is invalid.
     */
    private void validateEmail(String email) throws ValidationException {
        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        if(!email.matches(pattern)) {
            throw new ValidationException("Invalid email: '" + email + "'.");
        }
    }

    /**
     * Validates the provided phone number.
     * The phone number must match a standard phone number pattern, which can include formats like
     * (123) 456-7890, 123-456-7890, or a 10-digit number without delimiters.
     *
     * @param phone The phone number to validate.
     * @throws ValidationException if the phone number is invalid.
     */
    private void validatePhone(String phone) throws ValidationException {
        String pattern = "^(?:\\(\\d{3}\\)\\s?\\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}|\\d{10})$";
        if(!phone.matches(pattern)) {
            throw new ValidationException("Invalid phone number: '" + phone + "'.");
        }
    }

}

