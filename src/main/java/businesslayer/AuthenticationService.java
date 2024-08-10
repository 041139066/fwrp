package businesslayer;

import dataaccesslayer.UserDAO;
import model.User;
import utilities.PasswordHasher;
import validators.ValidationException;

import java.sql.SQLException;

/**
 * Provides authentication services including user authentication and registration.
 */
public class AuthenticationService {
    private final UserDAO userDAO;

    /**
     * Initializes a new instance of the AuthenticationService class.
     * Creates an instance of UserDAO for data access operations.
     */
    public AuthenticationService() {
        userDAO = new UserDAO();
    }

    /**
     * Authenticates a user based on the provided email and password.
     *
     * @param email The email address of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return The {@link User} object representing the authenticated user.
     * @throws ValidationException If the email address is not registered or the password is incorrect.
     */
    public User authenticate(String email, String password) throws ValidationException {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new ValidationException("The email address is not registered yet");
        }
        String hashedPassword = user.getPassword();
        if (!PasswordHasher.checkPassword(password, hashedPassword)) {
            throw new ValidationException("The email or password is incorrect");
        }
        return user;
    }

    /**
     * Registers a new user in the system.
     *
     * @param user The {@link User} object containing the details of the user to register.
     * @throws SQLException If there is an error during the registration process.
     */
    public void register(User user) throws SQLException {
        userDAO.register(user);
    }
}
